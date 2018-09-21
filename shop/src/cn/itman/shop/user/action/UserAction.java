package cn.itman.shop.user.action;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itman.shop.user.service.UserService;
import cn.itman.shop.user.vo.User;
/**
 * 用户模块的action的类
 * @author Deng
 *
 */
public class UserAction extends ActionSupport implements ModelDriven<User>{
	//模型驱动要使用的对象
	private User user = new User();
	public User getModel(){
		return user;
	}
	//注入UserService
	private UserService userService;
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	//属性驱动，注入验证码
	private String checkcode;
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	//执行注册页面跳转的方法
	public String registPage(){
		return "registPage";
	}
	//ajax校验用户名的方法
	public String findByUsername() throws IOException {
	//调用service查询
		User existUser = userService.findByUsername(user.getUsername());
		//获得response对象
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		//判断数据库里面是否存在这个用户
		if(existUser!=null){
			//获得response对象，向页面输出查有此人
			response.getWriter().println("<font id='usernamefont' color='red'>用户名已经存在</font>");
		}else{
			response.getWriter().println("<font id='usernamefont' color='green'>用户名可以使用</font>");
		}
		return NONE;
	}
	
	//注册的方法
	public String regist(){
		//首先从session里获得验证码随机值，判断验证码
		String checkcode1 = (String) ServletActionContext.getRequest().getSession().getAttribute("checkcode");
		if(!checkcode.equalsIgnoreCase(checkcode1)){
			this.addActionError("验证码错误！");
			return "checkfail";
		}
		userService.save(user);
		this.addActionMessage("注册成功,请去您的邮箱激活账户。");
		return "msg";
	}
	//用户激活
	public String active(){
		//根据激活码查询用户
		User existUser=userService.findByCode(user.getCode());
		//判断数据库里面是否存在这个用户
		if(existUser==null){//激活码错误
			this.addActionMessage("激活码错误，激活失败！");
		}else{
			//激活成功，用户状态改成1
			existUser.setState(1);
			existUser.setCode(null);
			userService.update(existUser);
			this.addActionMessage("激活成功，请登录开启阿猫商城购物之旅！");
		}
		return "msg";
	}
	
	//跳转到用户登录页面
	public String loginPage(){
		return "loginPage";
	}
	
	//用户登录的方法
	public String login(){
		User existUser = userService.login(user);
		//首先从session里获得验证码随机值，判断验证码
		String checkcode1 = (String) ServletActionContext.getRequest().getSession().getAttribute("checkcode");
		if(!checkcode.equalsIgnoreCase(checkcode1)){
			this.addActionError("验证码错误！");
			return "logincheckfail";
		}
		if(existUser==null){//登陆失败
			this.addActionError("登录失败：用户名/密码错误或用户未激活");
			return LOGIN;
		}else{//登陆成功,将信息存入session域，并跳转页面
			ServletActionContext.getRequest().getSession().setAttribute("existUser", existUser);
			return "loginSuccess";
		}
	}
	
	//用户退出登录的方法
	public String quit(){
		//销毁session对象
		ServletActionContext.getRequest().getSession().invalidate();
		return "quit";
	}
}
