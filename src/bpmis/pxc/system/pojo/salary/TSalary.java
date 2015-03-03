package bpmis.pxc.system.pojo.salary;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.pxcbpmisframework.core.common.entity.IdEntity;

/**
 * 
 * @author Panxiaochao 交易明细表
 */
@Entity
@Table(name = "s_salarytable")
public class TSalary extends IdEntity implements java.io.Serializable {
	private float pay;
	private float store;
	private float balance; // 余额
	private String paysort; // 支付类别
	private String storesort; // 存储类别
	private String summary; // 摘要
	private String bank; // 银行
	private String tradetime; // 交易时间
	private String createtime; // 创建时间
	private String tradeid; // 交易编码
	private String trdetype; // 资金流向; in 收入/ out 支出
	private String userid;
	private String remark;

	@Column(name = "s_pay")
	public float getPay() {
		return pay;
	}

	public void setPay(float pay) {
		this.pay = pay;
	}

	@Column(name = "s_store")
	public float getStore() {
		return store;
	}

	public void setStore(float store) {
		this.store = store;
	}

	@Column(name = "s_balance")
	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	@Column(name = "s_summary", length = 200)
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Column(name = "s_bank", length = 500)
	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	@Column(name = "s_tradetime", length = 50)
	public String getTradetime() {
		return tradetime;
	}

	public void setTradetime(String tradetime) {
		this.tradetime = tradetime;
	}

	@Column(name = "s_createtime", length = 50)
	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@Column(name = "s_paysort", length = 50)
	public String getPaysort() {
		return paysort;
	}

	public void setPaysort(String paysort) {
		this.paysort = paysort;
	}

	@Column(name = "s_storesort", length = 50)
	public String getStoresort() {
		return storesort;
	}

	public void setStoresort(String storesort) {
		this.storesort = storesort;
	}

	@Column(name = "s_remark", length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "s_trdetype", length = 4)
	public String getTrdetype() {
		return trdetype;
	}

	public void setTrdetype(String trdetype) {
		this.trdetype = trdetype;
	}

	@Column(name = "s_tradeid", length = 50)
	public String getTradeid() {
		return tradeid;
	}

	public void setTradeid(String tradeid) {
		this.tradeid = tradeid;
	}
	@Column(name = "s_userid", length = 32)
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

}
