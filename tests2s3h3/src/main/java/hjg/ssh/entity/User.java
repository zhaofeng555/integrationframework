package hjg.ssh.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_user", catalog="s2s3h3")
public class User implements Serializable{

	private static final long serialVersionUID = -3085389349446659198L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	@Column(name = "username", length = 50)
	private String username;
	@Column(length=50)
	private String password;
	public Integer getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + "]";
	}
	
}
