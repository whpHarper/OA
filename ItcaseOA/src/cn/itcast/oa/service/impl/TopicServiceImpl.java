package cn.itcast.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.base.DaoSupportImpl;
import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.domain.Topic;
import cn.itcast.oa.service.TopicService;

@Service
@Transactional
public class TopicServiceImpl extends DaoSupportImpl<Topic> implements TopicService{

	@Override
	public void save(Topic topic) {
		topic.setType(topic.TYPE_NORMAL);
		topic.setReplyCount(0);
		topic.setLastReply(null);
		topic.setLastUpdateTime(topic.getPostTime());
		
		getSession().save(topic);
		
		//维护相关特殊属性
		Forum forum=topic.getForum();
		forum.setTopicCount(forum.getTopicCount()+1);
		forum.setArticleCount(forum.getArticleCount()+1);
		forum.setLastTopic(topic);
		getSession().update(forum);
		
	}

	public List<Topic> findByForum(Forum forum) {
		
		return getSession().createQuery(
				"From Topic t where t.forum=? order by (CASE t.type WHEN 2 THEN 2 ELSE 0 END) DESC, t.lastUpdateTime DESC")
				.setParameter(0, forum)
				.list();
	}

	public PageBean getPageBean(int pageNum, int pageSize, Forum forum) {
		List list=getSession().createQuery(
				"From Topic t where t.forum=? order by (CASE t.type WHEN 2 THEN 2 ELSE 0 END) DESC, t.lastUpdateTime DESC")
				.setParameter(0, forum)
				.setFirstResult((pageNum-1)*pageSize)
				.setMaxResults(pageSize)
				.list();
		Long count=(Long) getSession().createQuery(
				"select count(*) From Topic t where t.forum=?")
				.setParameter(0, forum).uniqueResult();
		return new PageBean(pageNum,pageSize,count.intValue(),list);
	}

	
}
