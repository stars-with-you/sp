package com.fl.sp.model;

import java.util.Date;

public class AppNewsinfo {
	private String nid;

	private String title;

	private String cata;

	private String menucode;

	private Date addtime;

	private String addloginname;

	private String adddispname;

	private String ip;

	private String isout;

	private String istop;

	private String iswb;

	private String iswx;

	private String isqq;
	private String isapp;
	private String isdel;// 是否删除
	private Date updatetime;
	private String content;

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid == null ? null : nid.trim();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public String getCata() {
		return cata;
	}

	public void setCata(String cata) {
		this.cata = cata == null ? null : cata.trim();
	}

	public String getMenucode() {
		return menucode;
	}

	public void setMenucode(String menucode) {
		this.menucode = menucode == null ? null : menucode.trim();
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getAddloginname() {
		return addloginname;
	}

	public void setAddloginname(String addloginname) {
		this.addloginname = addloginname == null ? null : addloginname.trim();
	}

	public String getAdddispname() {
		return adddispname;
	}

	public void setAdddispname(String adddispname) {
		this.adddispname = adddispname == null ? null : adddispname.trim();
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip == null ? null : ip.trim();
	}

	public String getIsout() {
		return isout;
	}

	public void setIsout(String isout) {
		this.isout = isout == null ? null : isout.trim();
	}

	public String getIstop() {
		return istop;
	}

	public void setIstop(String istop) {
		this.istop = istop == null ? null : istop.trim();
	}

	public String getIswb() {
		return iswb;
	}

	public void setIswb(String iswb) {
		this.iswb = iswb == null ? null : iswb.trim();
	}

	public String getIswx() {
		return iswx;
	}

	public void setIswx(String iswx) {
		this.iswx = iswx == null ? null : iswx.trim();
	}

	public String getIsqq() {
		return isqq;
	}

	public void setIsqq(String isqq) {
		this.isqq = isqq == null ? null : isqq.trim();
	}

	public String getIsapp() {
		return isapp;
	}

	public void setIsapp(String isapp) {
		this.isapp = isapp == null ? null : isapp.trim();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	public String getIsdel() {
		return isdel;
	}

	public void setIsdel(String isdel) {
		this.isdel = isdel;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

}