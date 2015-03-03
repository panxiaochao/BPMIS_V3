package bpmis.pxc.system.controller.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.pxcbpmisframework.core.common.qbc.CriteriaQuery;
import org.pxcbpmisframework.core.common.qbc.HqlQuery;
import org.pxcbpmisframework.core.constant.Globals;
import org.pxcbpmisframework.core.json.AjaxJson;
import org.pxcbpmisframework.core.page.Page;
import org.pxcbpmisframework.core.util.BpmisUtils;
import org.pxcbpmisframework.core.util.DataToolsUtils;
import org.pxcbpmisframework.core.util.PasswordUtil;
import org.pxcbpmisframework.core.util.StringUtil;
import org.pxcbpmisframework.core.util.oConvertUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import bpmis.pxc.system.pojo.TRole;
import bpmis.pxc.system.pojo.TRoleUser;
import bpmis.pxc.system.pojo.TUser;
import bpmis.pxc.system.service.SystemService;

@Controller
@RequestMapping("/user")
public class UserController implements BaseController {
	private static final Logger logger = Logger.getLogger(UserController.class);
	@Resource
	private SystemService systemService;

	/**
	 * 得到list
	 */
	@RequestMapping(value = "/userlist.do")
	@ResponseBody
	public Map<?, ?> getList(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String pageNum = request.getParameter("page");// 当前页
		String rows = request.getParameter("rows");// 每页多少数据
		CriteriaQuery cq = new CriteriaQuery();
		// 排序字段
		Map<Object, Object> maporder = HqlQuery.getHashMapsObj();
		maporder.put("asc", "bpmisid");
		maporder.put("desc", "regtime");
		cq.setMaporder(maporder);
		if (!oConvertUtils.isEmpty(pageNum)) {
			cq.setCurrentPage(oConvertUtils.getInt(pageNum));
			cq.setPageSize(oConvertUtils.getInt(rows));
		}
		cq = systemService.getPageList2(TUser.class, cq);
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("rows", cq.getReaults());
		map.put("total", cq.getTotalRecords());
		return map;
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "save.do", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson SavePojo(HttpServletRequest req, TUser obj) {
		// TODO Auto-generated method stub
		AjaxJson ajx = new AjaxJson();
		obj.setBpmisid(BpmisUtils.getBpmisCode());
		obj.setPwd(PasswordUtil.PasswordMd5("123456"));
		obj.setRegtime(DataToolsUtils.getSimpleDateFormat());
		systemService.save(obj);
		// 设置用户状态
		// 设置默认角色
		// 设置默认权限
		ajx.setMsg("添加用户成功，初始密码为：123456！");
		return ajx;
	}

	// |-----@ManyToOne<User_Role>-------|
	protected void saveRoleUser(TUser user, String[] rolelist) {
		for (int i = 0; i < rolelist.length; i++) {
			TRoleUser userrole = new TRoleUser();
			TRole role = (TRole) systemService.getClassById(TRole.class,
					rolelist[i]);
			userrole.setUser(user);
			userrole.setRole(role);
			systemService.saveOrUpdata(userrole);
		}
	}

	/**
	 * Pojo信息
	 */
	@RequestMapping(value = "/getPojo.do")
	public ModelAndView getSinPojo(HttpServletRequest request, ModelMap map) {
		String id = request.getParameter("id");//
		TUser obj = (TUser) systemService.getClassById(TUser.class, id);
		map.put("pojo", obj);
		return new ModelAndView("system/user/useredit", map);
	}

	/**
	 * Pojo信息
	 */
	@RequestMapping(value = "/getPojoRole.do")
	public ModelAndView getSinPojo_Role(HttpServletRequest request, ModelMap map) {
		String id = request.getParameter("id");// 当前页
		// 角色id
		String sql = "select t.roleid from t_role_user t where t.userid = '"
				+ id + "'";
		List<String> rolelist = systemService.findByQuerySql(sql);
		String roleid = "";
		for (int i = 0; i < rolelist.size(); i++) {
			if (i == rolelist.size() - 1)
				roleid += "'" + rolelist.get(i) + "'";
			else
				roleid += "'" + rolelist.get(i) + "',";
		}
		map.put("roleid", roleid);
		map.put("id", id);
		return new ModelAndView("system/user/userrole", map);
	}

	/**
	 * update
	 */
	@RequestMapping(value = "update.do", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson updatePojo(HttpServletRequest req, TUser obj) {
		// TODO Auto-generated method stub
		AjaxJson ajx = new AjaxJson();
		try {
			systemService.update(obj);
			ajx.setMsg("修改用戶成功！");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			systemService.addLogger("用戶update失败！", Globals.Logger_Leavel_ERROR,
					Globals.Logger_Type_DELETE);
			ajx.setMsg("用戶update失败！");
			ajx.setSuccess(false);
		}
		return ajx;
	}

	/**
	 * update
	 */
	@RequestMapping(value = "updaterole.do", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson updatePojo_Role(HttpServletRequest req) {
		// TODO Auto-generated method stub
		AjaxJson ajx = new AjaxJson();
		try {
			String id = req.getParameter("id");//
			TUser obj = (TUser) systemService.getClassById(TUser.class, id);
			// 更新角色信息，先删再存
			String hql = "delete TRoleUser where userid = '" + obj.getId()
					+ "'";
			int success = systemService.executeByHql(hql);
			if (success >= 0) {
				String[] rolelist = req.getParameterValues("rolegroup"); // 用户角色
				if (rolelist != null && rolelist.length > 0) {
					saveRoleUser(obj, rolelist);
				}
				ajx.setMsg("添加或修改角色成功！");
			} else {
				ajx.setMsg("添加或修改角色失败！");
				ajx.setSuccess(false);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			systemService.addLogger("添加或修改角色update失败！",
					Globals.Logger_Leavel_ERROR, Globals.Logger_Type_DELETE);
			ajx.setMsg("添加或修改角色update失败！");
			ajx.setSuccess(false);
		}
		return ajx;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "delete.do", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson DeletePojo(HttpServletRequest req) {
		// TODO Auto-generated method stub
		AjaxJson ajx = new AjaxJson();
		try {
			String ids = req.getParameter("id");
			// 先删关系表，再删用户表
			for (String str : ids.split(";")) {
				delUserRole(str);
			}
			systemService.deleteAll(TUser.class, StringUtil.splitString(ids,
					";"), null);
			ajx.setMsg("删除成功！");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			systemService.addLogger("角色删除失败！", Globals.Logger_Leavel_ERROR,
					Globals.Logger_Type_DELETE);
			ajx.setMsg("删除失败！");
			ajx.setSuccess(false);
		}
		return ajx;
	}

	public void delUserRole(String id) {
		// 同步删除用户角色关联表
		Map<Object, Object> map = HqlQuery.getHashMapsObj();
		map.put("user.id", id);
		List<?> roleUserList = systemService.ByCrifindQueryForObj(
				TRoleUser.class, map);
		if (roleUserList != null && roleUserList.size() >= 0) {
			systemService.deleteAll(roleUserList);
		}
	}

	/**
	 * 添加Role
	 */
	@RequestMapping(value = "getrole.do", method = RequestMethod.POST)
	@ResponseBody
	public List<TRole> getRole() {
		// TODO Auto-generated method stub
		return systemService.ByCrifindQuery(TRole.class);
	}

	public String redirect(String jsp) {
		// TODO Auto-generated method stub
		return null;
	}
}
