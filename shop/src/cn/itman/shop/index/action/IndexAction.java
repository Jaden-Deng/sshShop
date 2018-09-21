package cn.itman.shop.index.action;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itman.shop.category.service.CategoryService;
import cn.itman.shop.category.vo.Category;
import cn.itman.shop.product.service.ProductService;
import cn.itman.shop.product.vo.Product;
import cn.itman.shop.user.vo.User;

public class IndexAction extends ActionSupport{

	//注入一级分类的service
	private CategoryService categoryService;
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	} 
	//注入商品的service
	private ProductService productService;
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}


	//执行访问首页的方法
	public String execute()  {
		List<Category> clist=categoryService.findAll();
		if(clist==null){
			System.out.println("查到的数据为空");
		}
		//将一级分类存入session
		ActionContext.getContext().getSession().put("clist", clist);
		//查詢熱門商品
		List<Product> hlist=productService.findHot();
		//将hlist保存到值栈当中
		ActionContext.getContext().getValueStack().set("hlist", hlist);
		//查询最新商品
		List<Product> nlist=productService.findNew();
		//将nlist保存到值栈
		ActionContext.getContext().getValueStack().set("nlist", nlist);
		
		return "index";
	}

}