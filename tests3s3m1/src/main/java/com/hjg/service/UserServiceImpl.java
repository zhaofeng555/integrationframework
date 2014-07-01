package com.hjg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hjg.dao.UserMapper;
import com.hjg.model.User;
import com.hjg.model.UserExample;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public List<User> list() {
		UserExample e = new UserExample();
		UserExample.Criteria ec = e.createCriteria();
		ec.andIdGreaterThan(0);
		return userMapper.selectByExample(e);
	}

}
