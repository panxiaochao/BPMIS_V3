package bpmis.pxc.system.controller.core.salary;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.pxcbpmisframework.core.common.qbc.CriteriaQuery;
import org.pxcbpmisframework.core.common.qbc.HqlQuery;
import org.pxcbpmisframework.core.util.ContextHolderUtils;
import org.pxcbpmisframework.core.util.JSONHelper;
import org.pxcbpmisframework.core.util.StringUtil;
import org.pxcbpmisframework.core.util.oConvertUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import bpmis.pxc.system.hicharts.pojo.HiChartsBase;
import bpmis.pxc.system.pojo.TUser;
import bpmis.pxc.system.pojo.salary.TSalary;
import bpmis.pxc.system.service.SystemService;

@Controller
@RequestMapping("/hicharts")
public class SalaryHicharts {
	private static final Logger logger = Logger.getLogger(SalaryHicharts.class);
	@Resource
	private SystemService systemService;

	/**
	 * 
	 */
	@RequestMapping(value = "/hibar.do")
	@ResponseBody
	public Map<?, ?> getHiBar(HttpServletRequest request) {
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");

		String sql = "select t.s_bank, sum(s_store) as store, sum(s_pay) as pay from s_salarytable t where t.s_tradetime > '"
				+ starttime
				+ "' and t.s_tradetime < '"
				+ endtime
				+ "' group by t.s_bank";
		System.out.println("*********** sql : " + sql);
		List<?> list = systemService.findByQuerySql(sql);
		List<Object> listbar = new ArrayList<Object>();
		StringBuilder categories = new StringBuilder();

		HiChartsBase in = new HiChartsBase();
		double[] datain = new double[list.size()];
		HiChartsBase out = new HiChartsBase();
		double[] dataout = new double[list.size()];

		for (int i = 0, len = list.size(); i < len; i++) {
			Object[] obj = (Object[]) list.get(i);
			categories.append(obj[0].toString().split(";")[1]).append(";");
			datain[i] = Double.parseDouble(obj[1].toString());
			dataout[i] = Double.parseDouble(obj[2].toString());
		}

		in.setName("收入");
		in.setData(datain);
		listbar.add(in);
		out.setName("支出");
		out.setData(dataout);
		listbar.add(out);

		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("list", listbar);
		map.put("title", starttime + " 至 " + endtime + " 数据统计");
		if (categories.length() == 0)
			map.put("xAxis", "");
		else
			map.put("xAxis", categories.substring(0, categories.length() - 1));

		// System.out.println("***********" + JSONHelper.mapTojson(map));
		return map;
	}

	@RequestMapping(value = "/hibarline.do")
	@ResponseBody
	public Map<?, ?> getHiBar_line(HttpServletRequest request) {
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		String[] totaltime = StringUtil.splitString(SalaryHicharts.DateFormat(
				starttime, endtime), ";");
		String sql = "";
		List<?> list = null;

		List<Object> listbar = new ArrayList<Object>();
		StringBuilder categories = new StringBuilder();
		HiChartsBase in = new HiChartsBase();
		double[] datain = new double[totaltime.length];
		HiChartsBase out = new HiChartsBase();
		double[] dataout = new double[totaltime.length];
		int i = 0;
		for (String strtime : totaltime) {
			sql = "select IsNULL(sum(s_store),0) as store, IsNULL(sum(s_pay),0) as pay from s_salarytable t where t.s_tradetime like '"
					+ strtime + "%' ";
			System.out.println("*********** sql : " + sql);
			list = systemService.findByQuerySql(sql);
			Object[] obj = (Object[]) list.get(0);
			categories.append(strtime).append(";");
			datain[i] = Double.parseDouble(obj[0].toString());
			dataout[i] = Double.parseDouble(obj[1].toString());
			i++;
		}
		in.setName("收入");
		in.setData(datain);
		listbar.add(in);
		out.setName("支出");
		out.setData(dataout);
		listbar.add(out);

		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("list", listbar);
		map.put("title", starttime + " 至 " + endtime + " 数据统计");
		map.put("xAxis", categories.substring(0, categories.length() - 1));
		System.out.println("***********" + JSONHelper.mapTojson(map));
		return map;
	}

	public static String DateFormat(String starttime, String endtime) {
		String[] stime = starttime.split("-");
		String totaltime = starttime + ";"; // 先初始化
		int year = Integer.parseInt(stime[0]);
		int month = Integer.parseInt(stime[1]);
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(year, month, 01);
		boolean flag = true;
		String temp = "";
		while (flag) {
			temp = String.valueOf(calendar.get(Calendar.YEAR)) + "-";
			if (calendar.get(Calendar.MONTH) + 1 < 10)
				temp += "0" + String.valueOf(calendar.get(Calendar.MONTH) + 1);
			else
				temp += String.valueOf(calendar.get(Calendar.MONTH) + 1);
			totaltime += temp + ";";
			if (temp.equals(endtime))
				flag = false;
			else
				calendar.add(Calendar.MONTH, 1);
		}
		return totaltime.substring(0, totaltime.length() - 1);
	}

	/**
	 * 得到list
	 */
	@RequestMapping(value = "/salarydetail.do")
	@ResponseBody
	public Map<?, ?> getList(HttpServletRequest request) {
		// TODO Auto-generated method stub
		TUser user = ContextHolderUtils.getSessionUser();
		String pageNum = request.getParameter("page");// 当前页
		String rows = request.getParameter("rows");// 每页多少数据
		// 查询字段
		String time = request.getParameter("time");
		String type = request.getParameter("type");
		//
		CriteriaQuery cq = new CriteriaQuery();
		// 排序字段
		Map<Object, Object> maporder = HqlQuery.getHashMapsObj();
		maporder.put("desc", "createtime");
		// 查询字段
		Map<Object, String[]> mapparms = HqlQuery.getHashMapsObjs();
		mapparms.put("userid", new String[] { HqlQuery.Restrictions_eq,
				user.getId() });
		mapparms.put("trdetype",
				new String[] { HqlQuery.Restrictions_eq, type });

		if (!"".equals(time) && null != time)
			mapparms.put("tradetime", new String[] {
					HqlQuery.Restrictions_like, time + "%" });

		cq.setMapparms(mapparms); // 参数
		//		
		cq.setMaporder(maporder); // 排序
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

	public static void main(String[] args) {
		SalaryHicharts.DateFormat("2014-08", "2014-10");
	}
}
