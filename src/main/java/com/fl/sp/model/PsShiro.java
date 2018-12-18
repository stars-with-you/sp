package com.fl.sp.model;

import java.math.BigDecimal;
import java.util.Date;

public class PsShiro {
	private String sguid;

	private String skey;

	private String svalue;

	private Date saddtime;

	private String sadder;

	private Date supdatetime;

	private String supdater;

	private String isdel;

	private BigDecimal ssx;
	private String sms;

	public String getSguid() {
		return sguid;
	}

	public void setSguid(String sguid) {
		this.sguid = sguid == null ? null : sguid.trim();
	}

	public String getSkey() {
		return skey;
	}

	public void setSkey(String skey) {
		this.skey = skey == null ? null : skey.trim();
	}

	public String getSvalue() {
		return svalue;
	}

	public void setSvalue(String svalue) {
		this.svalue = svalue == null ? null : svalue.trim();
	}

	public Date getSaddtime() {
		return saddtime;
	}

	public void setSaddtime(Date saddtime) {
		this.saddtime = saddtime;
	}

	public String getSadder() {
		return sadder;
	}

	public void setSadder(String sadder) {
		this.sadder = sadder == null ? null : sadder.trim();
	}

	public Date getSupdatetime() {
		return supdatetime;
	}

	public void setSupdatetime(Date supdatetime) {
		this.supdatetime = supdatetime;
	}

	public String getSupdater() {
		return supdater;
	}

	public void setSupdater(String supdater) {
		this.supdater = supdater == null ? null : supdater.trim();
	}

	public String getIsdel() {
		return isdel;
	}

	public void setIsdel(String isdel) {
		this.isdel = isdel == null ? null : isdel.trim();
	}

	public BigDecimal getSsx() {
		return ssx;
	}

	public void setSsx(BigDecimal ssx) {
		this.ssx = ssx;
	}

	public String getSms() {
		return sms;
	}

	public void setSms(String sms) {
		this.sms = sms;
	}

}