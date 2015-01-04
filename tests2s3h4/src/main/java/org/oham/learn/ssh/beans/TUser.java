package org.oham.learn.ssh.beans;

import java.io.Serializable;

public class TUser implements Serializable {
	private static final long serialVersionUID = -5408631726342120380L;

	private int id;
	private String name;
	private String resume;
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
	public String getResume() {
		return resume;
	}
	public void setResume(String resume) {
		this.resume = resume;
	}
}
