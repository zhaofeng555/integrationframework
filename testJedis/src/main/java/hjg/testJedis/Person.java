package hjg.testJedis;

import java.io.Serializable;

public class Person implements Serializable {
	private static final long serialVersionUID = -6381928603792619188L;

	private Integer id;
	private String name;

	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Person(){}
	public Person(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + "]";
	}
}