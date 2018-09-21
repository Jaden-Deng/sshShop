package cn.itman.shop.adminuser.dao;

import java.util.List;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cn.itman.shop.adminuser.vo.AdminUser;

public class AdminUserDao extends HibernateDaoSupport{

	//管理员登录的方法
	public AdminUser login(AdminUser adminUser) {
		String hql = "from AdminUser where username=? and password=?";
		List<AdminUser>list = (List<AdminUser>) this.getHibernateTemplate().find(hql, adminUser.getUsername(),adminUser.getPassword());
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		
		return null;
	}

}
