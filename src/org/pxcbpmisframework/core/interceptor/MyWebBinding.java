package org.pxcbpmisframework.core.interceptor;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.pxcbpmisframework.core.util.DataToolsUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

public class MyWebBinding implements WebBindingInitializer {

	public void initBinder(WebDataBinder binder, WebRequest request) {
		// 1. 使用spring自带的CustomDateEditor
		SimpleDateFormat dateFormat = DataToolsUtils
				.getSimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
		// 2. 自定义的PropertyEditorSupport
		// binder.registerCustomEditor(Date.class, new DateConvertEditor());
	}

}
