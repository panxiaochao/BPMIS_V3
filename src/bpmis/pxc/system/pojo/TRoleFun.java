package bpmis.pxc.system.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.pxcbpmisframework.core.common.entity.IdEntity;

/**
 * 
 * @Title: TRoleFun.java
 * @Package bpmis.pxc.system.pojo
 * @Description: TODO(角色权限表)
 * @author panxiaochao
 * @date 2014-7-30 上午11:02:42
 * @version V1.0
 */
@Entity
@Table(name = "t_role_fun")
public class TRoleFun extends IdEntity implements java.io.Serializable {
	private TFunction fun;
	private TRole role;
	private String operation;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "functionid")
	public TFunction getFun() {
		return fun;
	}

	public void setFun(TFunction fun) {
		this.fun = fun;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roleid")
	public TRole getRole() {
		return role;
	}

	public void setRole(TRole role) {
		this.role = role;
	}

	@Column(name = "operation", length = 100)
	public String getOperation() {
		return this.operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

}