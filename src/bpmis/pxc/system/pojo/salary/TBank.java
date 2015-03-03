package bpmis.pxc.system.pojo.salary;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.pxcbpmisframework.core.common.entity.IdEntity;
/**
 * 
* @Title: TBank.java 
* @Package bpmis.pxc.system.pojo.salary 
* @Description: TODO(银行类别) 
* @author panxiaochao 
* @date 2014-8-14 下午02:12:25 
* @version V1.0
 */
@Entity
@Table(name = "s_bank")
public class TBank extends IdEntity implements java.io.Serializable {
	private String bankname;
	private String banktype;
	private String bankvalue;

	@Column(name = "s_bankname", length = 50)
	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	@Column(name = "s_banktype", length = 50)
	public String getBanktype() {
		return banktype;
	}

	public void setBanktype(String banktype) {
		this.banktype = banktype;
	}
	@Column(name = "s_bankvalue", length = 50)
	public String getBankvalue() {
		return bankvalue;
	}

	public void setBankvalue(String bankvalue) {
		this.bankvalue = bankvalue;
	}

}
