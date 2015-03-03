package org.pxcbpmisframework.core.common.qbc;

import org.hibernate.cfg.Configuration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @ClassName: ConfigurationUtils
 * @Description: TODO(ConfigurationUtils)
 * @author Mr_Pxc
 * @date 2013-6-7 下午03:17:19
 * @project_name：BPMIS_1
 * @change
 * @remark
 * @version 1.0
 */
public class ConfigurationUtils {
	private static final String DEFAULT_HIBERNATE_CFG = "/hibernate.cfg.xml";
	private static final String DEFAULT_APPLILICATIONCOTEXT_CFG = "/applicationContext.xml";
	//private static Configuration configuration = new Configuration();

	private ConfigurationUtils() {
	}

	/** 
	 * @Title: getHibernateConfig
	 * @Description: TODO(返回一个Hibernate的Config)
	 * @throws
	 */
	public static Configuration getHibernateConfig() {
		return new Configuration().configure(DEFAULT_HIBERNATE_CFG);
	}
	
	/** 
	 * @Title: getApplicationContextConfig
	 * @Description: TODO(返回一个applicationcontext的Config)
	 * @throws
	 */
	public static ApplicationContext getApplicationContextConfig() {
		return new ClassPathXmlApplicationContext(DEFAULT_APPLILICATIONCOTEXT_CFG);
	}

}
