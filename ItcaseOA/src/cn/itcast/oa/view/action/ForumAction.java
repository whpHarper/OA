package cn.itcast.oa.view.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.domain.Topic;
import cn.itcast.oa.util.QueryHelper;

@Controller
@Scope("prototype")
public class ForumAction extends BaseAction<Forum> {
	
	/**
	 * 0表示查看全部主题<br>
	 * 1表示只看精华帖
	 */
	private int viewtype=0;
	
	/**
	 * 0表示默认排序（所有置顶帖在前面，并按最后更新时间降序排列）
	 * 1表示只按照最后更新时间排序
	 * 2表示只按照主题发表时间排序
	 * 3表示只按照回复数量排序
	 */
	private int orderBy=0;
	
	public int getViewtype() {
		return viewtype;
	}

	public void setViewtype(int viewtype) {
		this.viewtype = viewtype;
	}

	public int getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}

	public boolean isAsc() {
		return asc;
	}

	public void setAsc(boolean asc) {
		this.asc = asc;
	}

	/**
	 * true表示升序
	 * false表示降序
	 * 
	 */
	private  boolean asc=false;
	
	public String list(){
		List<Forum> forumList=forumService.findAll();
		ActionContext.getContext().put("forumList", forumList);
		return "list";
	}
	
	public String show(){
		//准备数据：forum
		Forum forum =forumService.getById(model.getId());
		ActionContext.getContext().put("forum", forum);
		
		//准备数据：topicList
		/*List<Topic> topicList=topicService.findByForum(forum);
		ActionContext.getContext().put("topicList", topicList);*/
		
		//准备分页信息：pageBean
		/*PageBean pageBean=topicService.getPageBean(pageNum,pageSize,forum);
		ActionContext.getContext().getValueStack().push(pageBean);*/
		
		/*String hql="From Topic t where t.forum=? order by (CASE t.type WHEN 2 THEN 2 ELSE 0 END) DESC, t.lastUpdateTime DESC";
		List parameters=new ArrayList();
		parameters.add(forum);
		
		PageBean pageBean=topicService.getPageBean(pageNum, pageSize, hql, parameters);
		ActionContext.getContext().getValueStack().push(pageBean);*/
		new QueryHelper(Topic.class,"t")
			//过滤条件
			.addCondition("t.forum=?", forum)
			.addCondition((viewtype==1),"t.type=?", Topic.TYPE_BEST)
			//排序条件
			.addOrderProperty((orderBy==1), "t.lastUpdateTime",asc)
			.addOrderProperty((orderBy==2), "t.postTime", asc)
			.addOrderProperty((orderBy==3),"t.replyCount" ,asc)
			.addOrderProperty((orderBy==0),"(CASE t.type WHEN 2 THEN 2 ELSE 0 END)", false)
			.addOrderProperty((orderBy==0),"t.lastUpdateTime",false)
			.preparePageBean(topicService, pageNum, pageSize);
		return "show";
	}
}
