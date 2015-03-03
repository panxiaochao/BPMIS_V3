package bpmis.pxc.system.controller.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.log4j.Logger;
import org.pxcbpmisframework.core.constant.Globals;
import org.pxcbpmisframework.core.json.AjaxJson;
import org.pxcbpmisframework.core.util.ContextHolderUtils;
import org.pxcbpmisframework.core.util.DataToolsUtils;
import org.pxcbpmisframework.core.util.IpUtils;
import org.pxcbpmisframework.core.util.JSONHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import bpmis.pxc.system.manager.Client;
import bpmis.pxc.system.manager.ClientManager;
import bpmis.pxc.system.pojo.TFunction;
import bpmis.pxc.system.pojo.TUser;
import bpmis.pxc.system.service.SystemService;

@Controller
@RequestMapping("/login")
public class LoginController {
	private static final Logger logger = Logger
			.getLogger(LoginController.class);
	@Resource
	private SystemService systemService;
	private String message = null;

	/**
	 * 首先检查用户名是否正确
	 * 
	 * @param username
	 * @param pwd
	 * @return
	 */
	@RequestMapping(value = "checkuserlogin.do")
	@ResponseBody
	public AjaxJson checkuserlogin(String username, String pwd,
			String checkcode, HttpServletRequest req) {
		HttpSession session = ContextHolderUtils.getSession();
		AjaxJson ajx = new AjaxJson();
		// String safecode =
		// ContextHolderUtils.getSession().getAttribute("code").toString();
		try {
			// if
			// (safecode.toLowerCase().equals(checkcode.toLowerCase().trim())) {
			int users = systemService.loadAll(TUser.class);
			if (users == 0) {
				TUser user = ContextHolderUtils.InitUser();
				systemService.save(user);
				ajx.setMsg("初始化成功，账号：admin/123456 ！");
				ajx.setSuccess(false);
			} else {
				TUser u = systemService.checkUserExits(username.trim(), pwd);
				if (u != null) {
					message = "用户: " + u.getAccount() + "登录成功";
					ContextHolderUtils.getSession().setAttribute(
							Globals.USER_SESSION, "");
					// 最后登陆时间
					if (null != u.getLogintime())
						u.setLasttime(u.getLogintime());
					else
						u.setLasttime(DataToolsUtils.getSimpleDateFormat());
					u.setLogintime(DataToolsUtils.getSimpleDateFormat());
					systemService.update(u);
					//				
					ajx.setMsg("登录成功！");
					Client client = new Client();
					client.setIp(IpUtils.getIpAddr(req));
					client.setLogindatetime(new Date());
					client.setUser(u);
					ClientManager.getInstance().addClinet(session.getId(),
							client);
					// 添加登陆日志
					systemService.addLogger(message,
							Globals.Logger_Leavel_INFO,
							Globals.Logger_Type_LOGIN);
				} else {
					ajx.setMsg("用户名或密码错误！");
					ajx.setSuccess(false);
				}

			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e);
			systemService.addLogger(e.getMessage(),
					Globals.Logger_Leavel_ERROR, Globals.Logger_Type_LOGIN);
			ajx.setMsg("登录验证发生错误！");
			ajx.setSuccess(false);
		}
		return ajx;
	}

	/**
	 * 登录
	 */
	@RequestMapping(value = "/login.do")
	public ModelAndView login(ModelMap map) {
		return new ModelAndView("main/main");
	}

	/**
	 * 首页
	 */
	@RequestMapping(value = "/index.do")
	public ModelAndView main_right(ModelMap map) {
		TUser user = ContextHolderUtils.getSessionUser();
		map.put("user", user);
		return new ModelAndView("main/right", map);
	}

	/**
	 * 退出系统
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "logout.do")
	public ModelAndView logout(HttpServletRequest request) {
		HttpSession session = ContextHolderUtils.getSession();
		TUser user = ContextHolderUtils.getSessionUser();

		systemService.addLogger("用户：" + user.getAccount() + "已退出",
				Globals.Logger_Leavel_INFO, Globals.Logger_Type_EXIT);
		ClientManager.getInstance().removeClinet(session.getId());
		ModelAndView modelAndView = new ModelAndView("login");
		return modelAndView;
	}
	
	/**
	 * 退出系统
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "errorout.do")
	public ModelAndView error_logout(HttpServletRequest request) {
		HttpSession session = ContextHolderUtils.getSession();
		systemService.addLogger("超时退出或者错误退出",
				Globals.Logger_Leavel_INFO, Globals.Logger_Type_EXIT);
		ClientManager.getInstance().removeClinet(session.getId());
		ModelAndView modelAndView = new ModelAndView("login");
		return modelAndView;
	}

	/**
	 * 登录后根据角色获取菜单列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "loginfun.do", method = RequestMethod.GET)
	@ResponseBody
	public String loginfun() {
		TUser user = ContextHolderUtils.getSessionUser();
		if (user != null) {
			String sql = "SELECT DISTINCT m.functionid "
					+ "FROM t_role_user AS t LEFT OUTER JOIN t_role_fun AS m ON t.roleid = m.roleid "
					+ "where t.userid = '" + user.getId() + "'";

			List<String> listfunid = systemService.findByQuerySql(sql);
			List<TFunction> temp = new ArrayList<TFunction>();
			for (String id : listfunid) {
				TFunction fun = (TFunction) systemService.getClassById(
						TFunction.class, id);
				if (fun.getNodelevel().equals("2"))
					temp.add(fun);
			}
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("list", temp);
			map.put("pojo", user);
			return JSONHelper.handlerTojson(map);
		} else
			return "";
	}

	/**
	 * 切换菜单
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/mainleft.do")
	public ModelAndView main_left(HttpServletRequest req, ModelMap map) {
		TUser user = ContextHolderUtils.getSessionUser();
		String sqlR = "select t.roleid from t_role_user t where userid = '"
				+ user.getId() + "' ";
		List<?> listR = systemService.findByQuerySql(sqlR);
		String result = "";
		if (listR != null) {
			for (int i = 0; i < listR.size(); i++) {
				String temp = listR.get(i).toString();
				result += "'" + temp + "',";
			}
			if (result.length() > 1)
				result = result.substring(0, result.length() - 1);
			else
				result = "''";
		} else
			result = "''";

		String funid = req.getParameter("funid");
		String sqlF = "select distinct t.id, t.nodeurl, t.nodename from t_role_fun m left join t_function t on m.functionid = t.id where m.roleid in ("
				+ result + ") and t.parentid = '" + funid + "' ";
		List<?> list = systemService.findByQuerySql(sqlF);
		TFunction fun = (TFunction) systemService.getClassById(TFunction.class,
				funid);
		map.put("list", list);
		map.put("pojo", fun);
		return new ModelAndView("main/left", map);
	}

	/**
	 * 跳转
	 */
	@RequestMapping(value = "/redirect.do")
	public String redirect(String jsp) {
		return jsp;
	}
}
