package bpmis.pxc.system.controller.core.salary;

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
import org.pxcbpmisframework.core.util.ContextHolderUtils;
import org.pxcbpmisframework.core.util.DataToolsUtils;
import org.pxcbpmisframework.core.util.JSONHelper;
import org.pxcbpmisframework.core.util.oConvertUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import bpmis.pxc.system.controller.core.BaseController;
import bpmis.pxc.system.pojo.TRole;
import bpmis.pxc.system.pojo.TUser;
import bpmis.pxc.system.pojo.salary.TBank;
import bpmis.pxc.system.pojo.salary.TBankSal;
import bpmis.pxc.system.pojo.salary.TSalary;
import bpmis.pxc.system.pojo.salary.TSort;
import bpmis.pxc.system.service.SystemService;

@Controller
@RequestMapping("/salary")
public class SalaryController implements BaseController {
	private static final Logger logger = Logger
			.getLogger(SalaryController.class);
	@Resource
	private SystemService systemService;
	private final static Map<String, String> banktype = new HashMap<String, String>();
	static {
		banktype.put("PSBC", "中国邮政储蓄银行");
		banktype.put("CMB", "招商银行");
		banktype.put("HZSMK", "杭州市民卡");
		banktype.put("FLK", "福利卡");
		banktype.put("BOC", "中国银行");
		banktype.put("ICBC", "中国工商银行");
		banktype.put("CCB", "中国建设银行");
		banktype.put("ABC", "中国农业银行");
		banktype.put("ZFB", "支付宝");
		banktype.put("OTHER", "其他");
	}

	/**
	 * 得到list
	 */
	@RequestMapping(value = "/salarylist.do")
	@ResponseBody
	public Map<?, ?> getList(HttpServletRequest request) {
		// TODO Auto-generated method stub
		TUser user = ContextHolderUtils.getSessionUser();
		String pageNum = request.getParameter("page");// 当前页
		String rows = request.getParameter("rows");// 每页多少数据
		// 查询字段
		String selectText = request.getParameter("selectText");
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		//
		CriteriaQuery cq = new CriteriaQuery();
		// 排序字段
		Map<Object, Object> maporder = HqlQuery.getHashMapsObj();
		maporder.put("desc", "createtime");
		// 查询字段
		Map<Object, String[]> mapparms = HqlQuery.getHashMapsObjs();
		mapparms.put("userid", new String[] { HqlQuery.Restrictions_eq,
				user.getId() });
		if (!"all".equals(selectText) && null != selectText)
			mapparms.put("bank", new String[] { HqlQuery.Restrictions_like,
					"%" + selectText + "%" });
		if ((!"".equals(starttime) && null != starttime)
				&& (!"".equals(endtime) && null != endtime))
			mapparms.put("tradetime", new String[] {
					HqlQuery.Restrictions_between, starttime, endtime });
		cq.setMapparms(mapparms);
		//		
		cq.setMaporder(maporder);
		if (!oConvertUtils.isEmpty(pageNum)) {
			cq.setCurrentPage(oConvertUtils.getInt(pageNum));
			cq.setPageSize(oConvertUtils.getInt(rows));
		}
		cq = systemService.getPageList2(TSalary.class, cq);
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
	public AjaxJson SavePojo(HttpServletRequest req, TSalary obj) {
		// TODO Auto-generated method stub
		AjaxJson ajx = new AjaxJson();
		TUser user = ContextHolderUtils.getSessionUser();
		float money = Float.valueOf(req.getParameter("money"));
		String trdetype = req.getParameter("trdetype");
		String sorttype = req.getParameter("sorttype");
		String banktype = req.getParameter("banktype");

		if ("in".equals(trdetype)) {
			obj.setStore(money);
			obj.setStoresort(sorttype);
			obj.setPaysort("-");
		} else if ("out".equals(trdetype)) {
			obj.setPay(money);
			obj.setPaysort(sorttype);
			obj.setStoresort("-");
		}
		float total = setTotalSal(user, trdetype, banktype.split(";")[0], money);// 总额
		obj.setBalance(total);
		obj.setSummary("【" + sorttype.substring(0, sorttype.indexOf('[')) + "】"
				+ obj.getSummary());
		obj.setBank(banktype);
		obj.setTradeid("BPMIS_"
				+ DataToolsUtils.SimpleFormatTime("yyyyMMddHHmmss")); // 交易时间暂时不能落户
		obj.setCreatetime(DataToolsUtils.getSimpleDateFormat());
		obj.setUserid(user.getId());
		systemService.save(obj);
		ajx.setMsg("添加账单成功！");
		return ajx;
	}

	public float setTotalSal(TUser user, String type, String bankid, float money) {
		String hql = "from TBankSal where userid = '" + user.getId()
				+ "' and bankid = '" + bankid + "'";
		List<?> list = systemService.findByQueryHql(hql);
		float total = money;
		if (list != null && list.size() > 0) {
			TBankSal banksal = (TBankSal) list.get(0);
			total = banksal.getTotal();
			if ("in".equals(type))
				total += money;
			else if ("out".equals(type))
				total -= money;
			if (total <= 0) { // 没有就为0
				total = 0;
				banksal.setTotal(total);
			} else
				banksal.setTotal(total);
			systemService.update(banksal);
		}
		return total;
	}

	public ModelAndView getSinPojo(HttpServletRequest request, ModelMap map) {
		// TODO Auto-generated method stub
		return null;
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
			systemService.deleteAll(TSalary.class, ids.split(";"), null);
			ajx.setMsg("删除成功！");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			systemService.addLogger("账单删除失败！", Globals.Logger_Leavel_ERROR,
					Globals.Logger_Type_DELETE);
			ajx.setMsg("删除失败！");
			ajx.setSuccess(false);
		}
		return ajx;
	}

	// --------------------- 以下是类别方法 ---------------------
	/**
	 * 得到list
	 */
	@RequestMapping(value = "/salsort.do")
	@ResponseBody
	public Map<?, ?> getList_sort(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String pageNum = request.getParameter("page");// 当前页
		String rows = request.getParameter("rows");// 每页多少数据
		CriteriaQuery cq = new CriteriaQuery();
		// 排序字段
		Map<Object, Object> maporder = HqlQuery.getHashMapsObj();
		// maporder.put("desc", "createtime");
		cq.setMaporder(maporder);
		if (!oConvertUtils.isEmpty(pageNum)) {
			cq.setCurrentPage(oConvertUtils.getInt(pageNum));
			cq.setPageSize(oConvertUtils.getInt(rows));
		}
		cq = systemService.getPageList2(TSort.class, cq);
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("rows", cq.getReaults());
		map.put("total", cq.getTotalRecords());
		return map;
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "savesort.do", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson SavePojo_Sort(HttpServletRequest req, TSort obj) {
		// TODO Auto-generated method stub
		AjaxJson ajx = new AjaxJson();
		systemService.save(obj);
		ajx.setMsg("添加账单类别成功！");
		return ajx;
	}

	/**
	 * Pojo信息
	 */
	@RequestMapping(value = "/getPojo_Sort.do")
	public ModelAndView getSinPojo_Sort(HttpServletRequest request, ModelMap map) {
		String id = request.getParameter("id");// 当前页
		TSort obj = (TSort) systemService.getClassById(TSort.class, id);
		map.put("pojo", obj);
		return new ModelAndView("commen/salary/sortedit", map);
	}

	/**
	 * update
	 */
	@RequestMapping(value = "updatesort.do", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson updatePojo_sort(HttpServletRequest req, TSort obj) {
		// TODO Auto-generated method stub
		AjaxJson ajx = new AjaxJson();
		try {
			systemService.update(obj);
			ajx.setMsg("修改类别成功！");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			systemService.addLogger("类别update失败！", Globals.Logger_Leavel_ERROR,
					Globals.Logger_Type_DELETE);
			ajx.setMsg("类别update失败！");
			ajx.setSuccess(false);
		}
		return ajx;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "delsort.do", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson DeletePojo_Sort(HttpServletRequest req) {
		// TODO Auto-generated method stub
		AjaxJson ajx = new AjaxJson();
		try {
			String ids = req.getParameter("id");
			systemService.deleteAll(TSort.class, ids.split(";"), null);
			ajx.setMsg("删除成功！");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			systemService.addLogger("账单类别删除失败！", Globals.Logger_Leavel_ERROR,
					Globals.Logger_Type_DELETE);
			ajx.setMsg("删除失败！");
			ajx.setSuccess(false);
		}
		return ajx;
	}

	/**
	 * 得到类别
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "getSort.do")
	@ResponseBody
	public String getSort(HttpServletRequest req) {
		String sort = req.getParameter("val");
		String hql = "from TSort where sorttype = '" + sort + "'";
		List<?> list = systemService.findByQueryHql(hql);
		return JSONHelper.collectionTojson(list);
	}

	// --------------------- 以下是銀行类别方法 ---------------------

	/**
	 * 得到list
	 */
	@RequestMapping(value = "/salbank.do")
	@ResponseBody
	public Map<?, ?> getList_bank(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String pageNum = request.getParameter("page");// 当前页
		String rows = request.getParameter("rows");// 每页多少数据
		CriteriaQuery cq = new CriteriaQuery();
		// 排序字段
		Map<Object, Object> maporder = HqlQuery.getHashMapsObj();
		// maporder.put("desc", "createtime");
		cq.setMaporder(maporder);
		if (!oConvertUtils.isEmpty(pageNum)) {
			cq.setCurrentPage(oConvertUtils.getInt(pageNum));
			cq.setPageSize(oConvertUtils.getInt(rows));
		}
		cq = systemService.getPageList2(TBank.class, cq);
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("rows", cq.getReaults());
		map.put("total", cq.getTotalRecords());
		return map;
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "savebank.do", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson SavePojo_Bank(HttpServletRequest req, TBank obj) {
		// TODO Auto-generated method stub
		AjaxJson ajx = new AjaxJson();
		obj.setBankvalue(banktype.get(obj.getBanktype()));
		systemService.save(obj);
		//
		TUser user = ContextHolderUtils.getSessionUser();
		float money = Float.valueOf(req.getParameter("money"));
		TBankSal banksal = new TBankSal();
		banksal.setUserid(user.getId());
		banksal.setBankid(obj.getId());
		banksal.setTotal(money);
		systemService.save(banksal);
		//
		ajx.setMsg("添加银行卡成功！");
		return ajx;
	}

	/**
	 * Pojo信息
	 */
	@RequestMapping(value = "/getPojo_Bank.do")
	public ModelAndView getSinPojo_Bank(HttpServletRequest request, ModelMap map) {
		String id = request.getParameter("id");// 当前页
		float num = 0;
		TBank obj = (TBank) systemService.getClassById(TBank.class, id);
		TUser user = ContextHolderUtils.getSessionUser();
		String hql = "from TBankSal where userid = '" + user.getId()
				+ "' and bankid = '" + obj.getId() + "'";
		List<?> list = systemService.findByQueryHql(hql);
		if (list != null && list.size() > 0) {
			TBankSal banksal = (TBankSal) list.get(0);
			num = banksal.getTotal();
		}
		map.put("pojo", obj);
		map.put("total", num);
		return new ModelAndView("commen/salary/bankedit", map);
	}

	/**
	 * update
	 */
	@RequestMapping(value = "updatebank.do", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson updatePojo_bank(HttpServletRequest req, TBank obj) {
		// TODO Auto-generated method stub
		AjaxJson ajx = new AjaxJson();
		try {
			obj.setBankvalue(banktype.get(obj.getBanktype()));
			systemService.update(obj);
			//
			TUser user = ContextHolderUtils.getSessionUser();
			float money = Float.valueOf(req.getParameter("money"));
			String hql = "from TBankSal where userid = '" + user.getId()
					+ "' and bankid = '" + obj.getId() + "'";
			List<?> list = systemService.findByQueryHql(hql);
			if (list != null && list.size() > 0) {
				TBankSal banksal = (TBankSal) list.get(0);
				banksal.setTotal(money);
				systemService.update(banksal);
			} else {
				TBankSal banksal = new TBankSal();
				banksal.setUserid(user.getId());
				banksal.setBankid(obj.getId());
				banksal.setTotal(money);
				systemService.save(banksal);
			}
			//
			ajx.setMsg("修改银行卡成功！");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			systemService.addLogger("银行卡update失败！",
					Globals.Logger_Leavel_ERROR, Globals.Logger_Type_DELETE);
			ajx.setMsg("银行卡update失败！");
			ajx.setSuccess(false);
		}
		return ajx;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "delbank.do", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson DeletePojo_Bank(HttpServletRequest req) {
		// TODO Auto-generated method stub
		AjaxJson ajx = new AjaxJson();
		try {
			String ids = req.getParameter("id");
			systemService.deleteAll(TBank.class, ids.split(";"), null);
			ajx.setMsg("删除成功！");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			systemService.addLogger("银行卡删除失败！", Globals.Logger_Leavel_ERROR,
					Globals.Logger_Type_DELETE);
			ajx.setMsg("删除失败！");
			ajx.setSuccess(false);
		}
		return ajx;
	}

	/**
	 * 得到类别
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "getbank.do")
	@ResponseBody
	public String getBank() {
		String hql = "from TBank";
		List<?> list = systemService.findByQueryHql(hql);
		return JSONHelper.collectionTojson(list);
	}

	/**
	 * 得到资金总额
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "getbanktotal.do")
	@ResponseBody
	public String getBankTotal() {
		String sql = "SELECT t.s_total as total, b.s_bankname as name FROM s_bank_salary AS t LEFT OUTER JOIN s_bank AS b ON t.s_bankid = b.ID where t.s_total != 0 order by t.s_total desc";
		List<?> list = systemService.findByQuerySql(sql);
		return JSONHelper.collectionTojson(list);
	}

	/**
	 * 跳转
	 */
	@RequestMapping(value = "/redirect.do")
	public String redirect(String jsp) {
		return jsp;
	}
}
