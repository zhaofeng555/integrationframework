package org.oham.learn.ssh.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.oham.learn.ssh.beans.TGroup;
import org.oham.learn.ssh.beans.TPerson;
import org.oham.learn.ssh.beans.TUser;
import org.oham.learn.ssh.services.TestHibernateService;
import org.oham.learn.ssh.sqlmapper.TUserMapper;
import org.springframework.context.annotation.Scope;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("ssh-default")
@Namespace("/sa")
@Scope(value="prototype")
public class TestStrutsAnnotationAction extends ActionSupport{
	private static final long serialVersionUID = -2793512785203051741L;
	
	private Map<String, Object> jsonMap;
	
	@Resource(name="TestHibernate")
	private TestHibernateService testHibernateService;
	
	@Resource
	private TUserMapper tUserMapper;
	
	private List<TPerson> persons;
	private List<TGroup> groups;
	
	@Action(value="testSACall",
			results={
				@Result(name="success", location="/WEB-INF/jsp/Main.jsp")
	})
	public String testAnnotaionCall() {
		persons = testHibernateService.getTpersonList();
		for(TPerson p : persons) {
			System.out.println(p.getName());
		}
		
		groups = testHibernateService.getTGroupList();
		for(TGroup g : groups) {
			System.out.println(g.getName());
		}
		return "success";
	}
	
	
	@Action(value="testSAMyBatis",
			results={
				@Result(name="success", location="/WEB-INF/jsp/Main.jsp")
	})
	public String testMyBatisCall() {
		List<TUser> list = tUserMapper.selectTUserByExample(null);
		for(TUser t : list) {
			System.out.println(t.getName());
		}
		return SUCCESS;
	}
	
	@Action(value="testJ",
			results={@Result(name="json", type="json")})
	public String testJ() {
		
		jsonMap = new HashMap<String, Object>();
		jsonMap.put("a", "aa");
		jsonMap.put("b", "b");
		return "json";
		
	}

	public Map<String, Object> getJsonMap() {
		return jsonMap;
	}


	public List<TPerson> getPersons() {
		return persons;
	}


	public void setPersons(List<TPerson> persons) {
		this.persons = persons;
	}


	public List<TGroup> getGroups() {
		return groups;
	}


	public void setGroups(List<TGroup> groups) {
		this.groups = groups;
	}
}
