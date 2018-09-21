package cn.itman.shop.cart.action;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.itman.shop.cart.vo.Cart;
import cn.itman.shop.cart.vo.CartItem;
import cn.itman.shop.product.service.ProductService;
import cn.itman.shop.product.vo.Product;

/**
 * 购物车的action
 * @author Deng
 *
 */
public class CartAction extends ActionSupport{
	//接收pid
	private Integer pid;
	//接收数量count
	private Integer count;
	//注入商品service
	private ProductService productService;
	
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	//将购物项添加到购物车
	public String addCart(){
		//封装一个CartItem对象
		CartItem cartItem = new CartItem();
		//设置数量
		cartItem.setCount(count);
		//根据商品pid查询商品
		Product product = productService.findByPid(pid);
		//设置商品信息
		cartItem.setProduct(product);
		//将购物项添加到购物车
		//购物车存在session中，所以从session中获得购物车
		Cart cart = getCart();
		cart.addCart(cartItem);
		
		return "addCart";
	}
	
	//从session获得购物车的方法
	private Cart getCart() {
		Cart cart = (Cart) ServletActionContext.getRequest().getSession().getAttribute("cart");
		if(cart==null){
			 cart = new Cart();
			 ServletActionContext.getRequest().getSession().setAttribute("cart", cart);
		}
		
		return cart;
	}
	
	//清空购物车
	public String clearCart(){
		//获得购物车对象
		Cart cart = getCart();
		//调用购物车清空的方法
		cart.clearCart();
		
		return "clearCart";
	}
	
	//删除购物项
	public String removeCart(){
		//获得购物车对象
		Cart cart = getCart();		
		//调用购物车移除的方法
		cart.removeCart(pid);
		
		return "removeCart";
	}
	
	//我的购物车
	public String myCart(){
		
		return "myCart";
	}
	
}
