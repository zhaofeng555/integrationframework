package com.hjg.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hjg.model.User;
import com.hjg.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("login.do")
	public String login(HttpServletRequest request){
		request.setAttribute("name", "world");
		request.setAttribute("age", "5");
		
		List<String> list = new ArrayList<String>();
		list.add("hello1");
		list.add("hello2");
		list.add("hello3");
		list.add("hello4");
		request.setAttribute("list", list);
		List<User> us = userService.list();
		System.out.println(us);
		request.setAttribute("us", us);
		return "user";
	}
	
}
