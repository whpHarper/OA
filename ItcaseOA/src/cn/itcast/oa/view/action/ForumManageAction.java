package cn.itcast.oa.view.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.Forum;

@Controller
@Scope("prototype")
public class ForumManageAction extends BaseAction<Forum> {
	
	public String list() throws Exception{
		List<Forum> forumList=forumService.findAll();
		ActionContext.getContext().put("forumList", forumList);
		return "list";
	}
	
	public String delete() throws Exception{
		forumService.delete(model.getId());
		return "toList";
	}
	
	public String addUI() throws Exception{
		return "saveUI";
	}
	
	public String add() throws Exception{
		forumService.save(model);
		return "toList";
	}
	
	public String editUI() throws Exception{
		Forum forum=forumService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(forum);
		return "saveUI";
	}
	
	public String edit() throws Exception{
		Forum forum=forumService.getById(model.getId());
		forum.setDescription(model.getDescription());
		forum.setId(model.getId());
		forum.setName(model.getName());
		forum.setPosition(model.getPosition());
		
		forumService.update(forum);
		return "toList";
	}
	
	public String moveUp() throws Exception{
		forumService.moveUp(model.getId());
		return "toList";
	}
	
	public String moveDown() throws Exception{
		forumService.moveDown(model.getId());
		return "toList";
	}

}
