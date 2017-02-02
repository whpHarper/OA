package cn.itcast.oa.service;

import java.util.List;


import cn.itcast.oa.domain.Role;

public interface RoleService {

	List<Role> queryAll();

	void deleteById(long id);

	void add(Role role);

	Role getById(long id);

	void edit(Role role);

}
