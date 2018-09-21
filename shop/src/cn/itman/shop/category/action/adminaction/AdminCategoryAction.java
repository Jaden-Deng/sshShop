package cn.itman.shop.category.action.adminaction;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itman.shop.category.service.CategoryService;
import cn.itman.shop.category.vo.Category;
import cn.itman.shop.categorysecond.vo.CategorySecond;

/**
 * 后台一级分类的action
 * @author Deng
 *
 */
public class AdminCategoryAction extends ActionSupport	implements ModelDriven<Category>{
	private Category category = new Category();
	//注入service
	private CategoryService categoryService;
	
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public Category getModel() {
		return category;
	}
	
	//后台执行查询所有一级分类的方法
	public String findAll() {
		List<Category>cList = categoryService.findAll();
		//将数据放入到值栈
		ActionContext.getContext().getValueStack().set("cList", cList);
		
		return "findAll";
	}
	
	//后台管理， 添加新的一级分类
	public String save(){
		categoryService.save(category);
		return "saveSuccess";
	}
	
	//后台管理，删除一级分类
	public String delete(){
		//删除一级类同时删除二级分类，先根据id查询再进行删除（category映射文件里二级分类的set集合已配置cascade="all" 可进行级联删除）
		category = categoryService.findByCid(category.getCid());
		//进行删除
		categoryService.delete(category);
		return "deleteSuccess";
	}
	
	//后台编辑一级分类
	public String edit(){
		//先查询一级分类
		category = categoryService.findByCid(category.getCid());
		//跳转到编辑页面
		return "editSuccess";
	}
	
	//修改一级分类的方法
	public String update(){
		categoryService.update(category);
		return "updateSuccess";
	}
	
}
