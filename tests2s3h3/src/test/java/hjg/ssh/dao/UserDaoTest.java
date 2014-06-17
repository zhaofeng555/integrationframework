package hjg.ssh.dao;

import hjg.ssh.entity.User;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/*.xml" })  
public class UserDaoTest {
    @Autowired
    private UserDao userDao;
    
    @Test
    public void testGetBookList(){
        System.out.println("xiaoji");
    }
    
    @Test
    public void testList(){
    	List<User> us = userDao.list();
    	for (User u : us) {
    		System.out.println(u); 
		}
    }
    @Test
    public void testSave(){
        User u = new User();
        u.setUsername("haojg1");
        u.setPassword("hehe1");
        System.out.println(userDao.save(u));
        System.out.println("ok");
    }
}