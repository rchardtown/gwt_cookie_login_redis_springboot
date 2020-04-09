package com.hellokoding.sso.auth;

import org.springframework.web.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CookieUtil {
	/**
	 * 创建Cookie
	 *
	 * @param httpServletResponse
	 * @param cookieName
	 * @param value
	 * @param secure
	 * @param maxAge
	 * @param domain
	 */
	public static void create(HttpServletResponse httpServletResponse, String cookieName, String value, Boolean secure, Integer maxAge, String domain) {
		Cookie cookie = new Cookie(cookieName, value); //name value 是必读的
		cookie.setSecure(secure);//定义cookie的安全性，当该值为true时必须是HTTPS状态下cookie才从客户端附加在HTTP消息中发送到服务端，在HTTP时cookie是不发送的；Secure为false时则可在HTTP状态下传递cookie，Secure缺省为false。
		cookie.setHttpOnly(true);//HttpOnly属性在一定程度上可以防止XSS攻击(XSS攻击类似sql注入，更多资料可以百度查阅)。在web应用中、JSESSIONID (Cookie)没有设置Httponly属性可能会窃取或操纵客户会话和 cookie，它们可能用于模仿合法用户，从而使黑客能够以该用户身份查看或变更用户记录以及执行事务、
		cookie.setMaxAge(maxAge);//定义cookie的有效时间，用秒计数，当超过有效期后，cookie的信息不会从客户端附加在HTTP消息头中发送到服务端。0:表示删除cookie
		cookie.setDomain(domain);//定义可访问该cookie的域名，对一些大的网站，如果希望cookie可以在子网站中共享，可以使用该属性。例如设置Domain为 .bigsite.com ,则sub1.bigsite.com和sub2.bigsite.com都可以访问已保存在客户端的cookie，这时还需要将Path设置为/。
		cookie.setPath("/");//定义网站上可以访问cookie的页面的路径，缺省状态下Path为产生cookie时的路径，此时cookie可以被该路径以及其子路径下的页面访问；可以将Path设置为/，使cookie可以被网站下所有页面访问。
		httpServletResponse.addCookie(cookie);////添加cookie
	}

	/**
	 * 清空Cookie
	 *
	 * @param httpServletResponse
	 * @param name
	 */
	public static void clear(HttpServletResponse httpServletResponse, String name) {
		Cookie cookie = new Cookie(name, null);// 清空cookie值
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(0);//设置为0：有效时长为0
		cookie.setDomain("localhost");
		httpServletResponse.addCookie(cookie);
	}

	/**
	 * 获取客户端传来的Cookie
	 *
	 * @param httpServletRequest
	 * @param cookieName
	 * @return
	 */
	public static String getValue(HttpServletRequest httpServletRequest, String cookieName) {
		Cookie cookie = WebUtils.getCookie(httpServletRequest, cookieName);
		return cookie != null ? cookie.getValue() : null;  //三元运算符，如果cookis !=null 返回cookie.getValue() ,否则返回null
	}

	/**
	 * 获取Cookie数组
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				String name = cookie.getName();
				String value = cookie.getValue();
				out.write(name + "=" + value);
			}
		}
	}
}

