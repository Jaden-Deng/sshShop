package cn.itman.shop.adminuser.action;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itman.shop.adminuser.service.AdminUserService;
import cn.itman.shop.adminuser.vo.AdminUser;

/**
 * 后台用户的action
 * @author Deng
 *
 */
public class AdminUserAction extends ActionSupport implements ModelDriven<AdminUser>{
	//注入模型驱动
	private AdminUser adminUser = new AdminUser();
	//注入service
	private AdminUserService adminUserService;
	public void setAdminUserService(AdminUserService adminUserService) {
		this.adminUserService = adminUserService;
	}
	public void setAdminUser(AdminUser adminUser) {
		this.adminUser = adminUser;
	}
	public AdminUser getModel() {
		return adminUser;
	}
	
	//管理员登录的方法
	public String login(){
		 AdminUser adminExistUser = adminUserService.login(adminUser);
		 if(adminExistUser==null){//登陆失败
			this.addActionMessage("登录 失败，请检查您的用户名密码！"); 
			return "loginFail";
		 }else{//登陆成功
			 ServletActionContext.getRequest().getSession().setAttribute("adminExistUser", adminExistUser );
			 return "loginSuccess"; 
		 }
	}
	
	
}
