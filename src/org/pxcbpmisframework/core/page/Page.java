package org.pxcbpmisframework.core.page;

/**
 * 
 * @ClassName: Page
 * @Description: TODO(分页工具类)
 * @author Mr_Pxc
 * @date 2013-6-19 下午03:11:44
 * @project_name：BPMIS_1
 * @version 1.0
 */
public class Page {

	private int currentPage = 1;// 当前页
	private int pageSize = 10;// 每页的数量
	private int totalRecords;// 总记录数
	private int totalPages;// 总页数

	public Page() {

	}

	/**
	 * 带参的构造方法
	 * 
	 * @param currentPage
	 * @param pageSize
	 */
	public Page(int currentPage, int pageSize) {
		pageSizeExceptionMsg(String.valueOf(pageSize));
		currentPageExceptionMsg(String.valueOf(currentPage));
	}

	/**
	 * 带参的构造方法
	 * 
	 * @param currentPage
	 * @param pageSize
	 */
	public Page(String currentPage, String pageSize) {
		pageSizeExceptionMsg(String.valueOf(pageSize));
		currentPageExceptionMsg(String.valueOf(currentPage));
	}

	/**
	 * 带参的构造方法
	 * 
	 * @param currentPage
	 * @param pageSize
	 */
	public Page(int currentPage, int pageSize, int totalRecords) {
		pageSizeExceptionMsg(String.valueOf(pageSize));
		currentPageExceptionMsg(String.valueOf(currentPage));
		totalRecordExceptionMsg(String.valueOf(totalRecords));
	}

	/**
	 * 带参的构造方法
	 * 
	 * @param currentPageStr
	 * @param pageSizeStr
	 * @param totalRecordsStr
	 */
	public Page(String currentPageStr, String pageSizeStr,
			String totalRecordsStr) {
		pageSizeExceptionMsg(pageSizeStr);
		currentPageExceptionMsg(currentPageStr);
		totalRecordExceptionMsg(totalRecordsStr);
	}

	/**
	 * 设置当前页
	 * 
	 * @param currentPage
	 *            int类型参数
	 */
	public final void setCurrentPage(int currentPage) {
		currentPageExceptionMsg(String.valueOf(currentPage));
	}

	/**
	 * 设置当前页
	 * 
	 * @param currentPage
	 *            String类型的参数
	 */
	public final void setCurrentPage(String currentPageStr) {
		currentPageExceptionMsg(currentPageStr);
	}

	/**
	 * 设置分页单位
	 * 
	 * @param pageSize
	 *            整型的分页单位
	 */
	public final void setPageSize(int pageSize) {
		pageSizeExceptionMsg(String.valueOf(pageSize));
	}

	/**
	 * 设置分页单位
	 * 
	 * @param pageSize
	 *            整型的分页单位
	 */
	public final void setPageSize(String pageSizeStr) {
		pageSizeExceptionMsg(pageSizeStr);
	}

	/**
	 * 设置总记录
	 * 
	 * @param totalRecords
	 *            整型参数
	 */
	public final void setTotalRecords(int totalRecords) {
		totalRecordExceptionMsg(String.valueOf(totalRecords));
	}

	/**
	 * 设置总记录
	 * 
	 * @param totalRecords
	 *            整型参数
	 */
	public final void setTotalRecords(String totalRecordsStr) {
		totalRecordExceptionMsg(totalRecordsStr);
	}

	/**
	 * 获得分页单位
	 * 
	 * @return
	 */
	public final int getPageSize() {
		return pageSize;
	}

	/**
	 * 获得当前页
	 * 
	 * @return
	 */
	public final int getCurrentPage() {
		return currentPage;
	}

	/**
	 * 获取总页数
	 * 
	 * @return
	 */
	public final int getTotalPages() {
		calculateTotalPages();
		return totalPages;
	}

	/**
	 * 获取总记录数
	 * 
	 * @return
	 */
	public final int getTotalRecords() {
		return totalRecords;
	}

	/**
	 * 反馈当前页的异常
	 * 
	 * @param currentPageStr
	 */
	private void currentPageExceptionMsg(String currentPageStr) {
		try {
			currentPage = Integer.parseInt(String.valueOf(currentPageStr));
		} catch (NumberFormatException e) {
			throw new RuntimeException("当前页数字格式有误-->" + currentPage);
		}
		if (currentPage <= 0) {
			throw new RuntimeException("当前页小于或等于零-->" + currentPage);
		}
	}

	/**
	 * 反馈分页单位的异常
	 * 
	 * @param pageSizeStr
	 */
	private void pageSizeExceptionMsg(String pageSizeStr) {
		try {
			pageSize = Integer.parseInt(String.valueOf(pageSizeStr));
		} catch (NumberFormatException e) {
			throw new RuntimeException("当前页数字格式有误-->" + pageSize);
		}
		if (pageSize <= 0) {
			throw new RuntimeException("分页单位小于或等于零-->" + pageSize);
		}
	}

	/**
	 * 反馈总记录数的异常
	 * 
	 * @param totalRecordsStr
	 */
	private void totalRecordExceptionMsg(String totalRecordsStr) {
		try {
			totalRecords = Integer.parseInt(String.valueOf(totalRecordsStr));
		} catch (NumberFormatException e) {
			throw new RuntimeException("总记录数字格式有误-->" + totalRecordsStr);
		}
		if (totalRecords < 0) {
			throw new RuntimeException("总记录小于零-->" + totalRecordsStr);
		}
	}

	/**
	 * 计算总页数
	 */
	private void calculateTotalPages() {
		totalPages = (int) Math.ceil((double) this.totalRecords
				/ (double) pageSize);
	}

	public static void main(String[] args) {
		Page pageUtils = new Page();
		System.out.println("总页数：" + pageUtils.getTotalPages());
		System.out.println("当前页：" + pageUtils.getCurrentPage());
		System.out.println("每页数：" + pageUtils.getPageSize());
		System.out.println("总计数：" + pageUtils.getTotalRecords());

		pageUtils.setTotalRecords(60);

		System.out.println("总页数：" + pageUtils.getTotalPages());
		System.out.println("当前页：" + pageUtils.getCurrentPage());
		System.out.println("每页数：" + pageUtils.getPageSize());
		System.out.println("总计数：" + pageUtils.getTotalRecords());
	}

}
