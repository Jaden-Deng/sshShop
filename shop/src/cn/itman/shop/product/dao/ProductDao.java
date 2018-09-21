package cn.itman.shop.product.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import cn.itman.shop.product.vo.Product;
import cn.itman.shop.utils.PageHibernateCallback;


public class ProductDao extends HibernateDaoSupport{
	//首页热门商品的查询
	public List<Product> findHot() {
		//涉及分页查询，使用离线条件查询
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
		//热门商品的"is_hot=1"否则不是热门
		criteria.add(Restrictions.eq("is_hot",1));
		//按时间倒序排序进行输出
		criteria.addOrder(Order.desc("pdate"));
		//执行查询
		List<Product> list=(List<Product>) this.getHibernateTemplate().findByCriteria(criteria, 0, 10);
		return list;
	}

	public List<Product> findNew() {
		//涉及分页查询，使用离线条件查询
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
		//按时间倒序排序进行输出
		criteria.addOrder(Order.desc("pdate"));
		//执行查询
		List<Product> list=(List<Product>) this.getHibernateTemplate().findByCriteria(criteria, 0, 10);
		return list;
	}

	public Product findByPid(Integer pid) {
		
		return this.getHibernateTemplate().get(Product.class, pid);
	}
	
	//按一级分类的cid查询商品总条数
	public int findCountCid(Integer cid) {
		String hql = "select count(*) from Product p where  p.categorySecond.category.cid=?";
		List<Long>list = (List<Long>) this.getHibernateTemplate().find(hql, cid);
		if(list!=null&&list.size()>0){
			return list.get(0).intValue();
		}
		return 0;
	}

	//按cid,当前页数，显示条数，查询商品数据集合

	public List<Product> findByPageCid(Integer cid, int begin, int limit) {
		String hql = "select p from Product p join p.categorySecond cs join cs.category c where c.cid=?";
		//分页new PageHibernateCallback<Product>(hql, new Object[]{cid}, begin, limit)
		List<Product> list = (List<Product>) this.getHibernateTemplate().execute(new PageHibernateCallback<Product>(hql,new Object[]{cid}, begin, limit));
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}

	//按二级分类的csid查询商品总条数
	public int findCountCsid(Integer csid) {
		String hql = "select count(*) from Product p where  p.categorySecond.csid=?";
		List<Long>list = (List<Long>) this.getHibernateTemplate().find(hql, csid);
		if(list!=null&&list.size()>0){
			return list.get(0).intValue();
		}
		return 0;
	}
	//按csid,当前页数，显示条数，查询商品数据集合
	public List<Product> findByPageCsid(Integer csid, int begin, int limit) {
		String hql = "select p from Product p join p.categorySecond cs  where cs.csid=?";
		//分页new PageHibernateCallback<Product>(hql, new Object[]{cid}, begin, limit)
		List<Product> list = (List<Product>) this.getHibernateTemplate().execute(new PageHibernateCallback<Product>(hql,new Object[]{csid}, begin, limit));
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}

	public int findCount() {
		String hql = "select count(*) from Product";
		List<Long>list = (List<Long>) this.getHibernateTemplate().find(hql);
		if(list!=null&&list.size()>0){
			return list.get(0).intValue();
		}
		return 0;
	}

	public List<Product> findByPage(int begin, int limit) {
		String hql = "from Product order by pdate desc";
		//分页new PageHibernateCallback<Product>(hql, new Object[]{cid}, begin, limit)
		List<Product> list = (List<Product>) this.getHibernateTemplate().execute(new PageHibernateCallback<Product>(hql,null, begin, limit));
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}

	//添加商品
	public void save(Product product) {
		this.getHibernateTemplate().save(product);
	}
	
	//删除商品
	public void delete(Product product) {
		this.getHibernateTemplate().delete(product);
	}

	//更新商品
	public void update(Product product) {
		this.getHibernateTemplate().update(product);
	}

}
