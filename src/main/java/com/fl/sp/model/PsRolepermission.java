package com.fl.sp.model;

public class PsRolepermission {
	private String rcode;

	private String pid;

	public String getRcode() {
		return rcode;
	}

	public PsRolepermission() {
	}

	public PsRolepermission(String rcode, String pid) {
		super();
		this.rcode = rcode;
		this.pid = pid;
	}

	public void setRcode(String rcode) {
		this.rcode = rcode == null ? null : rcode.trim();
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid == null ? null : pid.trim();
	}
}