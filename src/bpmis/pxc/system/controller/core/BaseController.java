package bpmis.pxc.system.controller.core;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.pxcbpmisframework.core.json.AjaxJson;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

/**
 * 总类
 * 
 * @author panxiaochao
 * 
 */
public interface BaseController {
	// public AjaxJson SavePojo(HttpServletRequest req, Object obj);

	public Map<?, ?> getList(HttpServletRequest request);
	public ModelAndView getSinPojo(HttpServletRequest request, ModelMap map);
	public AjaxJson DeletePojo(HttpServletRequest req);
	public String redirect(String jsp);
}
