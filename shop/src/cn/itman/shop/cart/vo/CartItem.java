package cn.itman.shop.cart.vo;

import cn.itman.shop.product.vo.Product;

/**
 * 购物项对象
 * @author Deng
 *
 */
public class CartItem {
	private Product product ;//商品
	private int count;//数量
	private double subtotal;//小计
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	//自动计算小计
	public double getSubtotal() {
		return product.getShop_price()*count;
	}
}
