package org.pxcbpmisframework.core.page;

import org.pxcbpmisframework.core.util.oConvertUtils;

/**
 * 
 * @ClassName: PageHtmlUtils   
 * @Description: TODO(分页html)   
 * @author Mr_Pxc  
 * @date 2014-2-7 上午09:28:59   
 * @project_name：Salary            
 * @change(1.修复BUG：在没有数据的时候也可以进行点击下一页)
 * @remark    
 * @version 2.0
 */
public class PageHtmlUtils {
	private static String Start_Form = "<form id=\"searchForm\" action=\"\" method=\"post\" >";
	private static String End_Form = "</form>";
	private static String Input_Hidden = "<input type=\"hidden\" name=\"currentPage\" id=\"pageNo\" value=\"\" />";
	/*
	 * ===================== 默认样式1 Default//正规的正方形====================
	 */
	private static String Div_Class_Pagination = "<div class=\"pagination center\">"; // 缺</div>
	private static String Ul_Class_Pager = "<ul>";// 缺</ul>
	private static String Li_Class_Previous_s = "<li class=\"previous\"><a href=\"javascript:page(";
	private static String Li_Class_Previous_e = ");\">上一页</a></li>";// pdisabled=disabled
	private static String Li_Class_Previous_Disabled = "<li class=\"previous disabled\"><a href=\"javascript:void(0);\">上一页</a></li>";// pdisabled=disabled
	private static String Li_Active_Content = "<li class=\"active\"><a>"; // 缺尾
	private static String Li_Content = "<li><a href=\"javascript:page("; // 缺尾
	private static String Li_Class_Next_s = "<li class=\"next\"><a href=\"javascript:page(";
	private static String Li_Class_Next_e = ");\">下一页 </a></li>";// ndisabled=disabled
	private static String Li_Class_Next_Disabled = "<li class=\"next disabled\"><a href=\"javascript:void(0);\">下一页 </a></li>";// ndisabled=disabled
	private static String Li_Sheng = "<li><a href=\"javascript:void(0);\">......</a></li>";
	/*
	 * ===================== 默认样式2 样式1//圆形====================
	 */
	private static String Ul_Class_Pager1 = "<ul class=\"pager\">";// 缺</ul>

	/*
	 * ===================== 后尾继续====================
	 */
	private static String Ul_Class_Last = "<ul>"
			+ "<li class=\"disabled controls\">"
			+ "<a href=\"javascript:\">当前第 <input type=\"text\" value=\"#currentPage#\" onkeypress=\"var e=window.event||this;var c=e.keyCode||e.which;if(c==13)return page(this.value);\" onclick=\"this.select();\"> / #totalPages# 页，共 #totalRecords# 条</a>"
			+ "</li>" + "</ul>";

	private static String Ul_Class_Last_Simple = "<ul>"
			+ "<li class=\"disabled controls\">"
			+ "<a href=\"javascript:void(0);\">当前第 #currentPage# / #totalPages# 页，共 #totalRecords# 条</a>"
			+ "</li>" + "</ul>";

	public PageHtmlUtils() {

	}

	/**
	 * 
	 * @Title: DefaultPagination
	 * @Description: TODO(默认样式)
	 * @param @param currentPage 当前页
	 * @param @param pageSize 页数量
	 * @param @param totalPages 总页数
	 * @return String
	 * @throws
	 */
	public static String getDefault(Page page) {
		return DefaultPagination(page.getCurrentPage(), page.getPageSize(),
				page.getTotalPages(), page.getTotalRecords());
	}

	private static String DefaultPagination(int currentPage, int pageSize,
			int totalPages, int totalRecords) {
		StringBuffer page = new StringBuffer();
		page.append(Start_Form).append(Input_Hidden).append(
				Div_Class_Pagination).append(Ul_Class_Pager);// 先添加头
		if (totalRecords != 0) {
			// totalPages <=5
			if (totalPages <= 5) {
				if (currentPage == 1) // ==第一页
					page.append(Li_Class_Previous_Disabled);
				else
					page.append(Li_Class_Previous_s).append(currentPage - 1)
							.append(Li_Class_Previous_e);
				for (int i = 1; i < totalPages + 1; i++) {
					if (currentPage == i)
						page.append(Li_Active_Content).append(i).append(
								"</a></li>");
					else
						page.append(Li_Content).append(i).append(");\">")
								.append(i).append("</a></li>");
				}
				if (currentPage == totalPages) // ==总页数
					page.append(Li_Class_Next_Disabled);
				else
					page.append(Li_Class_Next_s).append(currentPage + 1)
							.append(Li_Class_Next_e);

			} else {// totalPages >5
				if (currentPage < 5) {
					if (currentPage == 1) // ==第一页
						page.append(Li_Class_Previous_Disabled);
					else
						page.append(Li_Class_Previous_s)
								.append(currentPage - 1).append(
										Li_Class_Previous_e);
					for (int i = 1; i < 6; i++) {
						if (currentPage == i)
							page.append(Li_Active_Content).append(i).append(
									"</a></li>");
						else
							page.append(Li_Content).append(i).append(");\">")
									.append(i).append("</a></li>");
					}
					page.append(Li_Sheng);
					if (currentPage == totalPages) // ==总页数
						page.append(Li_Class_Next_Disabled);
					else
						page.append(Li_Class_Next_s).append(currentPage + 1)
								.append(Li_Class_Next_e);

				} else {
					page.append(Li_Class_Previous_s).append(currentPage - 1)
							.append(Li_Class_Previous_e);
					page.append(Li_Sheng);
					page.append(Li_Content).append(currentPage - 2).append(
							");\">").append(currentPage - 2)
							.append("</a></li>");
					page.append(Li_Content).append(currentPage - 1).append(
							");\">").append(currentPage - 1)
							.append("</a></li>");
					page.append(Li_Active_Content).append(currentPage).append(
							"</a></li>");
					if (currentPage + 1 == totalPages) {
						page.append(Li_Content).append(currentPage + 1).append(
								");\">").append(currentPage + 1).append(
								"</a></li>");
					} else if (currentPage + 2 <= totalPages) {
						page.append(Li_Content).append(currentPage + 1).append(
								");\">").append(currentPage + 1).append(
								"</a></li>");
						page.append(Li_Content).append(currentPage + 2).append(
								");\">").append(currentPage + 2).append(
								"</a></li>");
					}
					if (currentPage + 2 <= totalPages)
						page.append(Li_Sheng);
					if (currentPage == totalPages) // ==总页数
						page.append(Li_Class_Next_Disabled);
					else
						page.append(Li_Class_Next_s).append(currentPage + 1)
								.append(Li_Class_Next_e);

				}
			}
		} else {
			page.append(Li_Class_Previous_Disabled).append(
					Li_Class_Next_Disabled);
		}
		page.append("</ul>").append(Ul_Class_Last).append("</div>").append(
				End_Form);
		return page.toString().replaceAll("#currentPage#",
				oConvertUtils.getString(currentPage)).replaceAll(
				"#totalPages#", oConvertUtils.getString(totalPages))
				.replaceAll("#totalRecords#",
						oConvertUtils.getString(totalRecords)).replaceAll(
						"#pageSize#", oConvertUtils.getString(pageSize));
	}

	/**
	 * 
	 * @Title: getPaginationCir
	 * @Description: TODO(默认样式)
	 * @param @param currentPage 当前页
	 * @param @param pageSize 页数量
	 * @param @param totalPages 总页数
	 * @return String
	 * @throws
	 */
	public static String getPageCir(Page page) {
		return getPaginationCir(page.getCurrentPage(), page.getPageSize(), page
				.getTotalPages(), page.getTotalRecords());
	}

	private static String getPaginationCir(int currentPage, int pageSize,
			int totalPages, int totalRecords) {
		StringBuffer page = new StringBuffer();
		page.append(Start_Form).append(Input_Hidden).append(
				Div_Class_Pagination).append(Ul_Class_Pager1);// 先添加头
		if (totalRecords != 0) {
			// totalPages <=5
			if (totalPages <= 5) {
				if (currentPage == 1) // ==第一页
					page.append(Li_Class_Previous_Disabled);
				else
					page.append(Li_Class_Previous_s).append(currentPage - 1)
							.append(Li_Class_Previous_e);
				for (int i = 1; i < totalPages + 1; i++) {
					if (currentPage == i)
						page.append(Li_Active_Content).append(i).append(
								"</a></li>");
					else
						page.append(Li_Content).append(i).append(");\">")
								.append(i).append("</a></li>");
				}
				if (currentPage == totalPages) // ==总页数
					page.append(Li_Class_Next_Disabled);
				else
					page.append(Li_Class_Next_s).append(currentPage + 1)
							.append(Li_Class_Next_e);

			} else {// totalPages >5
				if (currentPage < 5) {
					if (currentPage == 1) // ==第一页
						page.append(Li_Class_Previous_Disabled);
					else
						page.append(Li_Class_Previous_s)
								.append(currentPage - 1).append(
										Li_Class_Previous_e);
					for (int i = 1; i < 6; i++) {
						if (currentPage == i)
							page.append(Li_Active_Content).append(i).append(
									"</a></li>");
						else
							page.append(Li_Content).append(i).append(");\">")
									.append(i).append("</a></li>");
					}
					page.append(Li_Sheng);
					if (currentPage == totalPages) // ==总页数
						page.append(Li_Class_Next_Disabled);
					else
						page.append(Li_Class_Next_s).append(currentPage + 1)
								.append(Li_Class_Next_e);

				} else {
					page.append(Li_Class_Previous_s).append(currentPage - 1)
							.append(Li_Class_Previous_e);
					page.append(Li_Sheng);
					page.append(Li_Content).append(currentPage - 2).append(
							");\">").append(currentPage - 2)
							.append("</a></li>");
					page.append(Li_Content).append(currentPage - 1).append(
							");\">").append(currentPage - 1)
							.append("</a></li>");
					page.append(Li_Active_Content).append(currentPage).append(
							"</a></li>");
					if (currentPage + 1 == totalPages) {
						page.append(Li_Content).append(currentPage + 1).append(
								");\">").append(currentPage + 1).append(
								"</a></li>");
					} else if (currentPage + 2 <= totalPages) {
						page.append(Li_Content).append(currentPage + 1).append(
								");\">").append(currentPage + 1).append(
								"</a></li>");
						page.append(Li_Content).append(currentPage + 2).append(
								");\">").append(currentPage + 2).append(
								"</a></li>");
					}
					if (currentPage + 2 <= totalPages)
						page.append(Li_Sheng);
					if (currentPage == totalPages) // ==总页数
						page.append(Li_Class_Next_Disabled);
					else
						page.append(Li_Class_Next_s).append(currentPage + 1)
								.append(Li_Class_Next_e);

				}
			}
		} else {
			page.append(Li_Class_Previous_Disabled).append(
					Li_Class_Next_Disabled);
		}
		page.append("</ul>").append(Ul_Class_Last).append("</div>").append(
				End_Form);
		return page.toString().replaceAll("#currentPage#",
				oConvertUtils.getString(currentPage)).replaceAll(
				"#totalPages#", oConvertUtils.getString(totalPages))
				.replaceAll("#totalRecords#",
						oConvertUtils.getString(totalRecords)).replaceAll(
						"#pageSize#", oConvertUtils.getString(pageSize));
	}

	/**
	 * 
	 * @Title: getPageDcp
	 * @Description: TODO(BPMIS_WebDCP首页专用)
	 * @param @param page
	 * @return String
	 */
	public String getPageDcp(Page page) {
		return getPageDcp(page.getCurrentPage(), page.getPageSize(), page
				.getTotalPages(), page.getTotalRecords());
	}

	private static String getPageDcp(int currentPage, int pageSize,
			int totalPages, int totalRecords) {
		StringBuffer page = new StringBuffer();
		page.append(Start_Form).append(Input_Hidden).append(
				Div_Class_Pagination).append(Ul_Class_Pager);// 先添加头
		if (totalRecords != 0) {
			// totalPages <=5
			if (totalPages <= 5) {
				if (currentPage == 1) // ==第一页
					page.append(Li_Class_Previous_Disabled);
				else
					page.append(Li_Class_Previous_s).append(currentPage - 1)
							.append(Li_Class_Previous_e);
				for (int i = 1; i < totalPages + 1; i++) {
					if (currentPage == i)
						page.append(Li_Active_Content).append(i).append(
								"</a></li>");
					else
						page.append(Li_Content).append(i).append(");\">")
								.append(i).append("</a></li>");
				}
				if (currentPage == totalPages) // ==总页数
					page.append(Li_Class_Next_Disabled);
				else
					page.append(Li_Class_Next_s).append(currentPage + 1)
							.append(Li_Class_Next_e);

			} else {// totalPages >5
				if (currentPage < 5) {
					if (currentPage == 1) // ==第一页
						page.append(Li_Class_Previous_Disabled);
					else
						page.append(Li_Class_Previous_s)
								.append(currentPage - 1).append(
										Li_Class_Previous_e);
					for (int i = 1; i < 6; i++) {
						if (currentPage == i)
							page.append(Li_Active_Content).append(i).append(
									"</a></li>");
						else
							page.append(Li_Content).append(i).append(");\">")
									.append(i).append("</a></li>");
					}
					page.append(Li_Sheng);
					if (currentPage == totalPages) // ==总页数
						page.append(Li_Class_Next_Disabled);
					else
						page.append(Li_Class_Next_s).append(currentPage + 1)
								.append(Li_Class_Next_e);

				} else {
					page.append(Li_Class_Previous_s).append(currentPage - 1)
							.append(Li_Class_Previous_e);
					page.append(Li_Sheng);
					page.append(Li_Content).append(currentPage - 2).append(
							");\">").append(currentPage - 2)
							.append("</a></li>");
					page.append(Li_Content).append(currentPage - 1).append(
							");\">").append(currentPage - 1)
							.append("</a></li>");
					page.append(Li_Active_Content).append(currentPage).append(
							"</a></li>");
					if (currentPage + 1 == totalPages) {
						page.append(Li_Content).append(currentPage + 1).append(
								");\">").append(currentPage + 1).append(
								"</a></li>");
					} else if (currentPage + 2 <= totalPages) {
						page.append(Li_Content).append(currentPage + 1).append(
								");\">").append(currentPage + 1).append(
								"</a></li>");
						page.append(Li_Content).append(currentPage + 2).append(
								");\">").append(currentPage + 2).append(
								"</a></li>");
					}
					if (currentPage + 2 <= totalPages)
						page.append(Li_Sheng);
					if (currentPage == totalPages) // ==总页数
						page.append(Li_Class_Next_Disabled);
					else
						page.append(Li_Class_Next_s).append(currentPage + 1)
								.append(Li_Class_Next_e);

				}
			}
		} else {
			page.append(Li_Class_Previous_Disabled).append(
					Li_Class_Next_Disabled);
		}
		page.append("</ul>").append(Ul_Class_Last_Simple).append("</div>")
				.append(End_Form);
		return page.toString().replaceAll("#currentPage#",
				oConvertUtils.getString(currentPage)).replaceAll(
				"#totalPages#", oConvertUtils.getString(totalPages))
				.replaceAll("#totalRecords#",
						oConvertUtils.getString(totalRecords)).replaceAll(
						"#pageSize#", oConvertUtils.getString(pageSize));
	}

	// System.out.println("http://"+request.getHeader("Host")+""+request.getRequestURI());

	/*
	 * function page(n,s){ $("#pageNo").val(n); $("#pageSize").val(s);
	 * $("#searchForm").submit(); return false; }
	 * 
	 * test
	 */
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		for (int i = 1; i < 2; i++) {
			System.out.println(new PageHtmlUtils().getPageDcp(i, 10, 0, 0));
		}

	}

}
