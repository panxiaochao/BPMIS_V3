package bpmis.pxc.system.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.pxcbpmisframework.core.util.PasswordUtil;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bpmis.pxc.system.dao.SystemDao;
import bpmis.pxc.system.pojo.TUser;

/**
 * 
 * @ClassName: ExtendDaoImpl
 * @Description: TODO(扩展方法写于此)
 * @author Mr_Pxc
 * @date 2014-1-13 上午10:24:06
 * @project_name：BPMIS_V2
 */
@Repository("systemDao")
@Transactional
public class ExtendDaoImpl extends SystemDaoImpl implements SystemDao {

	/**
	 * 检查用户
	 */
	public TUser checkUserExits(String username, String pwd) {
		// TODO Auto-generated method stub
		pwd = PasswordUtil.PasswordMd5(pwd);
		String query = "from TUser u where u.account = :username and u.pwd = :passowrd";
		Query queryObject = getSession().createQuery(query);
		queryObject.setParameter("username", username);
		queryObject.setParameter("passowrd", pwd);
		List<TUser> users = queryObject.list();
		if (users != null && users.size() > 0) {
			return users.get(0);
		}
		return null;
	}

}
