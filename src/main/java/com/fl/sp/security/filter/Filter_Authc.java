/*
  Created: 方磊
  Date: 2017年8月18日  上午8:59:55

*/
package com.fl.sp.security.filter;

import com.fl.sp.common.CommonHelp;
import com.fl.sp.common.TypeUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class Filter_Authc extends FormAuthenticationFilter {
	/**
	 * (1)访问地址必须存在，否则报404错误 (2)访问地址必须配置authc
	 * (3)请求不是Ajax请求(4)帐号登录失效或没有登录的情况下(5)否则不会调用这个filter
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		// 是否是登录请求地址，根据loginUrl调用pathMatcher方法判断
		String requestURI = getPathWithinApplication(request);
		System.out.println("请求的url:"+requestURI);
		if (isLoginRequest(request, response)) {
			if (isLoginSubmission(request, response)) {
				return executeLogin(request, response);
			} else {
				// 放行 allow them to see the login page ;)
				return true;
			}
		} else {
			HttpServletRequest httpRequest = WebUtils.toHttp(request);

			if (TypeUtils.isAjax(httpRequest)) {// Ajax请求
				HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
				httpServletResponse.setCharacterEncoding("UTF-8");
				PrintWriter out = httpServletResponse.getWriter();
				if (TypeUtils.isGet(httpRequest)) {
					out.println("<script>alert('登录失效,或者您没有访问权限');</script>");
				} else {
					out.println("登录失效,或者您没有访问权限");
				}
				out.flush();
				out.close();
				return false;
			} else {
				setLoginUrl("/user/login?nosession");
				saveRequestAndRedirectToLogin(request, response);
			}
			return false;
		}
	}

	private void out(ServletResponse hresponse, Map<String, String> resultMap)
			throws IOException {
		try {
			hresponse.setCharacterEncoding("UTF-8");
			PrintWriter out = hresponse.getWriter();
			out.println(CommonHelp.MyToJSONString(resultMap));
			out.flush();
			out.close();
		} catch (Exception e) {
			System.err.println("KickoutSessionFilter.class 输出JSON异常，可以忽略。");
		}
	}
}
