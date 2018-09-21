package cn.itman.shop.user.service;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.transaction.annotation.Transactional;

import cn.itman.shop.user.dao.UserDao;
import cn.itman.shop.user.vo.User;
import cn.itman.shop.utils.MailUtils;
import cn.itman.shop.utils.UUIDUtils;
@Transactional
public class UserService {
	//注入UserDao
	private UserDao userDao;
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	//按用户名查询用户的方法
	public User findByUsername(String username){
		
		return userDao.findByUsername(username);
	}

	//用户注册的方法
	public void save(User user) {
		user.setState(0);//状态0，代表用户未激活，1代表激活
		String code = UUIDUtils.getUUID()+UUIDUtils.getUUID();
		user.setCode(code);
		userDao.save(user);
		//发送激活邮件
		try {
			MailUtils.sendMail(user.getEmail(), code);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	//根据激活码查询用户
	public User findByCode(String code) {
		return userDao.findByCode(code);
	}
	
	//激活用户，修改用户状态
	public void update(User existUser) {
		userDao.update(existUser);
	}

	//用户登录的方法
	public User login(User user) {
		return userDao.login(user);
	}


}
