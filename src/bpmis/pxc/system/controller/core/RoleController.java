package bpmis.pxc.system.controller.core;

import java.util.ArrayList;
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
import org.pxcbpmisframework.core.json.ZTreeJson;
import org.pxcbpmisframework.core.page.Page;
import org.pxcbpmisframework.core.util.DataToolsUtils;
import org.pxcbpmisframework.core.util.JSONHelper;
import org.pxcbpmisframework.core.util.StringUtil;
import org.pxcbpmisframework.core.util.oConvertUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import bpmis.pxc.system.pojo.TBLogger;
import bpmis.pxc.system.pojo.TFunction;
import bpmis.pxc.system.pojo.TRole;
import bpmis.pxc.system.pojo.TRoleFun;
import bpmis.pxc.system.service.SystemService;

@Controller
@RequestMapping("/role")
public class RoleController implements BaseController {

	private static final Logger logger = Logger.getLogger(RoleController.class);
	@Resource
	private SystemService systemService;

	/**
	 * 得到list
	 */
	@RequestMapping(value = "/rolelist.do")
	@ResponseBody
	public Map<?, ?> getList(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String pageNum = request.getParameter("page");// 当前页
		String rows = request.getParameter("rows");// 每页多少数据
		CriteriaQuery cq = new CriteriaQuery();
		Page page = new Page();
		if (!oConvertUtils.isEmpty(pageNum)) {
			page.setCurrentPage(pageNum);
			page.setPageSize(rows);
		}
		cq.setPage(page);
		Map<?, ?> mapPage = systemService.getPageList(TRole.class, cq);
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("rows", mapPage.get("list"));
		map.put("total", mapPage.get("total"));
		return map;
	}

	/**
	 * Pojo信息
	 */
	@RequestMapping(value = "/getPojo.do")
	public ModelAndView getSinPojo(HttpServletRequest request, ModelMap map) {
		String id = request.getParameter("id");// 当前页
		TRole obj = (TRole) systemService.getClassById(TRole.class, id);
		map.put("pojo", obj);
		return new ModelAndView("system/role/roleedit", map);
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "save.do", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson SavePojo(HttpServletRequest req, TRole obj) {
		// TODO Auto-generated method stub
		AjaxJson ajx = new AjaxJson();
		systemService.save(obj);
		ajx.setMsg("添加角色成功！");
		return ajx;
	}

	/**
	 * update
	 */
	@RequestMapping(value = "update.do", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson updatePojo(HttpServletRequest req, TRole obj) {
		// TODO Auto-generated method stub
		AjaxJson ajx = new AjaxJson();
		try {
			systemService.update(obj);
			ajx.setMsg("修改角色成功！");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			systemService.addLogger("角色update失败！", Globals.Logger_Leavel_ERROR,
					Globals.Logger_Type_DELETE);
			ajx.setMsg("角色update失败！");
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
			// 先删关系表，再删本体
			for (String str : ids.split(";")) {
				delRoleFunction(str); // role-fun
				delUserRole(str); // role-user
			}
			systemService.deleteAll(TRole.class, ids.split(";"), null);
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

	public void delUserRole(String roleid) {
		// 同步删除用户角色关联表
		String hql = "delete from TRoleUser where role.id = '" + roleid + "'";
		int success = systemService.executeByHql(hql);
		System.err.println("删除了：" + success);
	}

	/**
	 * 得到角色设置权限
	 * 
	 * @Title: getRoleAuthority
	 * @param @param request
	 * @param @param map
	 * @throws
	 */
	@RequestMapping(value = "getrolefun.do")
	public ModelAndView getRoleAuthority(HttpServletRequest request,
			ModelMap map) {
		String roleid = request.getParameter("roleid");
		List<?> list = systemService.findByQueryHql("from TFunction");
		//
		String hql = "from TRoleFun where role.id = '" + roleid + "'";
		List<TRoleFun> roleUserList = systemService.findByQueryHql(hql);

		List<ZTreeJson> tree = new ArrayList<ZTreeJson>();
		for (int i = 0; i < list.size(); i++) {
			TFunction fun = (TFunction) list.get(i);
			ZTreeJson ztree = new ZTreeJson();
			// if (function.getNodelevel() == 1)//一级默认展开
			// ztree.setOpen(true);
			if ("root".equals(fun.getNodeid())) {
				ztree.setParent(true);
			}
			if (fun.getNodelevel().equals("1")
					|| fun.getNodelevel().equals("2")) {
				ztree.setOpen(true);
			}
			ztree.setId(fun.getId());
			if ("root".equals(fun.getNodeid()))
				ztree.setpId("0");
			else
				ztree.setpId(fun.getParentid());
			ztree.setName(fun.getNodename());
			if (comparefun(fun, roleUserList))
				ztree.setChecked(true);
			tree.add(ztree);
		}
		map.put("ztree", JSONHelper.toJSONString(tree));
		map.put("roleid", roleid);
		return new ModelAndView("system/role/rolefun", map);
	}

	// 比较
	private boolean comparefun(TFunction function, List<?> roleUserList) {
		// TODO Auto-generated method stub
		if (roleUserList != null && roleUserList.size() > 0) {
			for (int i = 0; i < roleUserList.size(); i++) {
				TRoleFun tbrolefun = (TRoleFun) roleUserList.get(i);
				TFunction fun = tbrolefun.getFun();
				if (fun.getId().equals(function.getId()))
					return true;
			}
		}
		return false;
	}

	/**
	 * 保存角色-菜单
	 */
	@RequestMapping(value = "saverolefun.do", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson SavePojo_RoleFun(HttpServletRequest req) {
		// TODO Auto-generated method stub
		AjaxJson ajx = new AjaxJson();
		String funid = req.getParameter("funid");
		String roleid = req.getParameter("roleid");
		if (roleid.equals("")) {
			ajx.setMsg("寻找不到角色ID，请刷新页面！");
			ajx.setSuccess(false);
			return ajx;
		}
		TRole role = (TRole) systemService.getClassById(TRole.class, roleid);
		// 先删除关联表，再更新
		delRoleFunction(roleid);
		//
		String[] functionlist = StringUtil.splitString(funid, ";");
		if (functionlist != null && functionlist.length > 0) {
			saveRoleFunction(role, functionlist);
		}
		ajx.setMsg("添加菜单成功！");
		return ajx;
	}

	// |-----@ManyToOne<Role_Function>-------|
	protected void saveRoleFunction(TRole role, String[] functionlist) {
		for (int i = 0; i < functionlist.length; i++) {
			TRoleFun rolefunction = new TRoleFun();
			TFunction function = (TFunction) systemService.getClassById(
					TFunction.class, functionlist[i]);
			rolefunction.setRole(role);
			rolefunction.setFun(function);
			systemService.save(rolefunction);
		}
	}

	public void delRoleFunction(String roleid) {
		// 同步删除角色菜单关联表
		String hql = "delete from TRoleFun where role.id = '" + roleid + "'";
		int success = systemService.executeByHql(hql);
		System.err.println("删除了：" + success);

		// Map<Object, Object> map = HqlQuery.getHashMapsObj();
		// map.put("role.id", role.getId());
		// List<?> roleFunList = systemService.ByCrifindQueryForObj(
		// TRoleFun.class, map);
		// if (roleFunList != null && roleFunList.size() > 0) {
		// systemService.deleteAll(roleFunList);
		// }
	}

	public String redirect(String jsp) {
		// TODO Auto-generated method stub
		return null;
	}
}
