package cn.itcast.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.base.DaoSupportImpl;
import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.service.ForumService;

@Service
@Transactional
public class ForumServiceImpl extends DaoSupportImpl<Forum> implements ForumService{

	@Override
	public List<Forum> findAll() {
		return getSession().createQuery("from Forum f order by f.position").list();
	}
	
	
	@Override
	public void save(Forum forum) {
		super.save(forum);
		forum.setPosition(forum.getId().intValue());
	}


	@Override
	public void update(Forum forum) {
		super.update(forum);
		forum.setPosition(forum.getId().intValue());
	}


	public void moveUp(Long id){
		Forum forum=getById(id);
		Forum other=(Forum) getSession().createQuery("from Forum f Where f.position<? order by f.position DESC")
				.setParameter(0, forum.getPosition())
				.setFirstResult(0)
				.setMaxResults(1)
				.uniqueResult();
		
		if(other==null){
			return;
		}
		
		int temp=forum.getPosition();
		forum.setPosition(other.getPosition());
		other.setPosition(temp);
		
		getSession().update(forum);
		getSession().update(other);
	}


	public void moveDown(Long id) {
		Forum forum=getById(id);
		Forum other=(Forum) getSession().createQuery("from Forum f Where f.position>? order by f.position")
				.setParameter(0, forum.getPosition())
				.setFirstResult(0)
				.setMaxResults(1)
				.uniqueResult();
		
		if(other==null){
			return;
		}
		
		int temp=forum.getPosition();
		forum.setPosition(other.getPosition());
		other.setPosition(temp);
		
		getSession().update(forum);
		getSession().update(other);
	}
}
