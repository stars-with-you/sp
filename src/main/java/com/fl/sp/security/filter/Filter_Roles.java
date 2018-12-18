/*
  Created: 方磊
  Date: 2017年8月23日  上午10:51:32

*/
package com.fl.sp.security.filter;

import com.fl.sp.common.TypeUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

public class Filter_Roles extends AuthorizationFilter {

  @Override
  protected boolean isAccessAllowed(
      ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
    HttpServletRequest httpRequest = WebUtils.toHttp(request);
    Subject subject = getSubject(request, response);
    String[] rolesArray = (String[]) mappedValue;
    boolean rst = false;
    if (rolesArray == null || rolesArray.length == 0) {
      // no roles specified, so nothing to check - allow access.
      return true;
    } else {
      Set<String> roles = CollectionUtils.asSet(rolesArray);
      try {
        rst = subject.hasAllRoles(roles);
      } catch (Exception e) {

      }
    }
    if (TypeUtils.isAjax(httpRequest)) { // Ajax请求

      HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
      httpServletResponse.setCharacterEncoding("UTF-8");
      PrintWriter out = httpServletResponse.getWriter();
      if (TypeUtils.isGet(httpRequest)) {
        out.println("<script>alert('', '您没有角色进行操作！');</script>");
      } else {
        out.println("您没有角色进行操作！");
      }
      out.flush();
      out.close();
    }
    return rst;
  }

  /**
   * 当角色验证没有通过时，调用此方法
   *
   * @param request
   * @param response
   * @return
   * @throws IOException
   */
  @Override
  protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
      throws IOException {
    Subject subject = getSubject(request, response);
    // If the subject isn't identified, redirect to login URL
    if (subject.getPrincipal() == null) {
      saveRequestAndRedirectToLogin(request, response);
    } else {
      HttpServletRequest httpRequest = WebUtils.toHttp(request);
      // (1)如果是Ajax请求,不进行跳转
      // java.lang.IllegalStateException: Cannot call sendRedirect() after
      // the response has been committed
      if (!TypeUtils.isAjax(httpRequest)) { // Ajax请求
        // If subject is known but not authorized, redirect to the
        // unauthorized URL if there is one
        // If no unauthorized URL is specified, just return an
        // unauthorized
        // HTTP status code
        String unauthorizedUrl = getUnauthorizedUrl();
        // SHIRO-142 - ensure that redirect _or_ error code occurs -
        // both
        // cannot happen due to response commit:
        if (StringUtils.hasText(unauthorizedUrl)) {
          WebUtils.issueRedirect(request, response, unauthorizedUrl);
        } else {
          WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
      }
    }
    return false;
  }
}
