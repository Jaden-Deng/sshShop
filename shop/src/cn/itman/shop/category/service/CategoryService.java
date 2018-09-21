package cn.itman.shop.category.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.itman.shop.category.dao.CategoryDao;
import cn.itman.shop.category.vo.Category;


/**
 * 一级分类的业务层对象
 * @author Deng
 *
 */
@Transactional
public class CategoryService {
	private CategoryDao categoryDao;

	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}
	//查询所有一级分类
	public List<Category> findAll() {
		
		return categoryDao.findAll();
	}
	
	//业务层后台保存一级分类
	public void save(Category category) {
		categoryDao.save(category);
	}
	
	//业务层，查询category
	public Category findByCid(Integer cid) {

		return categoryDao.findByCid(cid);
	}
	
	//删除一级分类
	public void delete(Category category) {
		 categoryDao.delete(category);
	}
	
	//修改一级分类的方法
	public void update(Category category) {
		categoryDao.update(category);
	}

	
}
