package com.fl.sp.security;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

public class MyPaswordValid extends SimpleCredentialsMatcher {

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		// TODO Auto-generated method stub
		CustomUsernamePasswordToken mytoken = (CustomUsernamePasswordToken) token;
		String pwd = new String(mytoken.getPassword());
		String loginname = (String) mytoken.getPrincipal();

		return super.doCredentialsMatch(token, info);

	}

}
