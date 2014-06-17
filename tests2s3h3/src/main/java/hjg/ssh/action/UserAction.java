package hjg.ssh.action;

import hjg.ssh.entity.User;
import hjg.ssh.service.UserService;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("struts-default")
@Namespace("/user")

@Results({ @Result(name = "success", location = "/views/success.jsp"),@Result(name = "error", location = "/views/error.jsp") })
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class UserAction extends ActionSupport {
	private static Log log = LogFactory.getLog(UserAction.class);
	private static final long serialVersionUID = 3911407715057497558L;
	private User user;
	private List<User> users;
	@Autowired
	private UserService userService;
	
	public UserAction(){
		log.info("user action...................");
	}
	
	@Action(value="list")
	public String list(){
		users = userService.list();
		return SUCCESS;
	}
	@Action(value="error")
	public String error(){
		 throw new RuntimeException("测试错误");
	}
	
	@Action(value = "save", results = { @Result(name = "success", location = "/result.jsp") })
	public String save(){
		user =userService.save(user);
		return  SUCCESS;
	}

	public User getUser() {
		return user;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
