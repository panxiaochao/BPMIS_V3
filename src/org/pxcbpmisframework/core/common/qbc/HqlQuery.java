package org.pxcbpmisframework.core.common.qbc;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author panxiaochao
 * @date： 日期：2013.05.28
 */
public class HqlQuery implements Query {

	public static String Restrictions_eq = "eq";
	public static String Restrictions_like = "like";
	public static String Restrictions_between = "between";

	/**
	 * 规则：map.put("参数名","值");
	 */
	public static Map<String, Integer> getParmsMaps() {
		return new HashMap<String, Integer>();
	}

	public static Map<Object, String> getHashMaps() {
		return new HashMap<Object, String>();
	}

	public static Map<Object, Object> getHashMapsObj() {
		return new HashMap<Object, Object>();
	}

	public static Map<Object, String[]> getHashMapsObjs() {
		return new HashMap<Object, String[]>();
	}

	/**
	 * hql:from entityname
	 * 
	 * @param entityname
	 * @return
	 */
	public static String getHQLList(String entityname) {
		return (TBFROM + entityname).trim();
	}

	/**
	 * hql:from entityname where xxx = '' and yyy = ''
	 * 
	 * @param entityname
	 * @param value
	 * @return 返回基本hql语句
	 */
	public static String getHqlEntity(String entityname,
			Map<String, String> parms) {
		StringBuilder append = new StringBuilder();
		if (parms.size() == 0 || parms == null)// 查询为空，返回原语句
			return getHQLList(entityname);
		Set<String> setkey = parms.keySet();
		for (Object key : setkey) {
			String value = parms.get(key);
			append.append(key).append("='").append(value).append("' and ");
		}
		return (TBFROM + entityname + TBWHERE + append.toString().substring(0,
				append.toString().lastIndexOf("and"))).trim();
	}

	/**
	 * hql:from entityname where zzz = '%^%' and xxx = '' and yyy = ''
	 * 
	 * @param entityname
	 * @param value
	 * @return 返回基本模糊语句
	 */
	public String getHqlEntity(String entityname, Map<String, String> parms,
			Map<String, String> miss) {
		StringBuilder append = new StringBuilder();
		if (parms.size() > 0 || parms != null) {
			Set<String> setkey = parms.keySet();
			for (Object key : setkey) {
				String value = parms.get(key);
				append.append(key).append("='").append(value).append("' and ");
			}
		}
		if (miss.size() == 0 || miss == null)// 查询为空，返回原语句
			return getHQLList(entityname);
		else {
			Set<String> setkey = miss.keySet();
			for (Object key : setkey) {
				String value = miss.get(key);
				append.append(key).append("='%").append(value)
						.append("%' and ");
			}
		}
		return (TBFROM + entityname + TBWHERE + append.toString().substring(0,
				append.toString().lastIndexOf("and"))).trim();
	}

}
