package cn.itcast.oa.util;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.oa.base.DaoSupport;
import cn.itcast.oa.domain.PageBean;

public class QueryHelper {
	
	private String fromClause; //From子句
	private String whereClause=""; //where子句
	private String orderByClause=""; //orederby子句
	
	private List<Object> parameters=new ArrayList<Object>();

	/**
	 * 生成from子句
	 * 
	 * @param clazz
	 * @param alias
	 */
	public QueryHelper(Class clazz,String alias){
		fromClause="From "+clazz.getSimpleName()+" " +alias;
	}
	
	/**
	 * 添加where子句
	 * 
	 * @param condition
	 * @param params
	 * @return
	 */
	public QueryHelper addCondition(String condition,Object... params){
		
		if(whereClause.length()==0){
			whereClause=" where "+condition;
		}else{
			whereClause+=" and "+condition;
		}
		
		if(params!=null){
			for(Object p:params){
				parameters.add(p);
			}
		}
		return this;
	}
	
	/**
	 * 如果第一个参数为true，则拼接where子句
	 * 
	 * @param append
	 * @param condition
	 * @param params
	 * @return
	 */
	public QueryHelper addCondition(boolean append,String condition,Object... params){
		
		if(append){
			addCondition(condition,params);
		}
		return this;
	}
	
	/**
	 * 添加orderBy属性
	 * 
	 * @param propertyName
	 * @param asc
	 * @return
	 */
	public QueryHelper addOrderProperty(String propertyName,boolean asc){
		if(orderByClause.length()==0){
			orderByClause=" order by "+propertyName+" "+(asc ?"ASC":"DESC");
		}else{
			orderByClause+=","+propertyName+" "+(asc? "ASC":"DESC");
		}
		return this;
	}
	
	/**
	 * 判断是否要添加orderby属性
	 * 
	 * @param append
	 * @param propertyName
	 * @param asc
	 * @return
	 */
	public QueryHelper addOrderProperty(boolean append,String propertyName,boolean asc){
		if(append){
			addOrderProperty(propertyName,asc);
		}
		return this;
	}
	
	/**
	 * 获取用于生成查询数据列表的HQL语句
	 * @return
	 */
	public String getListQueryHql(){
		return fromClause+whereClause+orderByClause;
	}
	
	/**
	 * 获取用于生成查询总记录数的HQL语句
	 * @return
	 */
	public String getCountQueryHql(){
		return "select count(*) "+fromClause+whereClause;
	}
	
	
	/**
	 * 获取参数列表
	 * 
	 * @return
	 */
	public List<Object> getParameters(){
		return parameters;
	}
	
	/**
	 * 通过prearePageBean将数据放入值栈中
	 * @param service
	 * @param pageNum
	 * @param pageSize
	 */
	public void preparePageBean(DaoSupport<?> service,int pageNum,int pageSize){
		PageBean pageBean =service.getPageBean(pageNum, pageSize, this);
		ActionContext.getContext().getValueStack().push(pageBean);
	}
}
