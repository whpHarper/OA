package cn.itcast.oa.util;

import cn.itcast.oa.domain.User;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class CheckPrivilegeInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		//准备user数据
		User user=(User) ActionContext.getContext().getSession().get("user");
		
		//准备要拦截的url地址
		String namespace=invocation.getProxy().getNamespace();
		String actionName=invocation.getProxy().getActionName();
		String privUrl=namespace+actionName;
		
		//判断是否已经登录，判断是否是去登录，是则放行，否则返回登录
		if(user==null){
			if(privUrl.startsWith("/user_login")){
				return invocation.invoke();
			}else{
				return "loginUI";
			}
		}else{
			//如果登录，则判断权限
			if(user.hasPrivilegeByUrl(privUrl)){
				return invocation.invoke();
			}else{
				return "noPrivilegeError";
			}
		}

	}

}
