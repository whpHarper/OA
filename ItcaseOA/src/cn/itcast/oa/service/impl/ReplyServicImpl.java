package cn.itcast.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.base.DaoSupportImpl;
import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.domain.Reply;
import cn.itcast.oa.domain.Topic;
import cn.itcast.oa.service.ReplyService;

@Service
@Transactional
public class ReplyServicImpl extends DaoSupportImpl<Reply> implements ReplyService{

	public List<Reply> findByTopic(Topic topic) {
		return getSession().createQuery(
				"From Reply r where r.topic=? order by r.postTime ASC")
				.setParameter(0, topic).list();
	}
	
	@Override
	public void save(Reply reply){
		getSession().save(reply);
		
		Topic topic =reply.getTopic();
		Forum forum=topic.getForum();
		
		forum.setArticleCount(forum.getArticleCount()+1);
		topic.setReplyCount(topic.getReplyCount()+1);
		topic.setLastReply(reply);
		topic.setLastUpdateTime(reply.getPostTime());
		
		getSession().update(topic);
		getSession().update(forum);
	}

	public PageBean getPageBean(int pageNum, int pageSize, Topic topic) {
		List list=getSession().createQuery(
				"From Reply r where r.topic=? order by r.postTime ASC")
				.setParameter(0, topic)
				.setFirstResult((pageNum-1)*pageSize)
				.setMaxResults(pageSize)
				.list();
		Long count=(Long) getSession().createQuery(
				"select count(*) From Reply r where r.topic=?")
				.setParameter(0, topic)
				.uniqueResult();
				
		return new PageBean(pageNum,pageSize,count.intValue(),list);
	}


	
}
