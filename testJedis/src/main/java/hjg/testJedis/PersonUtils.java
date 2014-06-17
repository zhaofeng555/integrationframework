package hjg.testJedis;

import redis.clients.jedis.Jedis;

public class PersonUtils {

	private Jedis jedis;

	public PersonUtils (Jedis jedis) {
		this.jedis = jedis;
	}

	public void setObject() {
		Person person = new Person(100, "alan");
		jedis.set("person:100".getBytes(), SerializeUtil.serialize(person));
		person = new Person(101, "bruce");
		jedis.set("person:101".getBytes(), SerializeUtil.serialize(person));
	}

	public Person getObject(int id) {
		byte[] person = jedis.get(("person:" + id).getBytes());
		return (Person) SerializeUtil.unserialize(person);
	}

	
}
