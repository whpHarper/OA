package cn.itcast.oa.view.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.domain.Reply;
import cn.itcast.oa.domain.Topic;
import cn.itcast.oa.util.QueryHelper;

@SuppressWarnings("serial")
@Controller
@Scope("prototype")
public class TopicAction extends BaseAction<Topic> {

	private Long forumId;
	
	public Long getForumId() {
		return forumId;
	}

	public void setForumId(Long forumId) {
		this.forumId = forumId;
	}

	public String show(){
		Topic topic=topicService.getById(model.getId());
		ActionContext.getContext().put("topic", topic);
		
		/*List<Reply> replyList=replyService.findByTopic(topic);
		ActionContext.getContext().put("replyList", replyList);
		*/
		
		//准备分页信息
		/*PageBean pageBean=replyService.getPageBean(pageNum,pageSize,topic);
		ActionContext.getContext().getValueStack().push(pageBean);*/
		
		//准备分页信息
		/*String hql="From Reply r where r.topic=? order by r.postTime ASC";
		List parameters=new ArrayList();
		parameters.add(topic);
		
		PageBean pageBean=replyService.getPageBean(pageNum, pageSize, hql, parameters);
		ActionContext.getContext().getValueStack().push(pageBean);
		*/
		new QueryHelper(Reply.class,"r")
			.addCondition("r.topic=?",topic)
			.addOrderProperty("r.postTime", true)
			.preparePageBean(replyService, pageNum, pageSize);
		return "show";
	}
	
	public String addUI(){
		Forum forum =forumService.getById(forumId);
		ActionContext.getContext().put("forum", forum);
		
		return "addUI";
	}
	
	public String add(){
		
		model.setForum(forumService.getById(forumId));
		model.setPostTime(new Date());
		model.setIpAddr(ServletActionContext.getRequest().getRemoteAddr());
		model.setAuthor(getCurrentUser());
		
		topicService.save(model);
		return "toShow";
	}
}
