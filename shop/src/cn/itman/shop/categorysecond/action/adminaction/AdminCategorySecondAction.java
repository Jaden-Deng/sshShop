package cn.itman.shop.categorysecond.action.adminaction;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itman.shop.category.service.CategoryService;
import cn.itman.shop.category.vo.Category;
import cn.itman.shop.categorysecond.service.CategorySecondService;
import cn.itman.shop.categorysecond.vo.CategorySecond;
import cn.itman.shop.utils.PageBean;

/**
 * 二级分类的后台管理类
 * @author Deng
 *
 */
public class AdminCategorySecondAction extends ActionSupport implements ModelDriven<CategorySecond>{
	private CategorySecond categorySecond = new CategorySecond();
	private CategorySecondService categorySecondService;
	private Integer page;
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	private  CategoryService categoryService;
	public void setCategorySecond(CategorySecond categorySecond) {
		this.categorySecond = categorySecond;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public CategorySecond getModel() {
		return categorySecond;
	}
	public void setCategorySecondService(CategorySecondService categorySecondService) {
		this.categorySecondService = categorySecondService;
	}
	
	//查询所有的二级分类
	public String findAll(){
		PageBean<CategorySecond> pageBean = categorySecondService.findAll(page);
		//将数据保存到值栈
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findAll";
	}
	
	//跳转到二级分类添加页面
	public String addPage() {
		//查询所有一级分类
		List<Category> clist =categoryService.findAll();
		//将数据显示到下拉列表中
		ActionContext.getContext().getValueStack().set("clist", clist);
		return "addPage";
	}
	
	//添加二级分类目录
	public String save(){
		categorySecondService.save(categorySecond);
		return "saveSuccess";
	}
	
	//删除二级分类目录
	public String delete(){
		//级联删除，先查询再删除
		categorySecond = categorySecondService.findByCsid(categorySecond.getCsid());
		categorySecondService.delete(categorySecond);
		return "deleteSuccess";
	}
	
	//跳转到编辑页面
	public String edit() {
		//查询所有一级分类
		categorySecond = categorySecondService.findByCsid(categorySecond.getCsid());
		//查询所有一级分类
		List<Category> clist =categoryService.findAll();
		ActionContext.getContext().getValueStack().set("clist", clist);
		//将数据显示到页面
		return "editPage";
	}
	
	//修改二级分类的方法
	public String update(){
		categorySecondService.update(categorySecond);
		return "updateSuccess";
	}
	
}
