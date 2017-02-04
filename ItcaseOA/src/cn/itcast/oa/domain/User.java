package cn.itcast.oa.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.opensymphony.xwork2.ActionContext;

/**
 * 用户
 * @author tyg
 * 
 */
public class User implements java.io.Serializable {
	private Long id;
	private Department department;
	private Set<Role> roles = new HashSet<Role>();

	private String loginName; // 登录名
	private String password; // 密码
	private String name; // 真实姓名
	private String gender; // 性别
	private String phoneNumber; // 电话号码
	private String email; // 电子邮件
	private String description; // 说明

	public boolean hasPrivilegeByUrl(String privUrl) {
		//超级管理员有所有权限
		if(isAdmin()){
			return true;
		}
		
		int po=privUrl.indexOf("?");
		if(po>-1){
			privUrl=privUrl.substring(0,po);
		}
		
		if(privUrl.endsWith("UI")){
			privUrl=privUrl.substring(0, privUrl.length()-2);
		}
		
		//如果URL不需要控制，则登录用户就可以使用
		Collection<String> allPrivilegeUrls=(Collection<String>) ActionContext.getContext().getApplication().get("allPrivilegeUrls");
		if(!allPrivilegeUrls.contains(privUrl)){
			return true;
		}else{
			//用于判断普通用户是否具有权限
			for(Role role:roles){
				for(Privilege priv:role.getPrivileges()){
					if(privUrl.equals(priv.getUrl())){
						return true;
					}
				}
			}
			return false;
		}
		
	}
	
	/*
	 * 判断用户是否含有指定的名称
	 * */
	public boolean hasPrivilegeByName(String name){
		
		//超级管理员有所有权限
		if(isAdmin()){
			return true;
		}
		
		//用于判断普通用户是否具有权限
		for(Role role:roles){
			for(Privilege priv:role.getPrivileges()){
				if(priv.getName().equals(name)){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isAdmin(){
		return "admin".equals(loginName);
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	

}
