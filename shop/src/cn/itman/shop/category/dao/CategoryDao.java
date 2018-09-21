package cn.itman.shop.category.dao;

import java.util.List;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import cn.itman.shop.category.vo.Category;

/**
 * 一级分类的持久层对象
 * @author Deng
 *
 */
public class CategoryDao extends HibernateDaoSupport{
	//查询一级分类
	public List<Category> findAll() {
		String hql="from Category";
		List<Category> clist= (List<Category>) this.getHibernateTemplate().find(hql);
		if(clist!=null&&clist.size()>0){
			return clist;
		}
		return null;
	}
	
	//保存以及分类
	public void save(Category category) {
		this.getHibernateTemplate().save(category);
	}

	public Category findByCid(Integer cid) {
		return this.getHibernateTemplate().get(Category.class, cid);
	}

	public void delete(Category category) {
		this.getHibernateTemplate().delete(category);
	}

	public void update(Category category) {
		this.getHibernateTemplate().update(category);
	}

}
