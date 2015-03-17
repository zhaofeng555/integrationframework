package hjg.testJedis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import com.alibaba.fastjson.JSONObject;

public class TestSharedJedisPool {

	public static void main(String[] args) {
		
		ShardedJedis shardedJedis=null;// 切片额客户端连接

		// 池基本配置
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(20);
		config.setMaxIdle(5);
		config.setMaxWaitMillis(1000l);
		config.setTestOnBorrow(false);

		// slave链接
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		JedisShardInfo info = new JedisShardInfo("127.0.0.1", 6379, "master");
//		info.setPassword("ChineseAll*()");
		shards.add(info);

		// 构造池
		ShardedJedisPool shardedJedisPool = new ShardedJedisPool(config, shards);
		
		shardedJedis = shardedJedisPool.getResource();
		shardedJedis.del("hello");
		
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			shardedJedis.hset("hello", "hello"+i, "hello"+i);
		}
		System.out.println("hmap存储100000："+(System.currentTimeMillis()-start));
		start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			shardedJedis.hget("hello", "hello"+i);
		}
		System.out.println("hmap获取100000："+(System.currentTimeMillis()-start));
		start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
//			shardedJedis.hget("hello", "hello"+i);
			shardedJedis.hdel("hello", "hello"+i);
		}
		System.out.println("hmap删除100000："+(System.currentTimeMillis()-start));
		
		start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			shardedJedis.set("world"+i, "world"+i);
		}
		System.out.println("存储100000："+(System.currentTimeMillis()-start));
		start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			shardedJedis.get("world"+i);
		}
		System.out.println("获取100000："+(System.currentTimeMillis()-start));
		
		
		start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			shardedJedis.del("world"+i);
		}
		System.out.println("删除100000："+(System.currentTimeMillis()-start));
		
//		System.out.println(shardedJedis.zrange("hello", 0, -1));
//		System.out.println(shardedJedis.zcard("hello"));
//System.out.println(Person.class.getName());
//		System.out.println(shardedJedis.exists(Person.class.getName()));
//		 System.out.println(shardedJedis.hexists(Person.class.getName(), "0"));
//		
//		System.out.println(shardedJedis.del(Person.class.getName()));
////		shardedJedis.hscan(key, cursor)
//		System.out.println(shardedJedis.hscan(Person.class.getName(), 0).getResult());
//		shardedJedis.hset(Person.class.getName(), "0", "{}");
//		System.out.println(shardedJedis.hscan(Person.class.getName(), 0).getResult());
//		System.out.println(shardedJedis.hlen(Person.class.getName()));
//		
//		Person p = new Person(1, "person1");
//		String pStr = JSONObject.toJSONString(p);
//		shardedJedis.hset(p.getClass().toString(), p.getId().toString(), pStr);
//		System.out.println(shardedJedis.hgetAll(Person.class.getName()));
//		System.out.println(shardedJedis.hlen(Person.class.getName()));
//		
//		p = new Person(2, "person2");
//		pStr = JSONObject.toJSONString(p);
//		shardedJedis.hset(p.getClass().toString(), p.getId().toString(), pStr);
//		
//		System.out.println(shardedJedis.hgetAll(Person.class.getName()));
//		System.out.println(shardedJedis.hlen(Person.class.getName()));
//		
//		
//		
//		System.out.println(shardedJedis.del(Person.class.getName()));
//		
//		System.out.println("Person.class.getName() size = "+shardedJedis.zcard(Person.class.getName()));
//
//		if(shardedJedis.zcard(Person.class.getName()) == 0){
//			System.out.println("从来没有查过");
//		}
//		shardedJedis.zadd(Person.class.getName(), 0, "{}");
//		if(shardedJedis.zcard(Person.class.getName()) == 1){
//			System.out.println(" 数据库里确实没有");
//		}
//		
//		Person p = new Person(1, "person1");
//		String pStr = JSONObject.toJSONString(p);
//		shardedJedis.zadd(Person.class.getName(), 1, pStr);
//		
//		p = new Person(2, "person2");
//		pStr = JSONObject.toJSONString(p);
//		shardedJedis.zadd(Person.class.getName(), 2, pStr);
//		
//		shardedJedis.zadd("hello", 1, "h1");
//		shardedJedis.zadd("hello", 1, "h2");
//		shardedJedis.zadd("hello", 1, "h3");
//		
//		System.out.println(shardedJedis.zrank("hello", "h1"));
//		System.out.println(shardedJedis.zrank("hello", "h4"));
//		System.out.println(shardedJedis.zrevrank("hello", "h1"));
//		System.out.println(shardedJedis.zrevrank("hello", "h4"));
//		System.out.println(shardedJedis.zrange("hello", 0, -1));
//		
//		System.out.println("byscore:");
//		System.out.println(shardedJedis.zrangeByScore(Person.class.getName(), 1, 1));
//		
//		Set<String> ps = shardedJedis.zrange(Person.class.getName(), 0, -1);
//		String[] psArr = ps.toArray(new String[]{});
//		for (int i = 0; i < psArr.length; i++) {
//			Person tmp = JSONObject.parseObject(psArr[i], Person.class);
//			System.out.println(tmp);
//		}
//		
//		System.out.println(shardedJedis.zrange(Person.class.getName(), 1, -1));
		
		shardedJedisPool.returnResource(shardedJedis);
	}
	
	ShardedJedis shardedJedis=null;
	public void saveBeanId(Serializable id, Class clazz){
		String key=clazz.getName().concat("_ids");
		 shardedJedis.zadd(key, 1, String.valueOf(id));
	}
	public void saveEmptyBeanId(Class clazz){
		String key=clazz.getName().concat("_ids");
		 shardedJedis.zadd(key, 0, "0");
	}
	
	public void saveBean(Serializable id, Object obj){
		String value = JSONObject.toJSONString(obj);
		 shardedJedis.set(obj.getClass().getName().concat("_").concat(String.valueOf(id)), value);
	}
	
	public void delByKey(String key){
		shardedJedis.del(key);
	}
	public void delBeanId(Serializable id, Class clazz){
		String key=clazz.getName().concat("_ids");
		 shardedJedis.zrem(key, String.valueOf(id));
	}
	
	public void delBean(Serializable id, Class clazz){
		String key=clazz.getName().concat("_").concat(String.valueOf(id));
		 shardedJedis.del(key);
	}
	public void delBeanAll(Class clazz){
		String key=clazz.getName().concat("_ids");
		Set<String> ids = shardedJedis.zrange(key, 1, -1);
		for (String id : ids) {
			delBean(id, clazz);
		}
		delByKey(key);
		saveEmptyBeanId(clazz);
	}
	
	public Boolean  existEmptyBean(Class clazz){
		String key=clazz.getName().concat("_ids");
		Boolean exist= shardedJedis.zcard(key)>0;
		 return false;
	}
	
	public Boolean  existBean(Serializable id, Class clazz){
		String key=clazz.getName().concat("_ids");
		Long rank= shardedJedis.zrank(key, String.valueOf(id));
		return rank!=null;
	}
	
	public<T> Set<String> getBeanIds(Class<T> clazz){
		if(!existEmptyBean(clazz)){
			return new HashSet<String>();
		}
		String key=clazz.getName().concat("_ids");
		Set<String> ids= shardedJedis.zrange(key, 1, -1);
		return ids;
	}
	public<T> Set<String> getBeanIds(Class<T> clazz, Integer start, Integer end){
		if(!existEmptyBean(clazz)){
			return new HashSet<String>();
		}
		String key=clazz.getName().concat("_ids");
		Set<String> ids= shardedJedis.zrange(key, start+1, end+1);
		return ids;
	}
	public<T> T getBean(Serializable id, Class<T> clazz){
		T tmp=null;
		if(!existEmptyBean(clazz)){
			return tmp;
		}
		String key=clazz.getName().concat("_").concat(String.valueOf(id));
		String val= shardedJedis.get(key);
		if(val==null){
			return tmp;
		}
		tmp = JSONObject.parseObject(val, clazz);
		return tmp;
	}
	public<T> List<T> getBeans(Set<Serializable> ids, Class<T> clazz){
		List<T> tmp=null;
		if(!existEmptyBean(clazz)){
			return tmp;
		}
		String key=clazz.getName().concat("_").concat(String.valueOf(""));
		String val= shardedJedis.get(key);
		if(val==null){
			return tmp;
		}
		return tmp;
	}
	public<T>List<T> getBeanAll(Class<T>clazz){
		List<T> rs = new ArrayList<T>();
		Set<String>ids=getBeanIds(clazz);
		for (String id : ids) {
			rs.add(getBean(id, clazz));
		}
		return rs;
	}
	
}