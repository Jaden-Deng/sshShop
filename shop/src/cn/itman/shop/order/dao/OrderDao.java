package cn.itman.shop.order.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cn.itman.shop.order.vo.Order;
import cn.itman.shop.order.vo.OrderItem;
import cn.itman.shop.product.vo.Product;
import cn.itman.shop.utils.PageHibernateCallback;

/**
 * 订单模块持久层
 * @author Deng
 *
 */
public class OrderDao extends HibernateDaoSupport{
	//保存订单信息
	public void save(Order order) {
		this.getHibernateTemplate().save(order);
	}
	//我的订单总数目
	public int findByCountUid(Integer uid) {
		String hql = "select count(*) from  Order o  where o.user.uid=?";
		List<Long>list = (List<Long>) this.getHibernateTemplate().find(hql, uid);
		if(list!=null&&list.size()>0){
			return list.get(0).intValue();
		}
		return 0;
	}
	//我的订单信息
	public List<Order> findByPageCid(Integer uid, int begin, int limit) {
		String hql = "from Order o where o.user.uid=? order by ordertime desc";
		//分页
		List<Order> list = (List<Order>) this.getHibernateTemplate().execute(new PageHibernateCallback<Order>(hql,new Object[]{uid}, begin, limit));
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}
	
	//根据oid查询订单order
	public  Order findByOid(Integer oid) {
		Order order = this.getHibernateTemplate().get(Order.class,oid);
		return order;
	}
	
	//修改订单
	public void update(Order currOrder) {
		this.getHibernateTemplate().update(currOrder);
	}
	
	//获取总条目数
	public int findByCount() {
		String hql = "select count(*) from  Order";
		List<Long>list = (List<Long>) this.getHibernateTemplate().find(hql);
		if(list!=null&&list.size()>0){
			return list.get(0).intValue();
		}
		return 0;
	}
	//获取每页的数据
	public List<Order> findByPage(int begin, int limit) {
		String hql = "from Order order by ordertime desc";
		//分页
		List<Order> list = (List<Order>) this.getHibernateTemplate().execute(new PageHibernateCallback<Order>(hql,null, begin, limit));
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}
	
	public List<OrderItem> findOrderItem(Integer oid) {
		String hql = "from OrderItem oi where oi.order.oid = ?";
		List<OrderItem>list = (List<OrderItem>) this.getHibernateTemplate().find(hql, oid);
		if(list!=null&&list.size()>0){
			System.out.println("DAOfindOrderItem 查到了内容"+list.size());
			return list;
		}
		return null;
	}

}
