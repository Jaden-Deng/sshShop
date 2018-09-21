package cn.itman.shop.categorysecond.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.itman.shop.category.vo.Category;
import cn.itman.shop.categorysecond.dao.CategorySecondDao;
import cn.itman.shop.categorysecond.vo.CategorySecond;
import cn.itman.shop.product.vo.Product;
import cn.itman.shop.utils.PageBean;

@Transactional
public class CategorySecondService {
	private CategorySecondDao categorySecondDao;

	public void setCategorySecondDao(CategorySecondDao categorySecondDao) {
		this.categorySecondDao = categorySecondDao;
	}

	//分页查询所有二级分类
	public PageBean<CategorySecond> findAll(Integer page) {
		PageBean<CategorySecond> pageBean = new PageBean<CategorySecond>();
		pageBean.setPage(page);
		int limit = 8;
		pageBean.setLimit(limit);
		//查找总记录数
		int totalCount = categorySecondDao.findCount();
		pageBean.setTotalCount(totalCount);
		//设置总页数
		int totalPage = 0;
		totalPage=(totalCount+limit-1)/limit;
		pageBean.setTotalPage(totalPage);
		
		//开始的页数
		int begin=(page-1)*limit;
		List<CategorySecond>list = categorySecondDao.findfindByPage(begin,limit);
		pageBean.setList(list);
		
		return pageBean;
	}

	//业务层后台保存二级分类
	public void save(CategorySecond categorySecond) {
		categorySecondDao.save(categorySecond);
	}

	public CategorySecond findByCsid(Integer csid) {
		
		return categorySecondDao.findByCsid(csid);
	}

	public void delete(CategorySecond categorySecond) {
		categorySecondDao.delete(categorySecond);
	}

	public void update(CategorySecond categorySecond) {
		categorySecondDao.update(categorySecond);
	}

	//查询所有二级分类目录
	public List<CategorySecond> findAll() {
		return categorySecondDao.findAll();
	}
	
}
