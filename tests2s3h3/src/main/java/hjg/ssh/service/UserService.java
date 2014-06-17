package hjg.ssh.service;

import hjg.ssh.entity.User;

import java.util.List;

public interface UserService {

	List<User> list();
	User save(User user);
	
}
