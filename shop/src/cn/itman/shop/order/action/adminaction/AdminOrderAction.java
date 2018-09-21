package cn.itman.shop.order.action.adminaction;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itman.shop.order.service.OrderService;
import cn.itman.shop.order.vo.Order;
import cn.itman.shop.order.vo.OrderItem;
import cn.itman.shop.utils.PageBean;

/**
 * 后台订单管理的action
 * @author Deng
 *
 */
public class AdminOrderAction extends ActionSupport implements ModelDriven<Order>{

	private Order order = new Order();
	private OrderService orderService;
	private int page;
	public void setPage(int page) {
		this.page = page;
	}
	public Order getModel() {
		// TODO Auto-generated method stub
		return order;
	}
	
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	//带分页查询的方法
	public String findAll(){
		PageBean<Order> pageBean = orderService.findByPage(page);
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findAll";
	}
	
	//根据oid查询订单项
	public String findOrderItem(){
		List<OrderItem>list = orderService.findOrderItem(order.getOid());
		ActionContext.getContext().getValueStack().set("list", list);
		return "findOrderItem";
	}
	
	//后台修改订单状态的方法
	public String updateState(){
		Order currOrder = orderService.findByOid(order.getOid());
		currOrder.setState(3);
		orderService.update(currOrder);
		
		return "updateState";
	}

}
