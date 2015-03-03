package bpmis.pxc.system.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.pxcbpmisframework.core.common.qbc.CriteriaQuery;
import org.pxcbpmisframework.core.common.qbc.HqlQuery;
import org.pxcbpmisframework.core.exception.BusinessException;
import org.pxcbpmisframework.core.page.Page;
import org.pxcbpmisframework.core.page.PageHtmlUtils;

public class SystemDaoImpl {
	private static final Logger logger = Logger.getLogger(SystemDaoImpl.class);
	private SessionFactory sessionFactory;

	@Resource(name = "sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/*
	 * <---------------implements method----------------->
	 */
	/**
	 * @Title: delete
	 */
	public <T> void delete(T entity) {
		// TODO Auto-generated method stub
		try {
			getSession().delete(entity);
			getSession().flush();
		} catch (RuntimeException e) {
			logger.error(e);
			throw new BusinessException(e);
		} finally {
			// s.close();
		}
	}

	/**
	 * @Title: deleteAll
	 */
	public <T> void deleteAll(List<T> entityAll) {
		// TODO Auto-generated method stub
		try {
			for (int i = 0; i < entityAll.size(); i++) {
				getSession().delete(entityAll.get(i));
				getSession().flush();
			}
		} catch (RuntimeException e) {
			logger.error(e);
			throw new BusinessException(e);
		} finally {
			// s.close();
		}
	}

	/**
	 * @Title: deleteAll
	 */
	public <T> void deleteAll(Class<T> entityName, String[] idstr,
			List<T> idlist) {
		// TODO Auto-generated method stub
		if (idstr == null) { // 采用List
			try {
				for (int i = 0; i < idlist.size(); i++) {
					Object object = getClassById(entityName, idlist.get(i)
							.toString());
					getSession().delete(object);
					getSession().flush();
				}
			} catch (RuntimeException e) {
				logger.error(e);
				throw new BusinessException(e);
			} finally {
				// s.close();
			}
		} else {// 采用String[]
			try {
				for (int i = 0; i < idstr.length; i++) {
					Object object = getClassById(entityName, idstr[i]);
					getSession().delete(object);
					getSession().flush();
				}
			} catch (RuntimeException e) {
				logger.error(e);
				throw new BusinessException(e);
			} finally {
				// s.close();
			}
		}

	}

	/**
	 * @Title: deleteEntityByHql
	 */
	public <T> void deleteEntityByHql(String hql) {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * @param <T>
	 * @param loadAll
	 * @return
	 */
	public <T> int loadAll(Class<T> entityName) {
		List<T> list = ByCrifindQuery(entityName);
		if (list != null && list.size() > 0)
			return list.size();
		return 0;
	}

	/**
	 * @Title: deleteEntityById
	 */
	public <T> void deleteEntityById(Class<T> entityName, String id) {
		// TODO Auto-generated method stub
		try {
			Object object = getClassById(entityName, id);
			getSession().delete(object);
			getSession().flush();
		} catch (RuntimeException e) {
			logger.error(e);
			throw new BusinessException(e);
		} finally {
			// s.close();
		}
	}

	/**
	 * @Title: deleteEntityById
	 */
	public <T> void deleteEntityById(Class<T> entityName, Integer id) {
		// TODO Auto-generated method stub
		try {
			Object object = getClassById(entityName, id);
			getSession().delete(object);
			getSession().flush();
		} catch (RuntimeException e) {
			logger.error(e);
			throw new BusinessException(e);
		} finally {
			// s.close();
		}
	}

	/**
	 * @Title: getClassById
	 */
	public <T> Object getClassById(Class<T> clazz, String id) {
		// TODO Auto-generated method stub
		Object obj = null;
		try {
			obj = getSession().load(clazz, id);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException(e);
		} finally {
			// s.close();
		}
		return obj;
	}

	/**
	 * @Title: getClassById
	 */
	public <T> Object getClassById(Class<T> clazz, Integer id) {
		// TODO Auto-generated method stub
		Object obj = null;
		try {
			obj = getSession().load(clazz, id);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException(e);
		} finally {
			// s.close();
		}
		return obj;
	}

	/**
	 * @Title: save
	 */
	public <T> void save(T entity) {
		// TODO Auto-generated method stub
		try {
			getSession().save(entity);
			getSession().flush();
		} catch (RuntimeException e) {
			logger.error(e);
			throw new BusinessException(e);
		}
	}

	/**
	 * @Title: saveAll
	 */
	public <T> void saveAll(List<T> entityAll) {
		// TODO Auto-generated method stub
		try {
			for (int i = 0; i < entityAll.size(); i++) {
				getSession().save(entityAll.get(i));
				getSession().flush();
			}
		} catch (RuntimeException e) {
			logger.error(e);
			throw new BusinessException(e);
		}
	}

	/**
	 * @Title: saveOrUpdata
	 */
	public <T> void saveOrUpdata(T entity) {
		// TODO Auto-generated method stub
		try {
			getSession().saveOrUpdate(entity);
			getSession().flush();
		} catch (RuntimeException e) {
			logger.error(e);
			throw new BusinessException(e);
		} finally {
			// s.close();
		}
	}

	/**
	 * @Title: update
	 */
	public <T> void update(T entity) {
		// TODO Auto-generated method stub
		try {
			getSession().update(entity);
			getSession().flush();
		} catch (RuntimeException e) {
			logger.error(e);
			throw new BusinessException(e);
		} finally {
			// s.close();
		}
	}

	/**
	 * 通过sql操作记录
	 * 
	 * @param <T>
	 * @param query
	 * @return
	 */
	public int executeBySql(String query) {
		Query querys = getSession().createSQLQuery(query);
		return querys.executeUpdate();
	}

	/**
	 * 通过sql操作记录
	 * 
	 * @param <T>
	 * @param query
	 * @return
	 */
	public int executeByHql(String hql) {
		Query querys = getSession().createQuery(hql);
		return querys.executeUpdate();
	}

	/**
	 * @Title: findByQueryHql
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> findByQueryHql(String hql) {
		// TODO Auto-generated method stub
		List<T> list = getSession().createQuery(hql).list();
		if (list.size() > 0) {
			getSession().flush();
		}
		return list;
	}

	/**
	 * 通过sql查询语句查找对象
	 * 
	 * @param <T>
	 * @param query
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> findByQuerySql(String sql) {
		Query querys = getSession().createSQLQuery(sql);
		List<T> list = querys.list();
		if (list.size() > 0) {
			getSession().flush();
		}
		return list;
	}

	// ******************************************************************
	// ---------------------下面采用Criteria方法--------------------------
	// ******************************************************************
	private DetachedCriteria detachedCriteria;

	public DetachedCriteria getDetachedCriteria() {
		return detachedCriteria;
	}

	public void setDetachedCriteria(DetachedCriteria detachedCriteria) {
		this.detachedCriteria = detachedCriteria;
	}

	/**
	 * 得到Criteria
	 * 
	 * @Title: getCriteria
	 */
	public <T> Criteria getCriteria(Class<T> clazz) {
		return getSession().createCriteria(clazz);
	}

	/**
	 * @Title: ByCrifindQuery
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> ByCrifindQuery(Class<T> clazz) {
		// TODO Auto-generated method stub
		return getCriteria(clazz).list();
	}

	/**
	 * @Title: ByCrifindQuery
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> ByCrifindQuery(Class<T> clazz, boolean isAsc,
			String ordername) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(clazz);
		if (isAsc)
			criteria.addOrder(Order.asc(ordername));
		else
			criteria.addOrder(Order.desc(ordername));
		return criteria.list();
	}

	/**
	 * @Title: ByCrifindQuery
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> ByCrifindQuery(Class<T> entityClass,
			Map<String, Integer> parms) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(entityClass);
		Set<String> setkey = parms.keySet();
		for (Object key : setkey) {
			Integer value = parms.get(key);
			criteria.add(Restrictions.eq(key.toString(), value));
		}
		return criteria.list();
	}

	/**
	 * @Title: ByCrifindQuery
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> ByCrifindQueryForObj(Class<T> entityClass,
			Map<Object, Object> parms) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(entityClass);
		Set<Object> setkey = parms.keySet();
		for (Object key : setkey) {
			Object value = parms.get(key);
			criteria.add(Restrictions.eq(key.toString(), value));
		}
		return criteria.list();
	}

	/**
	 * 分页,返回pageHtml
	 */

	@SuppressWarnings( { "static-access", "unchecked" })
	public Map<?, ?> getPageList(Class<?> clazz, CriteriaQuery cq, Page page) {
		PageHtmlUtils pageHtml = new PageHtmlUtils();
		Map map = new HashMap();
		//
		Criteria criteria = getCriteria(clazz);
		if (cq.isAsc()) {
			if (!"".equals(cq.getFiled()) && cq.getFiled() != null)
				criteria.addOrder(Order.asc(cq.getFiled()));
		} else {
			if (!"".equals(cq.getFiled()) && cq.getFiled() != null)
				criteria.addOrder(Order.desc(cq.getFiled()));
		}
		/**
		 * extend Map parms by panxiaochao created 2013.12.27
		 */
		if (cq.getMapparm() != null) {
			Set<Object> setkey = cq.getMapparm().keySet();
			for (Object key : setkey) {
				criteria.add(Restrictions.eq(key.toString(), cq.getMapparm()
						.get(key)));
			}
		}
		// totalRecords
		// int totalRecords = ((Long)
		// criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		// //总记录数,不是符合条件的记录数
		int totalRecords = criteria.list().size(); // 统计符合条件数据的总数
		cq.setTotalRecords(totalRecords);
		//
		int currentPage = page.getCurrentPage();
		int pageSize = page.getPageSize();
		criteria.setProjection(null);// 清空projection，以便取得记录
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);// 设置查询结果为实体对象，
		criteria.setFirstResult((currentPage - 1) * pageSize);
		criteria.setMaxResults(pageSize);
		String pagehtml = pageHtml.getPageDcp(page);

		map.put("list", criteria.list());
		map.put("total", totalRecords);
		map.put("pagehtml", pagehtml);
		return map;
	}

	/**
	 * 分页,返回pageHtml
	 */
	@SuppressWarnings("unchecked")
	public Map<?, ?> getPageList(Class<?> clazz, CriteriaQuery cq) {
		// PageHtmlUtils pageHtml = new PageHtmlUtils();
		Map map = new HashMap();
		Page page = new Page();
		page = cq.getPage();
		//
		Criteria criteria = getCriteria(clazz);
		if (cq.isAsc()) {
			if (!"".equals(cq.getFiled()) && cq.getFiled() != null)
				criteria.addOrder(Order.asc(cq.getFiled()));
		} else {
			if (!"".equals(cq.getFiled()) && cq.getFiled() != null)
				criteria.addOrder(Order.desc(cq.getFiled()));
		}
		/**
		 * extend Map parms by panxiaochao created 2013.12.27
		 */
		if (cq.getMapparm() != null) {
			Set<Object> setkey = cq.getMapparm().keySet();
			for (Object key : setkey) {
				criteria.add(Restrictions.eq(key.toString(), cq.getMapparm()
						.get(key)));
			}
		}
		// totalRecords
		// int totalRecords = ((Long)
		// criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		// //总记录数,不是符合条件的记录数
		int totalRecords = criteria.list().size(); // 统计符合条件数据的总数
		cq.setTotalRecords(totalRecords);
		//
		int currentPage = page.getCurrentPage();
		int pageSize = page.getPageSize();
		criteria.setProjection(null);// 清空projection，以便取得记录
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);// 设置查询结果为实体对象，
		criteria.setFirstResult((currentPage - 1) * pageSize);
		criteria.setMaxResults(pageSize);
		// String pagehtml = pageHtml.getPageDcp(page);
		// cq.setReaults(criteria.list());
		// cq.setPage(page);
		map.put("list", criteria.list());
		map.put("total", totalRecords);
		// map.put("pagehtml", pagehtml);
		return map;
	}

	/**
	 * 分页,新版，采用CriteriaQuery集中处理
	 */
	public CriteriaQuery getPageList2(Class<?> clazz, CriteriaQuery cq) {
		Criteria criteria = getCriteria(clazz);

		/*
		 * extend Map parms by panxiaochao created 2014.07.31
		 */
		if (cq.getMaporder() != null) {
			Set<Object> setkey = cq.getMaporder().keySet();
			for (Object key : setkey) {
				if (key.equals("asc"))
					criteria.addOrder(Order.asc(cq.getMaporder().get(key)
							.toString()));
				else if (key.equals("desc"))
					criteria.addOrder(Order.desc(cq.getMaporder().get(key)
							.toString()));
			}
		}
		/**
		 * extend Map parms by panxiaochao created 2013.12.27 update Map parms
		 * by panxiaochao 2014.08.20
		 * 
		 * update 2014.10.28 增加多个and的连接
		 */
		if (cq.getMapparms() != null && cq.getMapparms().size() > 0) {
			Set<Object> setkey = cq.getMapparms().keySet();
			for (Object key : setkey) {
				String[] temp = (String[]) cq.getMapparms().get(key);
				if (temp[0].equals(HqlQuery.Restrictions_eq)) {
					criteria.add(Restrictions.eq(key.toString(), temp[1]));
				} else if (temp[0].equals(HqlQuery.Restrictions_like)) {
					criteria.add(Restrictions.like(key.toString(), temp[1]));
				} else if (temp[0].equals(HqlQuery.Restrictions_between)) {
					criteria.add(Restrictions.between(key.toString(), temp[1],
							temp[2]));
				}
				// 可扩展
			}
		}

		// totalRecords
		// int totalRecords = ((Long)
		// criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		// //总记录数,不是符合条件的记录数
		int totalRecords = criteria.list().size(); // 统计符合条件数据的总数
		cq.setTotalRecords(totalRecords);
		//
		int currentPage = cq.getCurrentPage();
		int pageSize = cq.getPageSize();
		criteria.setProjection(null);// 清空projection，以便取得记录
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);// 设置查询结果为实体对象，
		criteria.setFirstResult((currentPage - 1) * pageSize);
		criteria.setMaxResults(pageSize);

		// resaults
		cq.setReaults(criteria.list());
		return cq;
	}
}
