package hjg.cache2;

import java.io.Serializable;
import java.util.List;

import redis.clients.jedis.ShardedJedisPool;

public interface RedisCacheDao {
	public void setShardedJedisPool(ShardedJedisPool shardedJedisPool);

	public String get(String key);

	public Boolean set(String key, String value);

	public Boolean exists(String key);

	public Long delete(String key);

	public Long modifyByNum(String key, Long n);

	public Long setEmptyBeanId(String key);

	public Long setEmptyBean(Class clazz);

	public <T> Boolean existEmptyBean(Class<T> clazz);

	public Boolean existEmptyBeanId(String allIdKey);

	public <T> Boolean existsBean(Class<T> clazz, Serializable id);

	public Long setBean(Object obj);

	public Long setBeans(List objList);

	public <T> T getBeanById(Class<T> clazz, Serializable id);

	public <T> List<T> getBeansByIds(Class<T> clazz, Serializable... ids);

	public <T> List<T> getAllBean(Class<T> clazz);

	public <T> List<T> getBeans(Class<T> clazz, Integer start, Integer end);

	public <T> Long countBeans(Class<T> clazz);

	public Long countBeans(String allIdKey);

	public <T> Long delBean(Serializable id, Class<T> clazz);

	public <T> Long delBeanAll(Class<T> clazz);

	public Long setBeanId(String key, Serializable id);

	public List<String> getBeanIds(String key, Integer start, Integer end);

	public Long delBeanId(String key, Serializable id);

	public <T> String getBeanKey(Class<T> clazz, Serializable id);

	public <T> String getBeanIdsKey(Class<T> clazz);

}
