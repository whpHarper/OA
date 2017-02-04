package cn.itcast.oa.view.action;

import java.util.HashSet;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.Department;
import cn.itcast.oa.domain.Role;
import cn.itcast.oa.domain.User;
import cn.itcast.oa.util.departmentUtils;

@SuppressWarnings("serial")
@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {
	
	private Long departmentId;
	private Long[] roleIds;
	
	public Long[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Long[] roleIds) {
		this.roleIds = roleIds;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	

	public String list() throws Exception{
		List<User> userList=userService.findAll();
		ActionContext.getContext().put("userList", userList);
		return "list";
	}
	
	public String delete() throws Exception{
		userService.delete(model.getId());
		return "toList";
	}
	
	public String addUI() throws Exception{
		List<Department> topList=departmentService.findTopList();
		List<Department> departmentList=departmentUtils.getAllDepartments(topList);
		ActionContext.getContext().put("departmentList", departmentList);
		
		List<Role> roleList=roleService.findAll();
		ActionContext.getContext().put("roleList", roleList);
		return "saveUI";
	}
	
	public String add() throws Exception{
		model.setDepartment(departmentService.getById(departmentId));
		
		List<Role> roleList=roleService.getByIds(roleIds);
		
		model.setRoles(new HashSet<Role>(roleList));
		
		String md5Digest=DigestUtils.md5Hex("1234");
		model.setPassword(md5Digest);
		
		userService.save(model);
		
		return "toList";
	}
	
	public String editUI() throws Exception{
		List<Department> topList=departmentService.findTopList();
		List<Department> departmentList=departmentUtils.getAllDepartments(topList);
		ActionContext.getContext().put("departmentList", departmentList);
		
		List<Role> roleList=roleService.findAll();
		ActionContext.getContext().put("roleList", roleList);
		
		User user=userService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(user);
		
		if(user.getDepartment().getId()!=null){
			departmentId=user.getDepartment().getId();
		}
		
		if(user.getRoles()!=null){
			roleIds=new Long[user.getRoles().size()];
			int index=0;
			for(Role role:user.getRoles()){
				roleIds[index++]=role.getId();
			}
		}
		
		return "saveUI";
	}
	
	public String edit() throws Exception{
		
		User user=userService.getById(model.getId());
		
		user.setLoginName(model.getLoginName());
		user.setDescription(model.getDescription());
		user.setEmail(model.getEmail());
		user.setGender(model.getGender());
		user.setName(model.getName());
		user.setPhoneNumber(model.getPhoneNumber());
		
		user.setDepartment(departmentService.getById(departmentId));
		List<Role> roleList=roleService.getByIds(roleIds);
		user.setRoles(new HashSet(roleList));
		
		userService.update(user);
		
		return "toList";
	}

	public String initPassword() throws Exception{
		
		User user=userService.getById(model.getId());
		
		String md5Digest=DigestUtils.md5Hex("1234");
		user.setPassword(md5Digest);
		
		userService.update(user);
		
		return "toList";
	}
	
	public String loginUI() throws Exception{
		return "loginUI";
	}
	
	public String login() throws Exception{
		User user=userService.findByLoginNameAndPassword(model.getLoginName(), model.getPassword());
		if(user==null){
			addFieldError("name","用户名或者密码不正确");
			return "loginUI";
		}else{
			ActionContext.getContext().getSession().put("user", user);
			return "toLogin";
		}

	}
	
	public String logout() throws Exception{
		ActionContext.getContext().getSession().remove("user");
		return "loginout";
	}
}
