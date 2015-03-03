package bpmis.pxc.system.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.pxcbpmisframework.core.common.entity.IdEntity;

/**
 * 
 * @author Panxiaochao 菜单表
 */
@Entity
@Table(name = "t_function")
public class TFunction extends IdEntity implements java.io.Serializable {
	private String iconsid;
	private String parentid;
	private String nodeid; // 本节点id
	private String nodename;
	private String nodelevel; // 几级
	private String isnode; // 1是目录；0是节点
	private String nodeurl; // 地址
	private String nodeview; // 是否禁用 1是 0否
	private String nodeorder;// 菜单排序
	private String remark;
	private String iconid;
	private String iconname;
	//
	private String _parentId; // treegrid 特有 ,不进入持久化

	@Column(name = "iconsid", length = 32)
	public String getIconsid() {
		return iconsid;
	}

	public void setIconsid(String iconsid) {
		this.iconsid = iconsid;
	}

	@Column(name = "parentid", length = 32)
	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	@Column(name = "nodeid", length = 20)
	public String getNodeid() {
		return nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}

	@Column(name = "nodename", length = 20)
	public String getNodename() {
		return nodename;
	}

	public void setNodename(String nodename) {
		this.nodename = nodename;
	}

	@Column(name = "nodelevel", length = 4)
	public String getNodelevel() {
		return nodelevel;
	}

	public void setNodelevel(String nodelevel) {
		this.nodelevel = nodelevel;
	}

	@Column(name = "isnode", length = 4)
	public String getIsnode() {
		return isnode;
	}

	public void setIsnode(String isnode) {
		this.isnode = isnode;
	}

	@Column(name = "nodeurl", length = 100)
	public String getNodeurl() {
		return nodeurl;
	}

	public void setNodeurl(String nodeurl) {
		this.nodeurl = nodeurl;
	}

	@Column(name = "nodeview", length = 4)
	public String getNodeview() {
		return nodeview;
	}

	public void setNodeview(String nodeview) {
		this.nodeview = nodeview;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "nodeorder", length = 20)
	public String getNodeorder() {
		return nodeorder;
	}

	public void setNodeorder(String nodeorder) {
		this.nodeorder = nodeorder;
	}

	@Transient
	public String get_parentId() {
		return _parentId;
	}

	public void set_parentId(String parentId) {
		_parentId = parentId;
	}

	@Column(name = "iconid", length = 32)
	public String getIconid() {
		return iconid;
	}

	public void setIconid(String iconid) {
		this.iconid = iconid;
	}

	@Column(name = "iconname", length = 50)
	public String getIconname() {
		return iconname;
	}

	public void setIconname(String iconname) {
		this.iconname = iconname;
	}

}
