package org.pxcbpmisframework.core.constant;

import java.util.TreeMap;

/**
 * 全局变量定义
 * 
 * @author panxiaochao
 * @ClassName Globals
 * @Description TODO
 * @date 2013-7-6
 */
public class Globals {
	/**
	 * 保存用户到SESSION
	 */
	public static String USER_SESSION = "USER_SESSION";
	/**
	 * 人员类型
	 */
	static int User_Forbidden = 0;// 禁用
	static int User_Normal = 1;// 正常
	static int User_Unaudited = 2;// 未审核

	/**
	 *日志级别定义
	 */
	public static String Logger_Leavel_INFO = "INFO";
	public static String Logger_Leavel_WARRING = "WARRING";
	public static String Logger_Leavel_ERROR = "ERROR";
	public static String Logger_Leavel_DEBUG = "DEBUG";

	/**
	 * 日志类型
	 */
	public static String Logger_Type_500 = "500(500内部错误)"; // 500
	public static String Logger_Type_FeedBack = "FeedBack(反馈)"; // 
	public static String Logger_Type_LOGIN = "LOGIN(登录)"; // 登陆
	public static String Logger_Type_EXIT = "EXIT(退出)"; // 退出
	public static String Logger_Type_INSERT = "INSERT(插入)"; // 插入
	public static String Logger_Type_DELETE = "DELETE(删除)"; // 删除
	public static String Logger_Type_UPDATE = "UPDATE(更新)"; // 更新
	public static String Logger_Type_UPLOAD = "UPLOAD(上传)"; // 上传
	public static String Logger_Type_OTHER = "OTHER(其他)"; // 其他

	
	

}
