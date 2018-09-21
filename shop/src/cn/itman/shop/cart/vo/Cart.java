package cn.itman.shop.cart.vo;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 购物车对象
 * @author Deng
 *
 */
public class Cart implements Serializable{
	//购物项的集合,map的key就是商品的pid,value就是购物项
	private Map<Integer,CartItem> map = new LinkedHashMap<Integer,CartItem>();
	//购物车总价格
	private double total;
	public Collection<CartItem> getCartItems() {
		return map.values();
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
	//购物车功能
	//1.添加商品到购物车
	public void addCart(CartItem cartItem){
		//判断购物车是否存在该购物项，若存在则数量加1，总计=总计+购物项小计
		//若不存在该购物项，项map添加购物项，总计=总计+购物项小计
		
		//首先获得商品的id
		Integer pid = cartItem.getProduct().getPid();
		//判断购物车中是否存在该商品
		if(map.containsKey(pid)){
			//存在
			CartItem ocartItem =map.get(pid);//获得购物车中原来的购物项
			ocartItem.setCount(ocartItem.getCount()+cartItem.getCount());//将原来的数量加上目前添加的数量
		}else{
			//不存在
			map.put(pid, cartItem);
		}
		//设置总计的值
		total += cartItem.getSubtotal();
		
	}
	
	//2.移除购物项
	public void removeCart(Integer pid){
		//将某项移除
		CartItem cartItem=map.remove(pid);
		//总计-移除的购物项小计
		total-=cartItem.getSubtotal();
	}
	
	//3.清空购物车
	public void clearCart(){
		//将所有购物项移除
		map.clear();
		//总计设置为0
		total = 0;
	}
	
	
}
