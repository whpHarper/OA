package cn.itcast.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.dao.DepartmentDao;
import cn.itcast.oa.domain.Department;
import cn.itcast.oa.service.DepartmentService;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class DepartmentServiceImpl implements DepartmentService {

	@Resource
	private DepartmentDao departmentDao;
	
	@Resource
	private SessionFactory sessionFactory;
	
	public List<Department> findAll() {
		return departmentDao.findAll();
	}

	public void delete(long id) {
		departmentDao.delete(id);
	}

	public void save(Department model) {
		departmentDao.save(model);
	}

	public Department findById(long id) {
		return departmentDao.getById(id);
	}

	public void update(Department department) {
		departmentDao.update(department);
	}

	
	public List<Department> findTopList() {
		
		return sessionFactory.getCurrentSession().createQuery("From Department d where d.parent IS null").list();
	}


	public List<Department> finChildrenList(Long parentId) {
		return sessionFactory.getCurrentSession().createQuery("from Department d where d.parent.id=?").setParameter(0,parentId).list();

	}

}
