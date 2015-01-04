package org.oham.learn.ssh.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_group")
public class TGroup implements Serializable {
	private static final long serialVersionUID = 2177231731375781687L;

	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="name", nullable= true)
	private String name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
