package cn.itman.shop.product.action;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itman.shop.category.service.CategoryService;
import cn.itman.shop.category.vo.Category;
import cn.itman.shop.product.service.ProductService;
import cn.itman.shop.product.vo.Product;
import cn.itman.shop.utils.PageBean;

public class ProductAction extends ActionSupport implements ModelDriven<Product>{
	//用于接收数据的模型驱动
	private Product product = new Product();
	//注入商品的service
	private ProductService productService;
	//注入分类的cid，为了接收页面传来的cid 
	private Integer cid;
	//注入二级分类的csid,为了接收csid
	private Integer csid;
	public Integer getCsid() {
		return csid;
	}
	public void setCsid(Integer csid) {
		this.csid = csid;
	}
	public Integer getCid() {
		return cid;
	}

	//注入一级分类的service
	private CategoryService categoryService;
	//接收当前页数
	private int page;
	public void setPage(int page) {
		this.page = page;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	public Product getModel() {
		return product;
	} 
	
	//根据商品id,查询商品的方法
	public String findByPid(){
		product=productService.findByPid(product.getPid());
		return "findByPid";
	}
	
	//根据一级目录的cid查询商品 
	public String findByCid(){
		//List<Category>clist=categoryService.findAll();
		PageBean<Product>pageBean =  productService.findByPageCid(cid,page);//根据一级分类和当前页数查询商品
		//将数据集合放入值栈
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findByCid";
	}
	
	//根据二级分类的id查询商品
	public String findByCsid() {
		PageBean<Product>pageBean =  productService.findByPageCsid(csid, page);
		//将数据集合放入值栈
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findByCsid";
	}
}
