package com.fl.sp.model;

public class PsUserrole {
	private String pguid;

	private String rcode;

	public PsUserrole() {
	}

	public PsUserrole(String pguid, String rcode) {
		super();
		this.pguid = pguid;
		this.rcode = rcode;
	}

	public String getPguid() {
		return pguid;
	}

	public void setPguid(String pguid) {
		this.pguid = pguid;
	}

	public String getRcode() {
		return rcode;
	}

	public void setRcode(String rcode) {
		this.rcode = rcode;
	}
}