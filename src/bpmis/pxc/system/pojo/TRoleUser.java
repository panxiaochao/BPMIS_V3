package bpmis.pxc.system.pojo;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.pxcbpmisframework.core.common.entity.IdEntity;

/**
 * 
* @Title: TRoleUser.java 
* @Package bpmis.pxc.system.pojo 
* @Description: TODO(用户-角色类) 
* @author panxiaochao 
* @date 2014-7-22 下午01:24:47 
* @version V1.0
 */
@Entity
@Table(name = "t_role_user")
public class TRoleUser extends IdEntity implements java.io.Serializable {
	private TUser user;
	private TRole role;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userid")
	public TUser getUser() {
		return user;
	}

	public void setUser(TUser user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "roleid")
	public TRole getRole() {
		return role;
	}

	public void setRole(TRole role) {
		this.role = role;
	}

}