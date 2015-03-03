package bpmis.pxc.system.pojo.salary;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.pxcbpmisframework.core.common.entity.IdEntity;

/**
 * 
 * @Title: TSort.java
 * @Package bpmis.pxc.system.pojo.salary
 * @Description: TODO(交易类别)
 * @author panxiaochao
 * @date 2014-8-14 上午09:40:03
 * @version V1.0
 */
@Entity
@Table(name = "s_sort")
public class TSort extends IdEntity implements java.io.Serializable {
	private String sortname;
	private String sorttype;

	@Column(name = "s_sortname", length = 50)
	public String getSortname() {
		return sortname;
	}

	public void setSortname(String sortname) {
		this.sortname = sortname;
	}

	@Column(name = "s_sorttype", length = 4)
	public String getSorttype() {
		return sorttype;
	}

	public void setSorttype(String sorttype) {
		this.sorttype = sorttype;
	}

}
