package cn.itcast.oa.base;

import java.util.List;

public interface BaseDao<T> {
	
	/**
	 * 保存实体
	 * @param entity
	 */
	void save(T entity);
	
	/**
	 * 删除实体
	 * @param id
	 */
	void delete(Long id);
	
	/**
	 * 更新方法
	 * @param entity
	 */
	void update(T entity);
	
	/**
	 * 根据id查询单个实体
	 * @param id
	 * @return
	 */
	T getById(Long id);
	
	/**
	 * 根据id数组查询所有实体集合
	 * @param ids
	 * @return
	 */
	List<T> getByIds(Long[] ids);
	
	/**
	 * 无条件查询所有实体
	 * @return
	 */
	List<T> findAll();
}
