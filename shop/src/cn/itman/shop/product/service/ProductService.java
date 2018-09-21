package cn.itman.shop.product.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.itman.shop.product.dao.ProductDao;
import cn.itman.shop.product.vo.Product;
import cn.itman.shop.utils.PageBean;
/**
 * 商品的業務層代碼
 * @author Deng
 *
 */
@Transactional
public class ProductService {
	//注入ProductDao
	private ProductDao productDao;

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}
	//首页显示热门商品(查询)
	public List<Product> findHot() {
		return productDao.findHot();
	}
	//首页显示热门商品（查询）
	public List<Product> findNew() {
		return productDao.findNew();
	}
	public Product findByPid(Integer pid) {
		return productDao.findByPid(pid);
	}
	//根据一级分类cid带有分页的查询商品
	public PageBean<Product> findByPageCid(Integer cid, int page) {
		PageBean<Product>pageBean = new PageBean<Product>();
		//设置当前页数
		pageBean.setPage(page);
		//设置每页显示条数
		int limit = 8;
		pageBean.setLimit(limit);
		//设置总记录数(要查询数据库)
		int totalCount = 0;
		totalCount = productDao.findCountCid(cid);
		pageBean.setTotalCount(totalCount);
		//设置总页数
		int totalPage = 0;
		totalPage=(totalCount+limit-1)/limit;
		pageBean.setTotalPage(totalPage);
		//每页显示的数据集合
		//开始的页数
		int begin=(page-1)*limit;
		List<Product>list = productDao.findByPageCid(cid,begin,limit);
		pageBean.setList(list);
		
		return pageBean;
	}
	public PageBean<Product> findByPageCsid(Integer csid, int page) {
		PageBean<Product>pageBean = new PageBean<Product>();
		//设置当前页数
		pageBean.setPage(page);
		//设置每页显示条数
		int limit = 8;
		pageBean.setLimit(limit);
		//设置总记录数(要查询数据库)
		int totalCount = 0;
		totalCount = productDao.findCountCsid(csid);
		pageBean.setTotalCount(totalCount);
		//设置总页数
		int totalPage = 0;
		totalPage=(totalCount+limit-1)/limit;
		pageBean.setTotalPage(totalPage);
		//每页显示的数据集合
		//开始的页数
		int begin=(page-1)*limit;
		List<Product>list = productDao.findByPageCsid(csid,begin,limit);
		pageBean.setList(list);
		
		return pageBean;
	}
	
	//分页查询所有商品
	public PageBean<Product> findByPage(int page) {
		PageBean<Product>pageBean = new PageBean<Product>();
		//设置当前页数
		pageBean.setPage(page);
		//设置每页显示条数
		int limit = 8;
		pageBean.setLimit(limit);
		//设置总记录数(要查询数据库)
		int totalCount = 0;
		totalCount = productDao.findCount();
		pageBean.setTotalCount(totalCount);
		//设置总页数
		int totalPage = 0;
		totalPage=(totalCount+limit-1)/limit;
		pageBean.setTotalPage(totalPage);
		//每页显示的数据集合
		//开始的页数
		int begin=(page-1)*limit;
		List<Product>list = productDao.findByPage(begin,limit);
		pageBean.setList(list);
		
		return pageBean;
	}
	
	//添加商品
	public void save(Product product) {
		productDao.save(product);
	}
	
	//删除商品
	public void delete(Product product) {
		productDao.delete(product);
	}
	
	//更新商品
	public void update(Product product) {
		productDao.update(product);
	}

}
