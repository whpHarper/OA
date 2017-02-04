package cn.itcast.oa.service;

import java.util.List;

import cn.itcast.oa.base.DaoSupport;
import cn.itcast.oa.domain.Department;

public interface DepartmentService extends DaoSupport<Department> {

	/*List<Department> findAll();
	
	void delete(long id);

	void save(Department model);

	Department findById(long id);

	void update(Department department);
	*/

	List<Department> findTopList();

	List<Department> finChildrenList(Long parentId);


}
