package cn.itman.shop.adminuser.service;

import org.springframework.transaction.annotation.Transactional;

import cn.itman.shop.adminuser.dao.AdminUserDao;
import cn.itman.shop.adminuser.vo.AdminUser;

/**
 * 后台用户业务层的类
 * @author Deng
 *
 */
@Transactional
public class AdminUserService {
	//注入dao
	private AdminUserDao adminUserDao;
	public void setAdminUserDao(AdminUserDao adminUserDao) {
		this.adminUserDao = adminUserDao;
	}
	
	//业务层管理员登录的方法
	public AdminUser login(AdminUser adminUser) {
		return adminUserDao.login(adminUser);
	}
	
}
