package org.pxcbpmisframework.core.common.qbc;

import java.util.List;
import java.util.Map;

import org.pxcbpmisframework.core.page.Page;

/**
 * 
 * @Title: CriteriaQuery.java
 * @Package org.pxcbpmisframework.core.common.qbc
 * @Description: TODO(完善)
 * @author panxiaochao
 * @date 2014-7-31 下午03:59:00
 * @version V2.0
 */
public class CriteriaQuery {
	private boolean isAsc = false;
	private int currentPage = 1;// 当前页
	private int pageSize = 10;// 默认一页条数
	private int totalPages;// 总页数
	private int totalRecords; // 总计路数
	private String myAction;// 请求的action 地址
	private String myForm;// form 名字
	private String filed;// 排序字段
	private Map<Object, Object> maporder = null;// 排序字段
	private Map<Object, Object> mapparm = null; // 查询字段
	private Map<Object, String[]> mapparms = null; // 查询字段2
	private List<?> reaults;// 结果集
	private Class<?> entityClass;// POJO
	private Page page;

	public CriteriaQuery() {
	}

	public CriteriaQuery(boolean isAsc, String filed) {
		super();
		this.isAsc = isAsc;
		this.filed = filed;
	}

	public CriteriaQuery(String filed) {
		super();
		this.filed = filed;
	}

	public List<?> getReaults() {
		return reaults;
	}

	public void setReaults(List<?> reaults) {
		this.reaults = reaults;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public boolean isAsc() {
		return isAsc;
	}

	public void setAsc(boolean isAsc) {
		this.isAsc = isAsc;
	}

	public String getFiled() {
		return filed;
	}

	public void setFiled(String filed) {
		this.filed = filed;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public String getMyAction() {
		return myAction;
	}

	public void setMyAction(String myAction) {
		this.myAction = myAction;
	}

	public String getMyForm() {
		return myForm;
	}

	public void setMyForm(String myForm) {
		this.myForm = myForm;
	}

	public Class<?> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
	}

	public Map<Object, Object> getMaporder() {
		return maporder;
	}

	public void setMaporder(Map<Object, Object> maporder) {
		this.maporder = maporder;
	}

	public Map<Object, Object> getMapparm() {
		return mapparm;
	}

	public void setMapparm(Map<Object, Object> mapparm) {
		this.mapparm = mapparm;
	}

	public Map<Object, String[]> getMapparms() {
		return mapparms;
	}

	public void setMapparms(Map<Object, String[]> mapparms) {
		this.mapparms = mapparms;
	}

}
