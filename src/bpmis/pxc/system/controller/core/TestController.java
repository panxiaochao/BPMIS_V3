package bpmis.pxc.system.controller.core;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {
	private static final Logger logger = Logger.getLogger(TestController.class);
	@RequestMapping(value = "test.do")
	public String test(HttpServletRequest request) {
		System.out.println("----------->go test");
		logger.info("测试呢！");
		System.out.println("----------->end test");
		return "success";
	}
}
