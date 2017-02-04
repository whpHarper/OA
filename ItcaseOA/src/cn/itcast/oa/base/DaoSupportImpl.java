package cn.itcast.oa.base;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

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

}
