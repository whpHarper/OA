package cn.itcast.oa.view.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.Department;
import cn.itcast.oa.service.DepartmentService;
import cn.itcast.oa.util.departmentUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
@Controller
@Scope("prototype")
public class DepartmentAction extends BaseAction<Department>{

	private Long parentId;
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String list() throws Exception{
		List<Department> departmentList=null;
		if(parentId==null){
			departmentList=departmentService.findTopList();
		}else{
			departmentList=departmentService.finChildrenList(parentId);
			Department parent=departmentService.getById(parentId);
			ActionContext.getContext().put("parent", parent);
		}	
				
		ActionContext.getContext().put("departmentList", departmentList);
		return "list";
	}
	
	public String delete() throws Exception{
		departmentService.delete(model.getId());
		return "toList";
	}
	
	public String addUI() throws Exception{
		List<Department> topList=departmentService.findTopList();
		List<Department> departmentList=departmentUtils.getAllDepartments(topList);
		ActionContext.getContext().put("departmentList", departmentList);
		
		return "saveUI";
	}
	
	public String add() throws Exception{
		Department parent=departmentService.getById(parentId);
		model.setParent(parent);
		
		departmentService.save(model);
		return "toList";
	}
	
	public String editUI() throws Exception{
		//准备数据
		List<Department> topList=departmentService.findTopList();
		List<Department> departmentList=departmentUtils.getAllDepartments(topList);
		
		ActionContext.getContext().put("departmentList", departmentList);
		
		Department department=departmentService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(department);
		
		if(department.getParent()!=null){
			parentId=department.getParent().getId();
		}
		
		return "saveUI";
	}
	
	public String edit() throws Exception{
		Department department=new Department();
		
		department.setId(model.getId());
		department.setDescription(model.getDescription());
		department.setName(model.getName());
		department.setParent(departmentService.getById(parentId));
		
		departmentService.update(department);
		return "toList";
	}


	
	
}
