package hjg.ssh.service;

import hjg.ssh.dao.UserDao;
import hjg.ssh.entity.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	
	@Override
	public List<User> list() {
		return userDao.list();
	}

	@Override
	public User save(User user) {
		return userDao.save(user);
	}

	
}
