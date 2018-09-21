package cn.itman.shop.user.dao;

import java.util.List;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cn.itman.shop.user.vo.User;

public class UserDao extends HibernateDaoSupport{
	//按用户名查询用户的存在
	public User findByUsername(String username){
		String hql="from User where username=?";
		List<User> list=(List<User>) this.getHibernateTemplate().find(hql, username);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	//注册用户
	public void save(User user) {
		this.getHibernateTemplate().save(user);
	}
	//根据激活码查询用户
	public User findByCode(String code) {
		String hql="from User where code=?";
		List<User> list=(List<User>) this.getHibernateTemplate().find(hql, code);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	//修改用户状态
	public void update(User existUser) {
		this.getHibernateTemplate().update(existUser);
	}
	//根据用户名密码查找用户
	public User login(User user) {
		String hql = "from User where username=? and password=? and state=?";
		List<User> list=(List<User>) this.getHibernateTemplate().find(hql, user.getUsername(),user.getPassword(),1);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
}
