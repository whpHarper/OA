package cn.itcast.oa.base;

import java.lang.reflect.ParameterizedType;

import javax.annotation.Resource;

import cn.itcast.oa.domain.Department;
import cn.itcast.oa.domain.User;
import cn.itcast.oa.service.DepartmentService;
import cn.itcast.oa.service.ForumService;
import cn.itcast.oa.service.PrivilegeService;
import cn.itcast.oa.service.ReplyService;
import cn.itcast.oa.service.RoleService;
import cn.itcast.oa.service.TopicService;
import cn.itcast.oa.service.UserService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings({ "unchecked", "serial" })
public class BaseAction<T> extends ActionSupport implements ModelDriven<T>{
	//==============model重写=========================
	protected T model;
	public BaseAction(){
		ParameterizedType pt=(ParameterizedType)this.getClass().getGenericSuperclass();
		
		Class<T> clazz=(Class<T>)pt.getActualTypeArguments()[0];
		try {
			model=clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	public T getModel() {
		return model;
	}
	
	protected int pageNum=1;
	protected int pageSize=10;
	
	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	
	//===============service单例实现====================
	@Resource
	protected DepartmentService departmentService;
	
	@Resource
	protected RoleService roleService;
	
	@Resource
	protected UserService userService;
	
	@Resource
	protected PrivilegeService privilegeService;
	
	@Resource
	protected ForumService forumService;
	
	@Resource
	protected TopicService topicService;
	
	@Resource
	protected ReplyService replyService;
	
	protected User getCurrentUser(){
		return (User) ActionContext.getContext().getSession().get("user");
	}
}
