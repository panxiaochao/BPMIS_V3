package bpmis.pxc.system.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.pxcbpmisframework.core.common.entity.IdEntity;

/**
 * 日志类
 * 
 * @ClassName: TBLogger
 * @author Mr_Pxc
 * @date 2013-7-12 下午02:43:09
 * @project_name：BPMIS_1
 * @version 1.0
 */
@Entity
@Table(name = "t_user")
@PrimaryKeyJoinColumn(name = "ID")
public class TUser extends IdEntity implements java.io.Serializable {
	private String bpmisid; // 身份编号
	private String account;
	private String username;
	private String pwd;
	private String sex; // 0男1女
	private String email;
	private String tel;
	private String qq;
	private String regtime;
	private String remark;
	private String logintime;
	private String lasttime;
	//
	private String roleid;
	private String rolename;

	@Column(name = "bpmisid", length = 50)
	public String getBpmisid() {
		return bpmisid;
	}

	public void setBpmisid(String bpmisid) {
		this.bpmisid = bpmisid;
	}

	@Column(name = "account", length = 50)
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Column(name = "username", length = 50)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "pwd", length = 32)
	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Column(name = "sex", length = 5)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "email", length = 50)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "tel", length = 20)
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "qq", length = 10)
	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	@Column(name = "regtime", length = 50)
	public String getRegtime() {
		return regtime;
	}

	public void setRegtime(String regtime) {
		this.regtime = regtime;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Transient
	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	@Transient
	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	@Column(name = "lasttime", length = 50)
	public String getLasttime() {
		return lasttime;
	}

	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}
	@Column(name = "logintime", length = 50)
	public String getLogintime() {
		return logintime;
	}

	public void setLogintime(String logintime) {
		this.logintime = logintime;
	}

}
