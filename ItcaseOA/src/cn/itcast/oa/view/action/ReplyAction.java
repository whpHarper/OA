package cn.itcast.oa.view.action;

import java.util.Date;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.Reply;
import cn.itcast.oa.domain.Topic;

@SuppressWarnings("serial")
@Controller
@Scope("prototype")
public class ReplyAction extends BaseAction<Reply> {

	private Long topicId;
	
	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	public String addUI(){
		//准备数据
		Topic topic =topicService.getById(topicId);
		ActionContext.getContext().put("topic", topic);
		return "addUI";
	}
	
	public String add(){
		
		model.setTopic(topicService.getById(topicId));
		model.setAuthor(getCurrentUser());
		model.setIpAddr(ServletActionContext.getRequest().getRemoteAddr());
		model.setPostTime(new Date());
		
		replyService.save(model);
		
		return "toTopicShow";
	}
}
