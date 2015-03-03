package bpmis.pxc.system.pojo.salary;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.pxcbpmisframework.core.common.entity.IdEntity;

/**
 * 
 * @Title: TBankSal.java
 * @Package bpmis.pxc.system.pojo.salary
 * @Description: TODO(银行和用户的钱的总数归档)
 * @author panxiaochao
 * @date 2014-8-19 上午10:50:26
 * @version V1.0
 */
@Entity
@Table(name = "s_bank_salary")
public class TBankSal extends IdEntity implements java.io.Serializable {

	private String userid;
	private String bankid;
	private float total;

	@Column(name = "s_userid", length = 32)
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Column(name = "s_bankid", length = 32)
	public String getBankid() {
		return bankid;
	}

	public void setBankid(String bankid) {
		this.bankid = bankid;
	}

	// 类似(18,3)
	@Column(name = "s_total", precision = 18, scale = 3)
	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

}
