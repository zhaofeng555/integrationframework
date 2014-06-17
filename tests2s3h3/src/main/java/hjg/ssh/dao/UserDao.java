package hjg.ssh.dao;

import hjg.ssh.entity.User;

import java.util.List;

public interface UserDao {

	public List<User> list();
	public User save(User user);
	
}
