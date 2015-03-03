package org.pxcbpmisframework.core.util;

/**
 * 
 * @author panxiaochao
 * 
 */
public class ImportUtil {
	static final String POJO_TYPE = "bpmis.pxc.system.pojo.";
	/**
	 * 得到实体类
	 * 
	 * @param fullentity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Class getEntityClass(String fullentity) {
		Class entityClass = null;
		try {
			entityClass = (Class) Class.forName(fullentity);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return entityClass;
	}
	
	/**
	 * 得到实体类
	 * 
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Class getEntityClassI(String entity) {
		Class entityClass = null;
		try {
			entityClass = (Class) Class.forName(POJO_TYPE+entity);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return entityClass;
	}
}
