package org.pxcbpmisframework.core.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
/**
 * 
* @Title: PropertiesUtil.java 
* @Package org.pxcbpmisframework.core.util 
* @Description: TODO(属性文件) 
* @author panxiaochao 
* @date 2014-7-29 上午11:21:26 
* @version V1.0
 */
public class PropertiesUtil {
	private static String properiesName_sys = ResourceUtil.getClassPath()
			+ "bpmis_sys.properties";

	private static Properties props_define = new Properties();
	static {
		try {
			props_define.load(new FileInputStream(properiesName_sys));
			// props_msg.load(new FileInputStream(properiesName_sys));
			// props_token.load(new FileInputStream(properiesName_token));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			System.exit(-1);
		}
	}

	/**
	 * 
	 */
	public static String getKeyValue(String key) {
		return props_define.getProperty(key);
	}

	/**
	 * 
	 */
	public static String getDefineKeyValue(String filename, String key) {
		try {
			props_define.load(new FileInputStream(ResourceUtil.getClassPath()
					+ filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			System.exit(-1);
		}
		return props_define.getProperty(key);
	}

	/**
	 * 
	 */
	public static void writeProperties(String keyname, String keyvalue) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(properiesName_sys);
			props_define.setProperty(keyname, keyvalue);
			props_define.store(os, keyname);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null)
					os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		// System.out.println(PropertiesUtil.getKeyValue_token("access_token"));
		System.out.println(PropertiesUtil.getDefineKeyValue("jdbc.properties",
				"driverClassName"));
	}

}
