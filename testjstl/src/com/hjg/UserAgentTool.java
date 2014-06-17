package com.hjg;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserAgentTool {
	private final static String IE9 = "MSIE 9.0";
	private final static String IE8 = "MSIE 8.0";
	private final static String IE7 = "MSIE 7.0";
	private final static String IE6 = "MSIE 6.0";
	private final static String MAXTHON = "Maxthon";
	private final static String QQ = "QQBrowser";
	private final static String GREEN = "GreenBrowser";
	private final static String SE360 = "360SE";
	private final static String FIREFOX = "Firefox";
	private final static String OPERA = "OPR";
	private final static String CHROME = "Chrome";
	private final static String SAFARI = "Safari";
	private final static String WORLD = "TheWorld";
	private final static String TAOBAO = "TaoBrowser";
	private final static String LIEBAO = "LBBROWSER";
	private final static String BAIDU = "BIDUBrowser";
	private final static String SOUGOU = "SE 2.X MetaSr 1.0";
	private final static String OTHER = "其它";

	public static String getBrowse(String userAgent) {
		if (regex(OPERA, userAgent))
			return "opera";
		if (regex(MAXTHON, userAgent))
			return "aoyou";
		if (regex(TAOBAO, userAgent))
			return "taobao";
		if (regex(LIEBAO, userAgent))
			return "liebao";
		if (regex(WORLD, userAgent))
			return "world";
		if (regex(SE360, userAgent))
			return "qihu360";
		if (regex(QQ, userAgent))
			return "tengxun";
		if (regex(BAIDU, userAgent))
			return "baidu";
		if (regex(SOUGOU, userAgent))
			return "sougou";
		if (regex(CHROME, userAgent))
			return "chrome";
		if (regex(FIREFOX, userAgent))
			return "firefox";
		if (regex(SAFARI, userAgent))
			return "safari";
		if (regex(GREEN, userAgent))
			return "green";
		if (regex(IE9, userAgent) || regex(IE8, userAgent)
				|| regex(IE7, userAgent))
			return "ie7";
		if (regex(IE6, userAgent))
			return "ie6";
		return OTHER;
	}

	private static boolean regex(String regex, String str) {
		Pattern p = Pattern.compile(regex, Pattern.MULTILINE);
		Matcher m = p.matcher(str);
		return m.find();
	}

	/**
	 * 获取客户端操作系统信息，目前只匹配win8、Win
	 * 7、WinXP、Win2003、Win2000、MAC、WinNT、Linux、Mac68k、Win9x
	 * 
	 * @param userAgent
	 *            request.getHeader("user-agent")的返回值
	 * @return
	 */

	public static String getClientOS(String userAgent) {
		String cos = "other";

		Pattern p = Pattern.compile(".*(Windows NT 6\\.2).*");
		Matcher m = p.matcher(userAgent);
		if (m.find()) {
			cos = "win8";
			return cos;
		}
		p = Pattern.compile(".*(Windows NT 6\\.1).*");
		m = p.matcher(userAgent);
		if (m.find()) {
			cos = "win7";
			return cos;
		}

		p = Pattern.compile(".*(Windows NT 6\\.0).*");
		m = p.matcher(userAgent);
		if (m.find()) {
			cos = "winvista";
			return cos;
		}

		p = Pattern.compile(".*(Windows NT 5\\.1|Windows XP).*");
		m = p.matcher(userAgent);
		if (m.find()) {
			cos = "winxp";
			return cos;
		}

		p = Pattern.compile(".*(Windows NT 5\\.2).*");
		m = p.matcher(userAgent);
		if (m.find()) {
			cos = "win2003";
			return cos;
		}

		p = Pattern.compile(".*(Win2000|Windows 2000|Windows NT 5\\.0).*");
		m = p.matcher(userAgent);
		if (m.find()) {
			cos = "win2000";
			return cos;
		}

		p = Pattern.compile(".*(Mac|apple|MacOS8).*");
		m = p.matcher(userAgent);
		if (m.find()) {
			cos = "mac";
			return cos;
		}

//		p = Pattern.compile(".*(WinNT|Windows NT).*");
//		m = p.matcher(userAgent);
//		if (m.find()) {
//			cos = "winnt";
//			return cos;
//		}

		p = Pattern.compile(".*Linux.*");
		m = p.matcher(userAgent);
		if (m.find()) {
			cos = "linux";
			return cos;
		}

		p = Pattern.compile(".*(68k|68000).*");
		m = p.matcher(userAgent);
		if (m.find()) {
			cos = "mac68";
			return cos;
		}

		p = Pattern
				.compile(".*(9x 4.90|Win9(5|8)|Windows 9(5|8)|95/NT|Win32|32bit).*");
		m = p.matcher(userAgent);
		if (m.find()) {
			cos = "win9x";
			return cos;
		}

		return cos;
	}
}
