package cn.itcast.oa.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.itcast.oa.domain.Department;

public class departmentUtils {

	public static List<Department> getAllDepartments(List<Department> topList) {
		
		List<Department> list=new ArrayList<Department>();
		walkGetAllDepartment("┝",topList,list);
		return list;
	}
	
	public static void walkGetAllDepartment(String prox,Collection<Department> topList,List<Department> list){
		
		for(Department top:topList){
			Department department=new Department();
			department.setId(top.getId());
			department.setName(prox+top.getName());
			list.add(department);
			walkGetAllDepartment("　　"+prox,top.getChildren(),list);
		}
	}

}
