package org.pxcbpmisframework.core.interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import bpmis.pxc.system.manager.Client;
import bpmis.pxc.system.manager.ClientManager;

public class WebAusFilter implements Filter {
	private static final Logger logger = Logger.getLogger(WebAusFilter.class);
	private static final long serialVersionUID = 1L;

	public void destroy() {
		// TODO Auto-generated method stub
		logger.info("=========  BPMIS ShutDown! =========");
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		// -----------------------------
		System.out.println("**************>client: " + client);
		// logger.info("Request mode：" + request.getMethod());
		logger.info("Request URl：" + request.getRequestURL());
		// logger.info("RemoteAddr：" + request.getRemoteAddr());
		// logger.info("RemoteHost：" + request.getRemoteHost());

		String path = request.getRequestURI();
		System.out.println("**************>URL: " + path);

		// 由于web.xml中设置Filter过滤jsp，可以排除不需要过滤的url
		if (path.endsWith("login.jsp") || path.endsWith("/bpmis3/")) {
			chain.doFilter(req, res);
			return;
		}
		// 判断用户是否登录，进行页面的处理
		if (null == client) {
			forward(request, response);
			return;
		} else {
			chain.doFilter(request, response);
		}

	}

	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
		logger.info("=========  BPMIS Start! =========");
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
		request.getRequestDispatcher("/webpage/error.jsp").forward(request,
				response);
	}
}
