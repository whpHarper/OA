package cn.itcast.oa.view.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.oa.domain.Role;
import cn.itcast.oa.service.RoleService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class RoleAction extends ActionSupport implements ModelDriven<Role>{
	
	@Resource
	private RoleService roleService;
	
	private Role model=new Role();
	public Role getModel() {
		return model;
	}
	
	/*private long id;
	private String name;
	private String description;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}*/

	public String list(){
		List<Role> list=roleService.queryAll();
		ActionContext.getContext().put("roleList", list);
		return "list";
	}
	
	public String delete(){
		roleService.deleteById(model.getId());
		return "toList";
	}
	
	public String addUI(){
		return "saveUI";
	}
	
	public String add(){
		/*Role role=new Role();
		role.setName(model.getName());
		role.setDescription(model.getDescription());
		roleService.add(role);*/
		roleService.add(model);
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
		roleService.edit(role);
		return "toList";
	}

	
}
