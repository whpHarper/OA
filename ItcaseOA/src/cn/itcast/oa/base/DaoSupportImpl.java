package cn.itcast.oa.base;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.util.QueryHelper;

@SuppressWarnings("unchecked")
@Transactional
public abstract class DaoSupportImpl<T> implements DaoSupport<T>{

	@Resource
	private SessionFactory sessionFactory;
	
	private Class<T> clazz=null;  //TODO:无法获得T的class类型
	
	public DaoSupportImpl(){
		//使用反射技术获得泛型T的类型
		ParameterizedType pt=(ParameterizedType) this.getClass().getGenericSuperclass();  //获取属性类型
		this.clazz=(Class<T>)pt.getActualTypeArguments()[0];
		System.out.println("clazz---->>"+clazz);
	}
	
	/**
	 * 获取当前的session
	 * 
	 * @return
	 */
	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public void save(T entity) {
		getSession().save(entity);
		
	}

	public void delete(Long id) {
		Object obj=getById(id);
		if(obj!=null){
			getSession().delete(obj);
		}
	}

	public void update(T entity) {
		getSession().update(entity);
		
	}


	public T getById(Long id) {
		if(id==null){
			return null;
		}else{
			return (T)getSession().get(clazz, id);
		}
	}

	public List<T> getByIds(Long[] ids) {
		if(ids!=null){
			return getSession().createQuery(
					"From "+clazz.getSimpleName()+" where id in(:ids)")
					.setParameterList("ids", ids)
					.list();
		}
		else{
			return Collections.EMPTY_LIST;
		}
	}

	public List<T> findAll() {
		return getSession().createQuery(
				"from "+clazz.getSimpleName())
				.list();
	}
	
	@Deprecated
	public PageBean getPageBean(int pageNum,int pageSize,String hql,List<Object> parameters){
		//使用query对象查询数据
		// 查询本页的数据列表
		Query listQuery = getSession().createQuery(hql); // 创建查询对象
		if (parameters != null) { // 设置参数
			for (int i = 0; i < parameters.size(); i++) {
				listQuery.setParameter(i, parameters.get(i));
			}
		}
		listQuery.setFirstResult((pageNum - 1) * pageSize);
		listQuery.setMaxResults(pageSize);
		List list = listQuery.list(); // 执行查询

		// 查询总记录数量
		Query countQuery = getSession().createQuery("SELECT COUNT(*) " + hql);
		if (parameters != null) { // 设置参数
			for (int i = 0; i < parameters.size(); i++) {
				countQuery.setParameter(i, parameters.get(i));
			}
		}
		Long count = (Long) countQuery.uniqueResult(); // 执行查询

		return new PageBean(pageNum, pageSize, count.intValue(), list);
	
	}
	
	/**
	 * 通过queryHelper创建查询对象
	 */
	public PageBean getPageBean(int pageNum,int pageSize,QueryHelper queryHelper){

		List<Object>parameters=queryHelper.getParameters();
		Query listQuery = getSession().createQuery(queryHelper.getListQueryHql()); // 创建查询对象
		if (parameters != null) { // 设置参数
			for (int i = 0; i < parameters.size(); i++) {
				listQuery.setParameter(i, parameters.get(i));
			}
		}
		listQuery.setFirstResult((pageNum - 1) * pageSize);
		listQuery.setMaxResults(pageSize);
		List list = listQuery.list(); // 执行查询

		// 查询总记录数量
		Query countQuery = getSession().createQuery(queryHelper.getCountQueryHql());
		if (parameters != null) { // 设置参数
			for (int i = 0; i < parameters.size(); i++) {
				countQuery.setParameter(i, parameters.get(i));
			}
		}
		Long count = (Long) countQuery.uniqueResult(); // 执行查询

		return new PageBean(pageNum, pageSize, count.intValue(), list);
	
	}


}
