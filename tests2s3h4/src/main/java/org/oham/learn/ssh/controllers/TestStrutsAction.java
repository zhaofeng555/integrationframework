package org.oham.learn.ssh.controllers;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.oham.learn.ssh.beans.TGroup;
import org.oham.learn.ssh.beans.TPerson;
import org.oham.learn.ssh.beans.TUser;
import org.oham.learn.ssh.services.TestHibernateService;
import org.oham.learn.ssh.sqlmapper.TUserMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionSupport;


/**
 * 此处的action由spring去负责实例化，默认是单例的，
 * 为迎合struts的action，现设置器scope为protoype
 */
@Service(value="testStrutsAction")
@Scope("prototype")
public class TestStrutsAction extends ActionSupport {
	private static final long serialVersionUID = 3362122496775495186L;
	
	@Resource(name="TestHibernate")
	private TestHibernateService testHibernateService;
	
	@Resource
	private TUserMapper tUserMapper;
	
	private Map<String, Object> jsonMap;
	
	/**
	 * 该action为xml方式配置的，此处测试其方法的调用，
	 * 用Hibernate执行了查询，此处的映射配置基于xml 
	 */
	public String testCall() {
		List<TPerson> list = testHibernateService.getTpersonList();
		for(TPerson p : list) {
			System.out.println(p.getName());
		}
		
		return "success";
	}
	
	/**
	 * 用Hibernate执行了查询，此处的映射配置基于annotation 
	 */
	public String testAnnotationCall() {
		List<TGroup> list = testHibernateService.getTGroupList();
		
		for(TGroup g : list) {
			System.out.println(g.getName());
		}
		
		return "success";
	}
	
	
	/**
	 * 测试json interceptor是否起作用
	 */
	public String testJsonCall() {
		jsonMap = new HashMap<String, Object>();
		jsonMap.put("a", "aa");
		jsonMap.put("b", "b");
		return "json";
	}
	
	/**
	 * 测试MyBatis 整合
	 */
	public String testMybatisCall() {
		TUser user = new TUser();
		user.setId(1);
		user.setName("oham");
		List<TUser> list = tUserMapper.selectTUserByExample(user);
		for(TUser t : list) {
			System.out.println(t.getName());
		}
		return SUCCESS;
	}

	public Map<String, Object> getJsonMap() {
		return jsonMap;
	}
}