package cn.itman.shop.categorysecond.vo;

import java.util.HashSet;
import java.util.Set;

import cn.itman.shop.category.vo.Category;
import cn.itman.shop.product.vo.Product;

/**
 * 二级分类实体
 * @author Deng
 *
 */
public class CategorySecond {
	private Integer csid;
	private String csname;
	//外键cid是一级分类的主键，所以存的是一级分类的对象
	private Category category;
	//配置商品集合
	private Set<Product> products = new HashSet<Product>();
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Set<Product> getProducts() {
		return products;
	}
	public void setProducts(Set<Product> products) {
		this.products = products;
	}
	public Integer getCsid() {
		return csid;
	}
	public void setCsid(Integer csid) {
		this.csid = csid;
	}
	public String getCsname() {
		return csname;
	}
	public void setCsname(String csname) {
		this.csname = csname;
	}

}
