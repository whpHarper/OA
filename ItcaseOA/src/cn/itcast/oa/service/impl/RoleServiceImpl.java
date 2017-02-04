package cn.itcast.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.base.DaoSupportImpl;
import cn.itcast.oa.dao.RoleDao;
import cn.itcast.oa.domain.Role;
import cn.itcast.oa.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl extends DaoSupportImpl<Role> implements RoleService{

/*	@Resource
	private RoleDao roleDao;
	
	public List<Role> queryAll() {
		List<Role> roleList= roleDao.findAll();
		return roleList;
	}

	public void deleteById(long id) {
		roleDao.delete(id);
		
	}

	public void add(Role role) {
		roleDao.save(role);
	}

	public Role getById(long id) {
		Role role=roleDao.getById(id);
		return role;
	}

	public void edit(Role role) {
		roleDao.update(role);
	}*/

}
