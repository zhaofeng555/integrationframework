package hjg.cache2;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import com.alibaba.fastjson.JSONObject;

@SuppressWarnings({"rawtypes","unchecked"})
public class RedisCacheDaoImpl  implements RedisCacheDao{
	
	ShardedJedisPool shardedJedisPool;
	
	public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
		this.shardedJedisPool = shardedJedisPool;
	}

	public String get(String key) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		String rs=null;
		try {
			rs = jedis.get(key);
		} catch (Exception e) {
			
			//shardedJedisPool.returnBrokenResource(jedis);
		}finally{
			shardedJedisPool.returnResource(jedis);
		}
		return rs;
	}

	public Boolean set(String key, String value) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		String rs="";
		try {
			rs = jedis.set(key, value);
		} catch (Exception e) {
			
			//shardedJedisPool.returnBrokenResource(jedis);
		}finally{
			shardedJedisPool.returnResource(jedis);
		}
		return "OK".equalsIgnoreCase(rs);
	}

	public Boolean exists(String key) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		Boolean rs=false;
		try {
			rs = jedis.exists(key);
		} catch (Exception e) {
			
			//shardedJedisPool.returnBrokenResource(jedis);
		}finally{
			shardedJedisPool.returnResource(jedis);
		}
		return rs;
	}

	public Long delete(String key) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		Long rs=0L;
		try {
			rs = jedis.del(key);
		} catch (Exception e) {
			
			//shardedJedisPool.returnBrokenResource(jedis);
		}finally{
			shardedJedisPool.returnResource(jedis);
		}
		return rs;
	}
 
	public Long modifyByNum(String key, Long n) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		Long rs=0L;
		try {
			rs = jedis.incrBy(key, n);
		} catch (Exception e) {
			
			//shardedJedisPool.returnBrokenResource(jedis);
		}finally{
			shardedJedisPool.returnResource(jedis);
		}
		return rs;
	}
	
	public Long setEmptyBeanId(String key){
		ShardedJedis jedis = shardedJedisPool.getResource();
		Long rs=0L;
		try {
//			Class<T> clazz
//			String key=clazz.getName().concat("_ids");
			rs = jedis.zadd(key, 0, "0");
		} catch (Exception e) {
			
			//shardedJedisPool.returnBrokenResource(jedis);
		}finally{
			shardedJedisPool.returnResource(jedis);
		}
		return rs;
	}
	
	public  Long setEmptyBean(Class clazz){
		String key=getBeanIdsKey(clazz);
		return setEmptyBeanId(key);
	}
	
	public<T> Boolean  existEmptyBean(Class<T> clazz){
		return countBeans(clazz)>-1;
	}
	
	public Boolean  existEmptyBeanId(String allIdKey){
		return countBeans(allIdKey)>-1;
	}
	
	public <T> Boolean existsBean(Class<T> clazz, Serializable id) {
		ShardedJedis jedis = shardedJedisPool.getResource();
		try {
			
			String key=getBeanIdsKey(clazz);
			Long rs = jedis.zrank(key, String.valueOf(id));
			return (rs!=null);
		} catch (Exception e) {
			
			//shardedJedisPool.returnBrokenResource(jedis);
		}finally{
			shardedJedisPool.returnResource(jedis);
		}
		return false;
	}

	/**
	 * obj 保存在 hashMap中：<className, id, jsonVal>
	 */
	public Long setBean(Object obj) {
		Long c=0L;
		ShardedJedis jedis = shardedJedisPool.getResource();
		try {
			
			Class clazz = obj.getClass();
			Method m = clazz.getMethod("getId");
			Object idObj = m.invoke(obj);
			if(idObj==null){
				throw new Exception("id 不能为空");
			}
			
			String id=idObj.toString();
			//set id
			String idsKey=getBeanIdsKey(clazz);
			Double s = 1D;
			
			//如果id是数字，转化Id为排序的score
			if(NumberUtils.isNumber(id)){
				s=Double.valueOf(id);
			}
			
			jedis.zadd(idsKey, s, id);
			
			//set bean
			String value = JSONObject.toJSONString(obj);
			c = jedis.hset(clazz.getName(), id, value);

		} catch (Exception e) {
			e.printStackTrace();
//			//shardedJedisPool.returnBrokenResource(jedis);
		}finally{
			shardedJedisPool.returnResource(jedis);
		}
		return c;
	}
	
	/* (non-Javadoc)
	 * @see hjg.cache.RedisCacheDao#setBeans(java.util.List)
	 */
	public Long setBeans(List objList) {
		Long c=0L;
		ShardedJedis jedis = shardedJedisPool.getResource();
		try {
			
			for (Object obj : objList) {
				Class clazz = obj.getClass();
				Method m = clazz.getMethod("getId");
				Object idObj = m.invoke(obj);
				if(idObj==null){
					continue;
				}
				
				String id=idObj.toString();
				//set id
				String idsKey=getBeanIdsKey(clazz);
				
				Double s = 1D;
				
				//如果id是数字，转化Id为排序的score
				if(NumberUtils.isNumber(id)){
					s=Double.valueOf(id);
				}
				
				jedis.zadd(idsKey, s, id);
				
				//set bean
				String key =getBeanKey(clazz, id);
				String value = JSONObject.toJSONString(obj);
				jedis.hset(clazz.getName(), id, value);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			//shardedJedisPool.returnBrokenResource(jedis);
		}finally{
			shardedJedisPool.returnResource(jedis);
		}
		return c;
	}

	public <T> T getBeanById(Class<T> clazz, Serializable id) {
		List<T> list = getBeansByIds(clazz, id);
		if(list.isEmpty()){
			return null;
		}else{
			return list.get(0);
		}
	}


	public <T> List<T> getBeansByIds(Class<T> clazz, Serializable... ids) {
		List<T> rs=new ArrayList<T>();
		ShardedJedis jedis = shardedJedisPool.getResource();
		try {
			for (Serializable id : ids) {
				String value = jedis.hget(clazz.getName(), String.valueOf(id));
				if(StringUtils.isNotBlank(value)){
					rs.add(JSONObject.parseObject(value, clazz));
				}
			}
			
		} catch (Exception e) {
			
			//shardedJedisPool.returnBrokenResource(jedis);
		}finally{
			shardedJedisPool.returnResource(jedis);
		}
		return rs;
	}

	public <T> List<T> getAllBean(Class<T> clazz) {
		List<T> rs=new ArrayList<T>();
		ShardedJedis jedis = shardedJedisPool.getResource();
		try {
			
			Map<String, String > allBeanMap = jedis.hgetAll(clazz.getName());
			
			String allIdKey=getBeanIdsKey(clazz);
			Set<String> allIds= jedis.zrange(allIdKey, 1, -1);
			for (Serializable id : allIds) {
				String value = jedis.hget(clazz.getName(), String.valueOf(id));
				
				//json -> bean
				if(StringUtils.isNotBlank(value)){
					rs.add(JSONObject.parseObject(value, clazz));
				}
			}
			
		} catch (Exception e) {
			
			//shardedJedisPool.returnBrokenResource(jedis);
		}finally{
			shardedJedisPool.returnResource(jedis);
		}
		return rs;
	}

	public <T> List<T> getBeans(Class<T> clazz, Integer start, Integer end) {
		List<T> rs=new ArrayList<T>();
		ShardedJedis jedis = shardedJedisPool.getResource();
		try {
			String allIdKey=getBeanIdsKey(clazz);
			Set<String> allIds= jedis.zrange(allIdKey, 1+start.longValue(), 1+end.longValue());
			for (Serializable id : allIds) {
				String key = getBeanKey(clazz, id);
				String value = jedis.get(key);
				if(StringUtils.isNotBlank(value)){
					rs.add(JSONObject.parseObject(value, clazz));
				}
			}
			
		} catch (Exception e) {
			
			//shardedJedisPool.returnBrokenResource(jedis);
		}finally{
			shardedJedisPool.returnResource(jedis);
		}
		return rs;
	}
	
	public <T> Long countBeans(Class<T> clazz) {
		String allIdKey = getBeanIdsKey(clazz);
		return countBeans(allIdKey);
	}
	
	public Long countBeans(String allIdKey) {
		Long c=0L;
		ShardedJedis jedis = shardedJedisPool.getResource();
		try {
			c= jedis.zcard(allIdKey)-1;
			
		} catch (Exception e) {
			
			//shardedJedisPool.returnBrokenResource(jedis);
		}finally{
			shardedJedisPool.returnResource(jedis);
		}
		return c;
	}
	
	public<T> Long delBean(Serializable id, Class<T> clazz){
		Long c=0L;
		ShardedJedis jedis = shardedJedisPool.getResource();
		try {
			String allIdKey=getBeanIdsKey(clazz);
			c=jedis.zrem(allIdKey, String.valueOf(id));
			
			String key=getBeanKey(clazz, id);
			jedis.del(key);
			
		} catch (Exception e) {
			
			//shardedJedisPool.returnBrokenResource(jedis);
		}finally{
			shardedJedisPool.returnResource(jedis);
		}
		return c;
	}
 
	public <T> Long delBeanAll(Class<T> clazz){
		Long c=0L;
		ShardedJedis jedis = shardedJedisPool.getResource();
		try {
			String allIdKey=getBeanIdsKey(clazz);
			Set<String> ids = jedis.zrange(allIdKey, 1, -1);
			for (String id : ids) {
				String key=getBeanKey(clazz, id);
				jedis.del(key);
			}
			
			c=jedis.del(allIdKey);
			
			jedis.zadd(allIdKey, 0, "0");//加入空对象Id

		} catch (Exception e) {
			e.printStackTrace();
			//shardedJedisPool.returnBrokenResource(jedis);
		}finally{
			shardedJedisPool.returnResource(jedis);
		}
		return c;
	}

	public  Long setBeanId(String key, Serializable id) {
		Long rs=0L;
		ShardedJedis jedis = shardedJedisPool.getResource();
		try {
			jedis.zadd(key, 1D, String.valueOf(id));			
		} catch (Exception e) {
			//shardedJedisPool.returnBrokenResource(jedis);
		}finally{
			shardedJedisPool.returnResource(jedis);
		}
		return rs;
	}

	public  List<String> getBeanIds(String key, Integer start, Integer end) {
		List<String> rs=new ArrayList<String>();
		ShardedJedis jedis = shardedJedisPool.getResource();
		try {
			if(end !=-1){//
				++end;
			}
			++start;
			Collection<String> ids= jedis.zrange(key, start.longValue(), end.longValue());
			rs.addAll(ids);
			
		} catch (Exception e) {
			
			//shardedJedisPool.returnBrokenResource(jedis);
		}finally{
			shardedJedisPool.returnResource(jedis);
		}
		return rs;
	}

	public Long delBeanId(String key, Serializable id) {
		Long rs=0L;
		ShardedJedis jedis = shardedJedisPool.getResource();
		try {
			jedis.zrem(key, String.valueOf(id));			
		} catch (Exception e) {
			//shardedJedisPool.returnBrokenResource(jedis);
		}finally{
			shardedJedisPool.returnResource(jedis);
		}
		return rs;
	}

	public <T> String getBeanKey(Class<T> clazz) {
		String key = clazz.getName();
		return key;
	}

	public <T> String getBeanIdsKey(Class<T> clazz) {
		String key = clazz.getName().concat("_ids");
		return key;
	}

	@Override
	public <T> String getBeanKey(Class<T> clazz, Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

}
