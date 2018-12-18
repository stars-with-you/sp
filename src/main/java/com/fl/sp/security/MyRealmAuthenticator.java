/*
  Created: 方磊
  Date: 2017年8月16日  上午11:00:56

*/
package com.fl.sp.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 认证器:绝定使用哪个realm
 * 
 * @author Administrator
 *
 */
public class MyRealmAuthenticator extends ModularRealmAuthenticator {

	@Override
	protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		// 判断getRealms()是否返回为空
		assertRealmsConfigured();
		// 强制转换回自定义的CustomizedToken
		CustomUsernamePasswordToken customizedToken = (CustomUsernamePasswordToken) authenticationToken;
		// 登录类型
		String loginType = customizedToken.getLogintype();
		// 所有Realm
		Collection<Realm> realms = getRealms();
		// 登录类型对应的所有Realm
		Collection<Realm> typeRealms = new ArrayList<Realm>();
		for (Realm realm : realms) {
			if (realm.getName().contains(loginType)) {
				typeRealms.add(realm);
			}
		}
		// 判断是单Realm还是多Realm
		if (typeRealms.size() == 1) {
			return doSingleRealmAuthentication(typeRealms.iterator().next(), customizedToken);
		} else
			return doMultiRealmAuthentication(typeRealms, customizedToken);

	}

}