package cn.itman.shop.interceptor;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

import cn.itman.shop.adminuser.vo.AdminUser;

/**
 * 后台管理权限校验 拦截器，阻止未登录用户进行访问	
 * @author Deng
 *
 */
public class PrivilegeInterceptor extends MethodFilterInterceptor{

	@Override
	//执行拦截的方法
	protected String doIntercept(ActionInvocation actionInvocation) throws Exception {
		//判断session中是否保存了后台信息
		AdminUser adminExistUser = (AdminUser) ServletActionContext.getRequest().getSession().getAttribute("adminExistUser");
		
		if(adminExistUser == null){//未登录
			ActionSupport actionSupport = (ActionSupport) actionInvocation.getAction();
			actionSupport.addActionMessage("没有访问权限，请前往登录···");
			
			return "loginFail";
		}else{//已经登录
			return actionInvocation.invoke();
		}

	}

}
