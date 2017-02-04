package cn.itcast.oa.view.action;

import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.Privilege;
import cn.itcast.oa.domain.Role;
import cn.itcast.oa.service.RoleService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role>{

	private Long[] privilegeIds;
	
	public Long[] getPrivilegeIds() {
		return privilegeIds;
	}

	public void setPrivilegeIds(Long[] privilegeIds) {
		this.privilegeIds = privilegeIds;
	}

	public String list(){
		List<Role> list=roleService.findAll();
		ActionContext.getContext().put("roleList", list);
		return "list";
	}
	
	public String delete(){
		roleService.delete(model.getId());
		return "toList";
	}
	
	public String addUI(){
		return "saveUI";
	}
	
	public String add(){
		roleService.save(model);
		return "toList";
	}
	
	public String editUI(){
		Role role=roleService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(role);
		return "saveUI";
	}
	
	public String edit(){
		Role role=new Role();
		role.setId(model.getId());
		role.setDescription(model.getDescription());
		role.setName(model.getName());
		roleService.update(role);
		return "toList";
	}

	public String setPrivilegeUI(){
		Role role=roleService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(role);
		
		//显示回显数据
		
		if(role.getPrivileges()!=null){
			int index=0;
			privilegeIds=new Long[role.getPrivileges().size()];
			for(Privilege privilege:role.getPrivileges()){
				privilegeIds[index++]=privilege.getId();
			}
		}
		
		//显示所有角色
		List<Privilege> privileges=privilegeService.findAll();
		ActionContext.getContext().put("privilegeList", privileges);

		return "setPrivilegeUI";
	}
	
	public String setPrivilege(){
		Role role=roleService.getById(model.getId());
		
		//获取privilege
		List<Privilege> privilegeList=privilegeService.getByIds(privilegeIds);
		role.setPrivileges(new HashSet<Privilege>(privilegeList));
		
		roleService.update(role);
		return "toList";
	}
}
