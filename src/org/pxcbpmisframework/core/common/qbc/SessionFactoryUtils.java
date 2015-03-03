package org.pxcbpmisframework.core.common.qbc;

import org.hibernate.SessionFactory;
/**
 * 
 * @ClassName: SessionFactoryUtils   
 * @Description: TODO(得到SessionFactoryUtils)   
 * @author Mr_Pxc  
 * @date 2013-6-7 下午03:05:15   
 * @project_name：BPMIS_1            
 * @change    
 * @remark    
 * @version 1.0
 */
public class SessionFactoryUtils {
	private static SessionFactory sf;
	
	/** 
	 * @Title: getSessionFactory     
	 * @return SessionFactory
	 * @throws
	 */
	@SuppressWarnings("deprecation")
	public static SessionFactory getSessionFactory() {
		if (sf == null) {
			sf = ConfigurationUtils.getHibernateConfig().buildSessionFactory();
		}
		return sf;
	}

}
