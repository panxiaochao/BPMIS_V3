package bpmis.pxc.system.controller.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.pxcbpmisframework.core.constant.Globals;
import org.pxcbpmisframework.core.json.AjaxJson;
import org.pxcbpmisframework.core.util.ContextHolderUtils;
import org.pxcbpmisframework.core.util.JSONHelper;
import org.pxcbpmisframework.core.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import bpmis.pxc.system.pojo.TFunction;
import bpmis.pxc.system.pojo.TRole;
import bpmis.pxc.system.service.SystemService;

@Controller
@RequestMapping("/function")
public class FunctionController implements BaseController {
	private static final Logger logger = Logger
			.getLogger(FunctionController.class);
	@Resource
	private SystemService systemService;

	/**
	 * 得到list
	 */
	@RequestMapping(value = "/funlist.do")
	@ResponseBody
	public Map<?, ?> getList(HttpServletRequest request) {
		// TODO Auto-generated method stub
		List<TFunction> list = systemService.findByQueryHql("from TFunction"); // 得到一级菜单
		if (list.size() < 1) // 初始化
			systemService.save(ContextHolderUtils.InitFun());
		List<TFunction> temp = new ArrayList<TFunction>();
		for (int i = 0; i < list.size(); i++) {
			TFunction fun = (TFunction) list.get(i);
			if (!fun.getNodename().equals("root"))
				fun.set_parentId(fun.getParentid());
			temp.add(fun);
		}
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("rows", temp);
		map.put("total", temp.size());
		// System.out.println(JSONHelper.map2json(map));
		return map;
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "save.do", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson SavePojo(HttpServletRequest req, TFunction obj) {
		// TODO Auto-generated method stub
		AjaxJson ajx = new AjaxJson();
		String parentid = req.getParameter("parentid");
		TFunction rootfun = (TFunction) systemService.getClassById(
				TFunction.class, parentid);
		if ("root".equals(rootfun.getNodeid())) {
			obj.setIsnode("1");
		} else {
			obj.setIsnode("0");
		}
		int nodelevel = Integer.valueOf(rootfun.getNodelevel());
		nodelevel++;
		obj.setNodelevel(String.valueOf(nodelevel));
		obj.setParentid(parentid);
		systemService.save(obj);
		ajx.setMsg("添加菜单成功！");
		return ajx;
	}

	/**
	 * 得到上级菜单
	 */
	@RequestMapping(value = "getParent.do", method = RequestMethod.GET)
	@ResponseBody
	public List<TFunction> ParentFun(HttpServletRequest req) {
		// TODO Auto-generated method stub
		List<TFunction> root = systemService
				.findByQueryHql("from TFunction where nodeid = 'root'"); // 得到一级菜单
		TFunction rootfun = (TFunction) root.get(0);
		List<TFunction> list = systemService
				.findByQueryHql("from TFunction where parentid = '"
						+ rootfun.getId() + "' or id = '" + rootfun.getId()
						+ "'"); // 得到一级菜单
		return list;
	}

	/**
	 * Pojo信息
	 */
	@RequestMapping(value = "/getPojo.do")
	public ModelAndView getSinPojo(HttpServletRequest request, ModelMap map) {
		String id = request.getParameter("id");// 当前页
		TFunction obj = (TFunction) systemService.getClassById(TFunction.class,
				id);
		map.put("pojo", obj);
		return new ModelAndView("system/function/funedit", map);
	}

	/**
	 * update
	 */
	@RequestMapping(value = "update.do", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson updatePojo(HttpServletRequest req, TFunction obj) {
		// TODO Auto-generated method stub
		AjaxJson ajx = new AjaxJson();
		try {
			String parentid = req.getParameter("parentid");
			List<TFunction> root = systemService
					.findByQueryHql("from TFunction where nodeid = 'root'"); // 得到一级菜单
			TFunction rootfun = (TFunction) root.get(0);
			if (parentid.equals(rootfun.getId())) {
				obj.setIsnode("1");
				obj.setNodelevel("2");
				obj.setParentid(rootfun.getId());
			} else {
				obj.setIsnode("0");
				obj.setNodelevel("3");
			}
			systemService.update(obj);
			ajx.setMsg("修改菜单成功！");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			systemService.addLogger("菜单update失败！", Globals.Logger_Leavel_ERROR,
					Globals.Logger_Type_DELETE);
			ajx.setMsg("菜单update失败！");
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
			String id = req.getParameter("id");
			// 先闪关系表，再删本体
			delRoleFunction(id);
			String hql = "delete TFunction where (id = '" + id
					+ "' or parentid = '" + id + "')";
			int success = systemService.executeByHql(hql);
			if (success >= 0)
				ajx.setMsg("删除成功！");
			else {
				ajx.setMsg("删除失败！");
				ajx.setSuccess(false);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e);
			systemService.addLogger("菜单删除失败！", Globals.Logger_Leavel_ERROR,
					Globals.Logger_Type_DELETE);
			ajx.setMsg("删除失败！");
			ajx.setSuccess(false);
		}
		return ajx;
	}

	public void delRoleFunction(String funid) {
		// 同步删除角色菜单关联表
		String hql = "delete from TRoleFun where fun.id = '" + funid + "'";
		int success = systemService.executeByHql(hql);
		System.err.println("删除了：" + success);
	}

	public String redirect(String jsp) {
		// TODO Auto-generated method stub
		return null;
	}
}
