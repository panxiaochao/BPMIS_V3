package org.pxcbpmisframework.core.json;

/**
 * 专门构造ZTree树的类
 * 
 * @author panxiaochao
 * @ClassName ZTreeEntity
 * @Description TODO
 * @date 2014-7-30
 */
public class ZTreeJson {
	private String id = "";
	private String pId = "";
	private String name = "";
	private boolean open = false; // 展开
	private boolean isParent = false;// 是否目录
	private boolean nocheck = false;// 是否是checkbox
	private boolean checked = false;// 选中状态
	private boolean chkDisabled = false;// 禁选状态
	private String icon = "";// 自定义图标

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isNocheck() {
		return nocheck;
	}

	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isChkDisabled() {
		return chkDisabled;
	}

	public void setChkDisabled(boolean chkDisabled) {
		this.chkDisabled = chkDisabled;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isParent() {
		return isParent;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}

}
