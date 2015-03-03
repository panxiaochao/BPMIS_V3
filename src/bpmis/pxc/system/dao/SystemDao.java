package bpmis.pxc.system.dao;

import java.util.List;
import java.util.Map;

import org.pxcbpmisframework.core.common.qbc.CriteriaQuery;
import org.pxcbpmisframework.core.page.Page;

import bpmis.pxc.system.pojo.TUser;

public interface SystemDao {
	public <T> void save(T entity);

	public <T> void saveOrUpdata(T entity);

	public <T> void saveAll(List<T> entityAll);

	public <T> void update(T entity);

	public <T> void delete(T entity);

	public <T> void deleteEntityById(Class<T> entityName, String id);

	public <T> void deleteEntityById(Class<T> entityName, Integer id);

	public <T> void deleteEntityByHql(String hql);

	public <T> void deleteAll(List<T> entityAll);

	public <T> void deleteAll(Class<T> entityName, String[] idstr,
			List<T> idlist);

	public <T> int loadAll(Class<T> entityName);

	public <T> List<T> ByCrifindQuery(Class<T> clazz);

	public <T> List<T> ByCrifindQuery(Class<T> clazz, boolean isAsc,
			String ordername);

	public <T> List<T> ByCrifindQuery(Class<T> entityClass,
			Map<String, Integer> parms);

	public <T> List<T> ByCrifindQueryForObj(Class<T> entityClass,
			Map<Object, Object> parms);

	public Map<?, ?> getPageList(Class<?> clazz, CriteriaQuery cq, Page page);

	public Map<?, ?> getPageList(Class<?> clazz, CriteriaQuery cq);
	public CriteriaQuery getPageList2(Class<?> clazz, CriteriaQuery cq);
	public <T> Object getClassById(Class<T> clazz, String id);

	public <T> List<T> findByQueryHql(String hql);

	public <T> List<T> findByQuerySql(String sql);

	public int executeByHql(String hql);

	public int executeBySql(String sql);

	// extend
	public TUser checkUserExits(String username, String passwordMd5);
}
