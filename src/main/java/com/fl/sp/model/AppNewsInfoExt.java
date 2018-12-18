/*
  Created: 方磊
  Date: 2018年3月30日  上午9:42:54

*/
package com.fl.sp.model;

public class AppNewsInfoExt extends AppNewsinfo {
	private AppNewsinfo model;
	private String totalcount;

	public AppNewsInfoExt() {
	}

	public AppNewsInfoExt(AppNewsinfo model, String totalcount) {
		super();
		this.model = model;
		this.totalcount = totalcount;
	}

	public AppNewsinfo getModel() {
		return model;
	}

	public void setModel(AppNewsinfo model) {
		this.model = model;
	}

	public String getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(String totalcount) {
		this.totalcount = totalcount;
	}
}
