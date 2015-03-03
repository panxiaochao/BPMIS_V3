package bpmis.pxc.system.manager;

import bpmis.pxc.system.pojo.TUser;

/**
 * 在线用户对象
 * 
 * @author panxiaochao
 * @date 2014-7-10
 * @version 1.0
 */
public class Client implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private TUser user;

	// private Map<String, TSFunction> functions;

	private java.lang.String ip;

	private java.util.Date logindatetime;

	public TUser getUser() {
		return user;
	}

	public void setUser(TUser user) {
		this.user = user;
	}

	// public Map<String, TSFunction> getFunctions() {
	// return functions;
	// }
	//
	// public void setFunctions(Map<String, TSFunction> functions) {
	// this.functions = functions;
	// }

	public java.lang.String getIp() {
		return ip;
	}

	public void setIp(java.lang.String ip) {
		this.ip = ip;
	}

	public java.util.Date getLogindatetime() {
		return logindatetime;
	}

	public void setLogindatetime(java.util.Date logindatetime) {
		this.logindatetime = logindatetime;
	}

}
