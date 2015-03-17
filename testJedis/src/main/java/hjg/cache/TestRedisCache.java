package hjg.cache;

import hjg.testJedis.App;
import hjg.testJedis.Person;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

public class TestRedisCache {

	public static void main(String[] args) throws Exception {
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
		RedisCacheDao cache = new RedisCacheDaoImpl();
		cache.setShardedJedisPool(shardedJedisPool);
		
		cache.delBeanAll(Person.class);
		System.out.println(cache.countBeans(Person.class));
		if(!cache.existEmptyBean(Person.class)){
			cache.setEmptyBean(Person.class);
		}else{
			System.out.println("存在空对象！");
		}
		System.out.println(cache.countBeans(Person.class));
//		List list = new ArrayList();
		for (int i = 0; i < 5; i++) {
			Person p = new Person(i, "person"+i);
//			list.add(p);
			String rs = cache.setBean(p);
			System.out.println(rs);
		}
//		cache.setBeans(list);
		
		
//		Person p = new Person(1, "person1");
//		cache.setBean(p);
//		System.out.println(cache.getAllBean(Person.class));
//		
//		p = new Person(2, "person2");
//		cache.setBean( p);
//		System.out.println(cache.getAllBean(Person.class));
//		p = new Person(3, "person3");
//		cache.setBean(p);
//		System.out.println(cache.getAllBean(Person.class));
//		p = new Person(4, "person4");
//		cache.setBean(p);
		System.out.println(cache.getAllBean(Person.class));
//		System.out.println(cache.getBeans(Person.class, 0, 1));
//		System.out.println(cache.getBeans(Person.class, 2, 5));
		
//		System.out.println(cache.countBeans(Person.class));
		
//		System.out.println(cache.getBeans(Person.class, 0, 1));
		
	}
}
