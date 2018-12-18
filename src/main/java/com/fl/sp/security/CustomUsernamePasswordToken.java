package com.fl.sp.security;

import org.apache.shiro.authc.UsernamePasswordToken;

public class CustomUsernamePasswordToken extends UsernamePasswordToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 用于存储用户输入的校验码
	private String validcode;

	private String logintype;

	public CustomUsernamePasswordToken() {
	}

	public CustomUsernamePasswordToken(String username, String password, boolean rememberMe, String host,
			String validCode, String loginType) {
		// 调用父类的构造函数
		super(username, password, rememberMe, host);
		this.validcode = validCode;
		this.logintype = loginType;
	}

	public String getValidcode() {
		return validcode;
	}

	public void setValidcode(String validcode) {
		this.validcode = validcode;
	}

	public String getLogintype() {
		return logintype;
	}

	public void setLogintype(String logintype) {
		this.logintype = logintype;
	}

}
