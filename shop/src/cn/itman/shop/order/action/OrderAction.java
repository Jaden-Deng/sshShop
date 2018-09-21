package cn.itman.shop.order.action;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itman.shop.cart.vo.Cart;
import cn.itman.shop.cart.vo.CartItem;
import cn.itman.shop.order.service.OrderService;
import cn.itman.shop.order.vo.Order;
import cn.itman.shop.order.vo.OrderItem;
import cn.itman.shop.user.vo.User;
import cn.itman.shop.utils.PageBean;
import cn.itman.shop.utils.PaymentUtil;

public class OrderAction extends ActionSupport implements ModelDriven<Order>{
	//模型驱动需要使用的对象
	private Order order = new Order();
	//注入orderService
	private OrderService orderService;
	//注入page属性
	private Integer page;
	//接收支付通道的编码
	private String pd_FrpId;
	//接收付款成功后的响应数据
	private String  r6_Order;  //商户订单号 
	private String  r3_Amt;//  支付金额 
	public void setR6_Order(String r6_Order) {
		this.r6_Order = r6_Order;
	}
	public void setR3_Amt(String r3_Amt) {
		this.r3_Amt = r3_Amt;
	}
	public void setPd_FrpId(String pd_FrpId) {
		this.pd_FrpId = pd_FrpId;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	public Order getModel() {
		// TODO Auto-generated method stub
		return order;
	}
	
	//生成订单的方法
	public String save(){
		//保存订单数据到数据库
		order.setOrdertime(new Date());
		order.setState(1);//订单状态：1未付款 2付款未发货 3已发货但未确认收货 4交易完成
		//总计金额的数据从购物车得来
		Cart cart = (Cart) ServletActionContext.getRequest().getSession().getAttribute("cart");
		if(cart==null){//购物车没内容
			this.addActionError("您的购物车没有商品哦，请在商城内精挑细选~");
			return "msg";
		}
		order.setTotal(cart.getTotal());
		//设置订单中购物项的内容
		for (CartItem cartItem : cart.getCartItems()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setCount(cartItem.getCount());
			orderItem.setSubtotal(cartItem.getSubtotal());
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setOrder(order);
			order.getOrderItems().add(orderItem);
		}
		//设置订单所属的用户
		User existUser = (User) ServletActionContext.getRequest().getSession().getAttribute("existUser");
		if(existUser==null){
			this.addActionError("您的没有登录哦，请登录开启购物之旅~");
			return "login";
		}
		order.setUser(existUser);
		orderService.save(order);
		//将订单的对象存入值栈，显示到页面
		//清空购物车
		cart.clearCart();
		return "saveSuccess";
	}
	
		//我的订单（根据用户uid查询订单）
	public String findByUid(){
		//根据用户id查询
		User existUser = (User) ServletActionContext.getRequest().getSession().getAttribute("existUser");
		//调用service查询
		PageBean<Order>pageBean = orderService.findByPageUid(existUser.getUid(),page);
		//通过值栈将数据显示到页面上
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		
		return "findByUidSuccess";
	}
	
	//根据订单oid查询订单
	public String findByOid(){
		order = orderService.findByOid(order.getOid());
		return "findByOidSuccess";
	}
	
	//付款的方法
	public String payOrder() throws IOException{
		//修改订单
		System.out.println(order.getOid());
		Order currOrder = orderService.findByOid(order.getOid());
		currOrder.setAddr(order.getAddr());
		currOrder.setName(order.getName());
		currOrder.setPhone(order.getPhone());
		orderService.update(currOrder);
		//为订单付款
		String p0_Cmd = "Buy"; // 业务类型:
		String p1_MerId = "10001126856";// 商户编号:
		String p2_Order = order.getOid().toString();// 订单编号:
		String p3_Amt = "0.01"; // 付款金额:
		String p4_Cur = "CNY"; // 交易币种:
		String p5_Pid = ""; // 商品名称:
		String p6_Pcat = ""; // 商品种类:
		String p7_Pdesc = ""; // 商品描述:
		String p8_Url = "http://192.168.43.41:8080/shop/order_callBack.action"; // 商户接收支付成功数据的地址:
		String p9_SAF = ""; // 送货地址:
		String pa_MP = ""; // 商户扩展信息:
		String pd_FrpId = this.pd_FrpId;// 支付通道编码:
		String pr_NeedResponse = "1"; // 应答机制:
		String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl"; // 秘钥
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
				p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
				pd_FrpId, pr_NeedResponse, keyValue); // hmac
		// 向易宝发送请求:
		StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
		sb.append("p0_Cmd=").append(p0_Cmd).append("&");
		sb.append("p1_MerId=").append(p1_MerId).append("&");
		sb.append("p2_Order=").append(p2_Order).append("&");
		sb.append("p3_Amt=").append(p3_Amt).append("&");
		sb.append("p4_Cur=").append(p4_Cur).append("&");
		sb.append("p5_Pid=").append(p5_Pid).append("&");
		sb.append("p6_Pcat=").append(p6_Pcat).append("&");
		sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
		sb.append("p8_Url=").append(p8_Url).append("&");
		sb.append("p9_SAF=").append(p9_SAF).append("&");
		sb.append("pa_MP=").append(pa_MP).append("&");
		sb.append("pd_FrpId=").append(pd_FrpId).append("&");
		sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
		sb.append("hmac=").append(hmac);
		
		// 重定向:向易宝出发:
		ServletActionContext.getResponse().sendRedirect(sb.toString());
		
		return NONE;
	}
	
	//付款成功后回到本地
	public String callBack(){
		//修改订单状态为已付款
		Order currOrder = orderService.findByOid(Integer.parseInt(r6_Order));
		currOrder.setState(2);
		orderService.update(currOrder);
		//在页面显示付款成功的信息
		this.addActionMessage("付款成功！ 订单编号："+r6_Order+" 金额：￥"+r3_Amt);
		return "msg";
	}
	
	//更新订单的状态
	public String updateState(){
		Order currOrder = orderService.findByOid(order.getOid());
		currOrder.setState(4);
		orderService.update(currOrder);
		
		return "updateState";
	}	
		
}
