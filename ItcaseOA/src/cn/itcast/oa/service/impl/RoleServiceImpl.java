package cn.itcast.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.dao.RoleDao;
import cn.itcast.oa.domain.Role;
import cn.itcast.oa.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{

	@Resource
	private RoleDao roleDao;
	
	public List<Role> queryAll() {
		List<Role> roleList= roleDao.findAll();
		return roleList;
	}

	public void deleteById(long id) {
		roleDao.delete(id);
		
	}

}
