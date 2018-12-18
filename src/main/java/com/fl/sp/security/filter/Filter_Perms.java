/*
  Created: 方磊
  Date: 2017年8月22日  下午5:17:44

*/
package com.fl.sp.security.filter;

import com.fl.sp.common.TypeUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Filter_Perms extends AuthorizationFilter {

	// TODO - complete JavaDoc
	/**
	 * 当权限验证没有通过时调用此方法
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {

		Subject subject = getSubject(request, response);
		//用户没有登录时，跳转到登录页面
		if (subject.getPrincipal() == null) {
			saveRequestAndRedirectToLogin(request, response);
		} else {
			HttpServletRequest httpRequest = WebUtils.toHttp(request);
			// (1)如果是Ajax请求,不进行跳转
			// java.lang.IllegalStateException: Cannot call sendRedirect() after
			// the response has been committed
			if (!TypeUtils.isAjax(httpRequest)) {// 非Ajax请求
				// If subject is known but not authorized, redirect to the
				// unauthorized URL if there is one
				// If no unauthorized URL is specified, just return an
				// unauthorized
				// HTTP status code
				String unauthorizedUrl = getUnauthorizedUrl();
				// SHIRO-142 - ensure that redirect _or_ error code occurs -
				// both
				// 设置unauthorizedUrl时跳转到unauthorizedUrl
				if (StringUtils.hasText(unauthorizedUrl)) {
					WebUtils.issueRedirect(request, response, unauthorizedUrl);
				} else {
					WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
				}
			}
		}
		return false;
	}

	/**
	 * 请求url时，用户必须拥有所有访问权限才可以请求url,,,题外：也可以设置满足一个权限时访问
	 * @param request
	 * @param response
	 * @param mappedValue
	 * @return
	 * @throws IOException
	 */
	public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws IOException {
		Subject subject = getSubject(request, response);
		//获取请求url的  访问权限
		String[] perms = (String[]) mappedValue;
		boolean isPermitted = false;
		if (perms != null && perms.length > 0) {
			try {
				if (perms.length == 1) {
					if (!subject.isPermitted(perms[0])) {
						isPermitted = false;
					} else {
						isPermitted = true;
					}
				} else {
					if (!subject.isPermittedAll(perms)) {
						isPermitted = false;
					} else {
						isPermitted = true;
					}
				}
			} catch (Exception e) {
				isPermitted = false;
			}
		} else {
			//没有设置访问权限
			isPermitted = true;
		}

		if (!isPermitted) {
			HttpServletRequest httpRequest = WebUtils.toHttp(request);
			if (TypeUtils.isAjax(httpRequest)) {// Ajax请求
				HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
				httpServletResponse.setCharacterEncoding("UTF-8");
				PrintWriter out = httpServletResponse.getWriter();
				if (TypeUtils.isGet(httpRequest)) {
					out.println("<script>alert('您没有权限进行操作！');</script>");
				} else {
					out.println("您没有权限进行操作！");
				}
				out.flush();
				out.close();
			}
		}
		return isPermitted;
	}
}
