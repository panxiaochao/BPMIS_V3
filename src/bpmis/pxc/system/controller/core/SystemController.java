package bpmis.pxc.system.controller.core;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.pxcbpmisframework.core.common.qbc.CriteriaQuery;
import org.pxcbpmisframework.core.constant.Globals;
import org.pxcbpmisframework.core.json.AjaxJson;
import org.pxcbpmisframework.core.page.Page;
import org.pxcbpmisframework.core.util.DataToolsUtils;
import org.pxcbpmisframework.core.util.StringUtil;
import org.pxcbpmisframework.core.util.oConvertUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import bpmis.pxc.system.pojo.TBLogger;
import bpmis.pxc.system.service.SystemService;

/**
 * 系统杂类
 * 
 * @author panxiaochao
 * @ClassName SystemController
 * @Description TODO
 * @date 2014-7-15
 */
@Controller
@RequestMapping("/system")
public class SystemController implements BaseController {
	private static final Logger logger = Logger
			.getLogger(SystemController.class);
	@Resource
	private SystemService systemService;

	/**
	 * 得到日志
	 */
	@RequestMapping(value = "/logger.do")
	@ResponseBody
	public Map<?, ?> getList(HttpServletRequest request) {
		String pageNum = request.getParameter("page");// 当前页
		String rows = request.getParameter("rows");// 每页多少数据
		CriteriaQuery cq = new CriteriaQuery(false, "operatetime");
		Page page = new Page();
		if (!oConvertUtils.isEmpty(pageNum)) {
			page.setCurrentPage(pageNum);
			page.setPageSize(rows);
		}
		cq.setPage(page);
		Map<?, ?> mapPage = systemService.getPageList(TBLogger.class, cq);
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("rows", mapPage.get("list"));
		map.put("total", mapPage.get("total"));
		// System.out.println(JSONHelper.map2json(map));
		return map;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "deletelog.do", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson DeletePojo(HttpServletRequest req) {
		// TODO Auto-generated method stub
		AjaxJson ajx = new AjaxJson();
		try {
			String ids = req.getParameter("id");
			systemService.deleteAll(TBLogger.class, StringUtil.splitString(ids,
					";"), null);
			ajx.setMsg("删除成功！");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			systemService.addLogger("日志删除失败！", Globals.Logger_Leavel_ERROR,
					Globals.Logger_Type_DELETE);
			ajx.setMsg("删除失败！");
			ajx.setSuccess(false);
		}
		return ajx;
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "savelog.do", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson SavePojo(HttpServletRequest req, TBLogger obj) {
		// TODO Auto-generated method stub
		AjaxJson ajx = new AjaxJson();
		obj.setOperatetime(DataToolsUtils.getSimpleDateFormat());
		systemService.save(obj);
		ajx.setMsg("添加成功！");
		return ajx;
	}

	public ModelAndView getSinPojo(HttpServletRequest request, ModelMap map) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 跳转
	 */
	@RequestMapping(value = "/redirect.do")
	public String redirect(String jsp) {
		// TODO Auto-generated method stub
		return jsp;
	}
}
