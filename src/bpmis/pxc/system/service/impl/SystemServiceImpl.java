package bpmis.pxc.system.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.pxcbpmisframework.core.common.qbc.CriteriaQuery;
import org.pxcbpmisframework.core.page.Page;
import org.pxcbpmisframework.core.util.BrowserUtils;
import org.pxcbpmisframework.core.util.ContextHolderUtils;
import org.pxcbpmisframework.core.util.DataToolsUtils;
import org.pxcbpmisframework.core.util.IpUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bpmis.pxc.system.dao.SystemDao;
import bpmis.pxc.system.dao.jdbc.JdbcTemplateDao;
import bpmis.pxc.system.pojo.TBLogger;
import bpmis.pxc.system.pojo.TUser;
import bpmis.pxc.system.service.SystemService;

@Service("systemService")
@Transactional
public class SystemServiceImpl implements SystemService {
	@Resource
	public SystemDao systemDao;
	@Resource
	public JdbcTemplateDao jdbcTemplateDao;

	/*
	 * <---------------implements method----------------->
	 */

	public <T> List<T> ByCrifindQuery(Class<T> clazz) {
		// TODO Auto-generated method stub
		return systemDao.ByCrifindQuery(clazz);
	}

	public <T> List<T> ByCrifindQuery(Class<T> clazz, boolean isAsc,
			String ordername) {
		// TODO Auto-generated method stub
		return systemDao.ByCrifindQuery(clazz, isAsc, ordername);
	}

	public Map<?, ?> getPageList(Class<?> clazz, CriteriaQuery cq, Page page) {
		// TODO Auto-generated method stub
		return systemDao.getPageList(clazz, cq, page);
	}

	public <T> void delete(T entity) {
		// TODO Auto-generated method stub
		systemDao.delete(entity);
	}

	public <T> void deleteAll(List<T> entityAll) {
		// TODO Auto-generated method stub
		systemDao.deleteAll(entityAll);
	}

	public <T> void deleteAll(Class<T> entityName, String[] idstr,
			List<T> idlist) {
		// TODO Auto-generated method stub
		systemDao.deleteAll(entityName, idstr, idlist);
	}

	public <T> void deleteEntityByHql(String hql) {
		// TODO Auto-generated method stub
		systemDao.deleteEntityByHql(hql);
	}

	public <T> void deleteEntityById(Class<T> entityName, String id) {
		// TODO Auto-generated method stub
		systemDao.deleteEntityById(entityName, id);
	}

	public <T> void deleteEntityById(Class<T> entityName, Integer id) {
		// TODO Auto-generated method stub
		systemDao.deleteEntityById(entityName, id);
	}

	public <T> List<T> findByQueryHql(String hql) {
		// TODO Auto-generated method stub
		return systemDao.findByQueryHql(hql);
	}

	public <T> Object getClassById(Class<T> clazz, String id) {
		// TODO Auto-generated method stub
		return systemDao.getClassById(clazz, id);
	}

	public Map<?, ?> getPageList(Class<?> clazz, CriteriaQuery cq) {
		// TODO Auto-generated method stub
		return systemDao.getPageList(clazz, cq);
	}
	public CriteriaQuery getPageList2(Class<?> clazz, CriteriaQuery cq){
		// TODO Auto-generated method stub
		return systemDao.getPageList2(clazz, cq);
	}
	public <T> void save(T entity) {
		// TODO Auto-generated method stub
		systemDao.save(entity);
	}

	public <T> void saveAll(List<T> entityAll) {
		// TODO Auto-generated method stub
		systemDao.saveAll(entityAll);
	}

	public <T> void update(T entity) {
		// TODO Auto-generated method stub
		systemDao.update(entity);
	}

	public <T> void saveOrUpdata(T entity) {
		// TODO Auto-generated method stub
		systemDao.saveOrUpdata(entity);
	}

	public void addLogger(String logcontent, String loglevel, String operatetype) {
		// TODO Auto-generated method stub
		HttpServletRequest request = ContextHolderUtils.getRequest();
		String broswer = BrowserUtils.getUserAgent(request);

		TUser u = ContextHolderUtils.getSessionUser();
		TBLogger logger = new TBLogger();
		if (u == null)
			logger.setAccount("Guest");
		else {
			logger.setUserid(u.getId());
			logger.setAccount(u.getAccount());
		}
		logger.setLoglevel(loglevel);
		logger.setLogcontent(logcontent);
		logger.setBroswertype(broswer);
		logger.setOperatetime(DataToolsUtils.getSimpleDateFormat());
		logger.setOperatetype(operatetype);
		logger.setAdrip(IpUtils.getServerHostIp());

		systemDao.save(logger);
	}

	public void execute(String sql) {
		// TODO Auto-generated method stub
		jdbcTemplateDao.execute(sql);
	}

	public int queryForInt(String sql) {
		// TODO Auto-generated method stub
		return jdbcTemplateDao.queryForInt(sql);
	}

	public int queryForInt(String sql, Object[] args) {
		// TODO Auto-generated method stub
		return jdbcTemplateDao.queryForInt(sql, args);
	}

	public <T> List<T> queryForList(String sql, Class<T> entityClass) {
		// TODO Auto-generated method stub
		return jdbcTemplateDao.queryForList(sql, entityClass);
	}

	public <T> List<T> queryForList(String sql, Object[] args,
			Class<T> entityClass) {
		// TODO Auto-generated method stub
		return jdbcTemplateDao.queryForList(sql, args, entityClass);
	}

	public String queryForString(String sql) {
		// TODO Auto-generated method stub
		return jdbcTemplateDao.queryForString(sql);
	}

	public String queryForString(String sql, Object[] args) {
		// TODO Auto-generated method stub
		return jdbcTemplateDao.queryForString(sql, args);
	}

	public <T> List<T> queryForList(String sql) {
		// TODO Auto-generated method stub
		return jdbcTemplateDao.queryForList(sql);
	}

	public <T> int loadAll(Class<T> entityName) {
		// TODO Auto-generated method stub
		return systemDao.loadAll(entityName);
	}

	// extend
	public TUser checkUserExits(String username, String passwordMd5) {
		// TODO Auto-generated method stub
		return systemDao.checkUserExits(username, passwordMd5);
	}

	public <T> List<T> ByCrifindQuery(Class<T> entityClass,
			Map<String, Integer> parms) {
		// TODO Auto-generated method stub
		return systemDao.ByCrifindQuery(entityClass, parms);
	}

	public <T> List<T> ByCrifindQueryForObj(Class<T> entityClass,
			Map<Object, Object> parms) {
		return systemDao.ByCrifindQueryForObj(entityClass, parms);
	}

	public int executeByHql(String hql) {
		// TODO Auto-generated method stub
		return systemDao.executeByHql(hql);
	}

	public int executeBySql(String sql) {
		// TODO Auto-generated method stub
		return systemDao.executeBySql(sql);
	}

	public <T> List<T> findByQuerySql(String sql) {
		// TODO Auto-generated method stub
		return systemDao.findByQuerySql(sql);
	}
}
