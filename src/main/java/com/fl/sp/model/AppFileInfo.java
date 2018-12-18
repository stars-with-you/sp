/*
  Created: 方磊
  Date: 2017年8月9日  下午5:46:48

*/
package com.fl.sp.model;

public class AppFileInfo {

	private String cata;// 0:文件 1:文件夹
	private String filename;
	private String path;
	private String abpath;// 绝对路径

	public AppFileInfo() {
	}

	public AppFileInfo(String cata, String filename, String path, String abpath) {
		super();
		this.cata = cata;
		this.filename = filename;
		this.path = path;
		this.abpath = abpath;
	}

	public String getCata() {
		return cata;
	}

	public void setCata(String cata) {
		this.cata = cata;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getAbpath() {
		return abpath;
	}

	public void setAbpath(String abpath) {
		this.abpath = abpath;
	}

}
