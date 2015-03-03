package org.pxcbpmisframework.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author panxiaochao
 * @ClassName BrowserUtils
 * @Description TODO
 * @date 2013-5-26
 */
public class BrowserUtils {
	// 判断是否是IE
	public static boolean isIE(HttpServletRequest request) {
		return request.getHeader("USER-AGENT").toLowerCase().indexOf("msie") > 0 ? true
				: false;
	}

	/**
	 * 获取IE版本
	 * 
	 * @param request
	 * @return
	 */
	public static Double getIEversion(HttpServletRequest request) {
		Double version = 0.0;
		if (getBrowserType(request, "msie 11.0")) {
			version = 11.0;
		}
		if (getBrowserType(request, "msie 10.0")) {
			version = 10.0;
		}
		if (getBrowserType(request, "msie 9.0")) {
			version = 9.0;
		}
		if (getBrowserType(request, "msie 8.0")) {
			version = 8.0;
		}
		if (getBrowserType(request, "msie 7.0")) {
			version = 7.0;
		}
		if (getBrowserType(request, "msie 6.0")) {
			version = 6.0;
		}
		return version;
	}
	/**
	 * 获取USER-AGENT
	 * 
	 * @param request
	 * @return
	 */
	public static String getUserAgent(HttpServletRequest request){
		return request.getHeader("USER-AGENT");
	}

	/**
	 * 获取浏览器类
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("null")
	public static String getBrowserType(HttpServletRequest request) {
		BrowserType browserType = null;
		StringBuffer brower = null;
		if (getBrowserType(request, "msie 10.0")) {
			browserType = BrowserType.IE10;
			brower.append(BrowserType.IE10).append(",");
		}
		if (getBrowserType(request, "msie 9.0")) {
			browserType = BrowserType.IE9;
			brower.append(BrowserType.IE9).append(",");
		}
		if (getBrowserType(request, "msie 8.0")) {
			browserType = BrowserType.IE8;
			brower.append(BrowserType.IE8).append(",");
		}
		if (getBrowserType(request, "msie 7.0")) {
			browserType = BrowserType.IE7;
			brower.append(BrowserType.IE7).append(",");
		}
		if (getBrowserType(request, "msie 6.0")) {
			browserType = BrowserType.IE6;
			brower.append(BrowserType.IE6).append(",");
		}
		if (getBrowserType(request, "maxthon")) {
			browserType = BrowserType.Maxthon;
			brower.append(BrowserType.Maxthon).append(",");
		}
		if (getBrowserType(request, "firefox")) {
			browserType = BrowserType.Firefox;
			brower.append(BrowserType.Firefox).append(",");
		}
		if (getBrowserType(request, "safari")) {
			browserType = BrowserType.Safari;
			brower.append(BrowserType.Safari).append(",");
		}
		if (getBrowserType(request, "chrome")) {
			browserType = BrowserType.Chrome;
			brower.append(BrowserType.Chrome).append(",");
		}
		if (getBrowserType(request, "opera")) {
			browserType = BrowserType.Opera;
			brower.append(BrowserType.Opera).append(",");
		}
		if (getBrowserType(request, "camino")) {
			browserType = BrowserType.Camino;
			brower.append(BrowserType.Camino).append(",");
		}
		if (brower == null)
			return "";
		else
			return brower.toString().substring(0,
					brower.toString().length() - 1);
	}

	private static boolean getBrowserType(HttpServletRequest request,
			String brosertype) {
		return request.getHeader("USER-AGENT").toLowerCase()
				.indexOf(brosertype) > 0 ? true : false;
	}

	private final static String IE11 = "MSIE 11.0";
	private final static String IE10 = "MSIE 10.0";
	private final static String IE9 = "MSIE 9.0";
	private final static String IE8 = "MSIE 8.0";
	private final static String IE7 = "MSIE 7.0";
	private final static String IE6 = "MSIE 6.0";
	private final static String MAXTHON = "Maxthon";
	private final static String QQ = "QQBrowser";
	private final static String GREEN = "GreenBrowser";
	private final static String SE360 = "360SE";
	private final static String FIREFOX = "Firefox";
	private final static String OPERA = "Opera";
	private final static String CHROME = "Chrome";
	private final static String SAFARI = "Safari";
	private final static String OTHER = "其它";

	public static String checkBrowse(HttpServletRequest request) {
		String userAgent = request.getHeader("USER-AGENT");
		if (regex(OPERA, userAgent))
			return OPERA;
		if (regex(CHROME, userAgent))
			return CHROME;
		if (regex(FIREFOX, userAgent))
			return FIREFOX;
		if (regex(SAFARI, userAgent))
			return SAFARI;
		if (regex(SE360, userAgent))
			return SE360;
		if (regex(GREEN, userAgent))
			return GREEN;
		if (regex(QQ, userAgent))
			return QQ;
		if (regex(MAXTHON, userAgent))
			return MAXTHON;
		if (regex(IE11, userAgent))
			return IE11;
		if (regex(IE10, userAgent))
			return IE10;
		if (regex(IE9, userAgent))
			return IE9;
		if (regex(IE8, userAgent))
			return IE8;
		if (regex(IE7, userAgent))
			return IE7;
		if (regex(IE6, userAgent))
			return IE6;
		return OTHER;
	}

	public static boolean regex(String regex, String str) {
		Pattern p = Pattern.compile(regex, Pattern.MULTILINE);
		Matcher m = p.matcher(str);
		return m.find();
	}

	public static void main(String[] args) {
		//40288ad74757f8c2014757f9cb710000
		String var = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Maxthon/4.4.1.2000 Chrome/30.0.1599.101 Safari/537.36";
		String varie = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E)";
		System.out.println("40288ad74757f8c2014757f9cb710000".length());
		for (BrowserType s : BrowserType.values()) {
			System.out
					.println(s
							+ ":"
							+ (varie.toLowerCase()
									.indexOf(s.name().toLowerCase()) > 0 ? true
									: false));
		}

	}

}
