package cn.itman.shop.order.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.itman.shop.order.dao.OrderDao;
import cn.itman.shop.order.vo.Order;
import cn.itman.shop.order.vo.OrderItem;
import cn.itman.shop.product.vo.Product;
import cn.itman.shop.utils.PageBean;

/**
 * 订单模块的业务层
 * @author Deng
 *
 */
@Transactional
public class OrderService {
	private OrderDao orderDao;

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	//保存订单的方法
	public void save(Order order) {
		orderDao.save(order);
	}
	
	//查询我的订单
	public PageBean<Order> findByPageUid(Integer uid, Integer page) {
		PageBean<Order> pageBean = new PageBean<Order>();
		//设置当前页数
		pageBean.setPage(page);
		//设置每页显示条数
		int limit = 5;
		pageBean.setLimit(limit);
		//设置总记录数(要查询数据库)
		int totalCount = 0;
		totalCount = orderDao.findByCountUid(uid);
		pageBean.setTotalCount(totalCount);
		//设置总页数
		int totalPage = 0;
		totalPage=(totalCount+limit-1)/limit;
		pageBean.setTotalPage(totalPage); 
		//每页显示的数据集合
		//开始的页数
		int begin=(page-1)*limit;
		List<Order>list = orderDao.findByPageCid(uid,begin,limit);
		pageBean.setList(list);
		
		return pageBean;

	}

	//根据订单oid查询订单
	public Order findByOid(Integer oid) {

		return orderDao.findByOid(oid);
	}
	
	//修改订单信息
	public void update(Order currOrder) {
		orderDao.update(currOrder);
	}

	//分页查询订单
	public PageBean<Order> findByPage(int page) {
		PageBean<Order> pageBean = new PageBean<Order>();
		//设置当前页数
		pageBean.setPage(page);
		//设置每页显示条数
		int limit = 5;
		pageBean.setLimit(limit);
		//设置总记录数(要查询数据库)
		int totalCount = 0;
		totalCount = orderDao.findByCount();
		pageBean.setTotalCount(totalCount);
		//设置总页数
		int totalPage = 0;
		totalPage=(totalCount+limit-1)/limit;
		pageBean.setTotalPage(totalPage); 
		//每页显示的数据集合
		//开始的页数
		int begin=(page-1)*limit;
		List<Order>list = orderDao.findByPage(begin,limit);
		pageBean.setList(list);
		
		return pageBean;
	}

	public List<OrderItem> findOrderItem(Integer oid) {
		System.out.println("現在在service  findOrderItem");
		return orderDao.findOrderItem(oid);
	}
}
