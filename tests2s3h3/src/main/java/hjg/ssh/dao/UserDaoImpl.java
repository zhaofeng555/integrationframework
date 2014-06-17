package hjg.ssh.dao;

import hjg.ssh.entity.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private HibernateTemplate template;

	@SuppressWarnings("unchecked")
	@Override
	public List<User> list() {
		return template.loadAll(User.class);
	}

	@Override
	public User save(User user) {
		template.saveOrUpdate(user);
		return user;
	}

}
