package cn.itman.shop.categorysecond.dao;

import java.util.List;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cn.itman.shop.categorysecond.vo.CategorySecond;
import cn.itman.shop.product.vo.Product;
import cn.itman.shop.utils.PageHibernateCallback;

public class CategorySecondDao extends HibernateDaoSupport{

	//查询总记录数
	public int findCount() {
		String hql = "select count(*) from CategorySecond";
		List<Long> list = (List<Long>) this.getHibernateTemplate().find(hql);
		if(list!=null&&list.size()>0){
			return list.get(0).intValue();
		}
		
		return 0;
	}

	//分页查询二级分类数据
	public List<CategorySecond> findfindByPage(int begin, int limit) {
		String hql = "from CategorySecond order by csid desc";
		List<CategorySecond> list = this.getHibernateTemplate().execute(new PageHibernateCallback<CategorySecond>(hql,null, begin, limit));
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}
	
	//后台保存二级分类目录
	public void save(CategorySecond categorySecond) {
		this.getHibernateTemplate().save(categorySecond);
	}

	public CategorySecond findByCsid(Integer csid) {
		return this.getHibernateTemplate().get(CategorySecond.class, csid);
	}

	public void delete(CategorySecond categorySecond) {
		this.getHibernateTemplate().delete(categorySecond);
	}

	public void update(CategorySecond categorySecond) {
		this.getHibernateTemplate().update(categorySecond);
	}

	//查询所有二级分类
	public List<CategorySecond> findAll() {
		String hql = "from CategorySecond";
		return (List<CategorySecond>) this.getHibernateTemplate().find(hql);
	}

	
	
}
