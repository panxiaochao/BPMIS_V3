package org.pxcbpmisframework.core.interceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import bpmis.pxc.system.manager.Client;
import bpmis.pxc.system.manager.ClientManager;

public class SystemInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = Logger
			.getLogger(SystemInterceptor.class);
	private List<String> excludeUrls;

	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		System.out.println("==============>1、执行顺序(preHandle)");
		HttpSession session = request.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		String requestUri = request.getRequestURI(); // 请求路径

		System.out.println("URL：" + requestUri);
		System.out.println("USER-AGENT：" + request.getHeader("USER-AGENT"));
		long startTime = System.currentTimeMillis();
		request.setAttribute("startTime", startTime);

		// if (excludeUrls.contains(url))
		// return true;
		// else {
		// request.setAttribute("ex", "没有权限！");
		// forward(request, response);
		// return false;
		// }

		if (requestUri.endsWith(".do")) {
			return true;
		}

		// 判断用户是否登录，进行页面的处理
		if (null == client) {
			forward(request, response);
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		long startTime = (Long) request.getAttribute("startTime");
		// request.removeAttribute("startTime");
		long endTime = System.currentTimeMillis();
		// modelAndView.addObject("handlingTime", endTime - startTime);
		System.out.println("==============> 2、执行顺序(postHandle)");
		System.out.println("完成请求处理耗时：" + (endTime - startTime) + " 毫秒");
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		long startTime = (Long) request.getAttribute("startTime");
		long endTime = System.currentTimeMillis();
		System.out.println("==============3、执行顺序(afterCompletion)");
		System.out.println("完成视图渲染耗时：" + (endTime - startTime) + " 毫秒");
	}

	/**
	 * 转发
	 * 
	 * @param user
	 * @param req
	 * @return
	 */

	public void forward(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/webpage/login.jsp").forward(request,
				response);
	}

	public static void main(String[] args) {
		List<String> excludeUrls = new ArrayList<String>();
	}

}
