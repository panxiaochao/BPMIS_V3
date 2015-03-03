package org.pxcbpmisframework.core.util;

import java.util.UUID;

/**
 * 
 * @Title: BpmisUtils.java
 * @Package org.pxcbpmisframework.core.util
 * @Description: TODO(内部工具类)
 * @author panxiaochao
 * @date 2014-7-31 下午03:13:14
 * @version V1.0
 */
public class BpmisUtils {

	/**
	 * 获取用户编号,组成6位
	 * 
	 * @return
	 */
	public static String getBpmisCode() {
		String str = UUID.randomUUID().toString();
		return "BPMIS_" + str.substring(9, 13) + str.substring(19, 21);
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			System.out.println(getBpmisCode());
		}
	}
}
