package cn.itman.shop.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class MailUtils {

	//to:邮件发给谁  code：激活码
	public static void sendMail(String to, String code)
			throws AddressException, MessagingException {
		
		// 1.创建一个程序与邮件服务器会话对象 Session
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "SMTP");//发邮件的协议
		props.setProperty("mail.host", "localhost");//发送邮件的服务器地址
		props.setProperty("mail.smtp.auth", "true");// 指定验证为true

		// 创建验证器
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("service@acat.com", "111");//发邮件的账号的验证
			}
		};

		Session session = Session.getInstance(props, auth);

		// 2.创建一个Message，它相当于是邮件内容
		Message message = new MimeMessage(session);

		message.setFrom(new InternetAddress("service@acat.com")); // 设置发送者

		message.setRecipient(RecipientType.TO, new InternetAddress(to)); // 设置发送方式与接收者

		message.setSubject("阿猫商城用户激活");//邮件的主题

		message.setContent("<h1>阿猫商城用户激活点击链接进行操作</h1><h2><a href='http://192.168.43.41:8080/shop/user_active.action?code="+code+"'>http://192.168.43.41:8080/shop/user_active.action?code="+code+"</a></h2>", "text/html;charset=utf-8");

		// 3.创建 Transport用于将邮件发送
		Transport.send(message);
	}
	public static void main(String[] args) {
		try {
			sendMail("robot@acat.com","111");
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}