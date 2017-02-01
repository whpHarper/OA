package cn.itcast.oa.view.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.oa.domain.Role;
import cn.itcast.oa.service.RoleService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class RoleAction extends ActionSupport {
	
	@Resource
	private RoleService roleService;
	
	private long id;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String list(){
		List<Role> list=roleService.queryAll();
		ActionContext.getContext().put("roleList", list);
		return "list";
	}
	
	public String delete(){
		roleService.deleteById(id);
		return "toList";
	}
	
	public String addUI(){
		return "addUI";
	}
	
	public String add(){
		return "toList";
	}
	
	public String editUI(){
		
		return "editUI";
	}
	
	public String edit(){
		return "toList";
	}
}
