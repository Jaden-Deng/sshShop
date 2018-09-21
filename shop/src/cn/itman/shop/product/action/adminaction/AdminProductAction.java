package cn.itman.shop.product.action.adminaction;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itman.shop.categorysecond.service.CategorySecondService;
import cn.itman.shop.categorysecond.vo.CategorySecond;
import cn.itman.shop.product.service.ProductService;
import cn.itman.shop.product.vo.Product;
import cn.itman.shop.utils.PageBean;

/**
 * 商品后台管理的action
 * @author Deng
 *
 */
public class AdminProductAction extends ActionSupport implements ModelDriven<Product>{

	private Product product = new Product();
	private ProductService productService;
	private CategorySecondService categorySecondService;
	private int page;
	public void setCategorySecondService(CategorySecondService categorySecondService) {
		this.categorySecondService = categorySecondService;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public Product getModel() {
		return product;
	}
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
	//文件上传需要的参数
	private File upload;//上传的文件，与表单的name相同
	private String uploadFileName; //接收文件上传的文件名
	private String uploadContextType; //接收文件的MIME类型
	
	
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public void setUploadContextType(String uploadContextType) {
		this.uploadContextType = uploadContextType;
	}
	//分页查询所有商品
	public String findAll(){
		PageBean<Product> pageBean = productService.findByPage(page);
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findAll";
	}
	
	//跳转到添加商品页面
	public String addPage(){
		//先查询所有二级分类的商品
		List<CategorySecond> cslist = categorySecondService.findAll();
		//存入值栈
		ActionContext.getContext().getValueStack().set("cslist", cslist);
		
		return "addPage";
	}
	
	//添加新商品
	public String save() throws IOException{
		product.setPdate(new Date());

		if(upload!=null){
			//先获得文件上传到的磁盘绝对路径
			String realPath = ServletActionContext.getServletContext().getRealPath("/products");
			//创建一个文件
			File diskFile = new File(realPath+"//"+uploadFileName);
			//上传
			FileUtils.copyFile(upload,diskFile);
			product.setImage("products/"+uploadFileName);
			
		}
		productService.save(product);
		
		return "saveSuccess";		
	}
	
	//删除商品的方法
	public String delete(){
		product = productService.findByPid(product.getPid());
		
		//删除图片
		String path = product.getImage();
		if(path != null){
			String realPath = ServletActionContext.getServletContext().getRealPath("/"+path);
			File file = new File(realPath);
			file.delete();
		}
		
		productService.delete(product);
		
		return "deleteSuccess";
	}
	
	//编辑商品信息
	public String edit(){
		product = productService.findByPid(product.getPid());
		//查询二级分类		
		List<CategorySecond> cslist = categorySecondService.findAll();
		//存入值栈
		ActionContext.getContext().getValueStack().set("cslist", cslist);
		
		return "editPage";
	}	
	
	//修改商品的方法
	public String update() throws IOException{
		product.setPdate(new Date());
		//上传新图
		if(upload!=null){
			//x先删除原来的图片
			String delPath = product.getImage();
			File delFile = new File(ServletActionContext.getServletContext().getRealPath("/"+delPath));
			delFile.delete();
			//先获得文件上传到的磁盘绝对路径
			String realPath = ServletActionContext.getServletContext().getRealPath("/products");
			//创建一个文件
			File diskFile = new File(realPath+"//"+uploadFileName);
			//上传
			FileUtils.copyFile(upload,diskFile);
			product.setImage("products/"+uploadFileName);
			
		}
		productService.update(product);
		
		return "updateSuccess";
	}
	
	
}
