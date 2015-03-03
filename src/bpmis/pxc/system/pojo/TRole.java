package bpmis.pxc.system.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.pxcbpmisframework.core.common.entity.IdEntity;

/**
 * @Title: TRole.java
 * @Package bpmis.pxc.system.pojo
 * @Description: TODO(角色類)
 * @author panxiaochao
 * @date 2014-7-22 下午12:54:03
 * @version V1.0
 */
@Entity
@Table(name = "t_role")
public class TRole extends IdEntity implements java.io.Serializable {
	private String roleName;// 角色名称
	private String roleCode;// 角色编码
	private String remark;// 

	@Column(name = "rolename", nullable = false, length = 100)
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "rolecode", length = 20)
	public String getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	@Column(name = "remark", length = 100)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
