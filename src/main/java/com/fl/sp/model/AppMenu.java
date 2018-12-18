package com.fl.sp.model;

import java.math.BigDecimal;

public class AppMenu {
  private String mid;

  private String cata;

  private String menucode;

  private String menuname;

  private String parentcode;

  private String url;

  private String iconname;

  private BigDecimal sort;
  private String permission;

  public AppMenu() {}

  public AppMenu(String mid, String cata, String menucode, String menuname, String parentcode, String url, String iconname, BigDecimal sort, String permission) {
    this.mid = mid;
    this.cata = cata;
    this.menucode = menucode;
    this.menuname = menuname;
    this.parentcode = parentcode;
    this.url = url;
    this.iconname = iconname;
    this.sort = sort;
    this.permission = permission;
  }

  public String getMid() {
    return mid;
  }

  public void setMid(String mid) {
    this.mid = mid;
  }

  public String getCata() {
    return cata;
  }

  public void setCata(String cata) {
    this.cata = cata;
  }

  public String getMenucode() {
    return menucode;
  }

  public void setMenucode(String menucode) {
    this.menucode = menucode;
  }

  public String getMenuname() {
    return menuname;
  }

  public void setMenuname(String menuname) {
    this.menuname = menuname;
  }

  public String getParentcode() {
    return parentcode;
  }

  public void setParentcode(String parentcode) {
    this.parentcode = parentcode;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getIconname() {
    return iconname;
  }

  public void setIconname(String iconname) {
    this.iconname = iconname;
  }

  public BigDecimal getSort() {
    return sort;
  }

  public void setSort(BigDecimal sort) {
    this.sort = sort;
  }

  public String getPermission() {
    return permission;
  }

  public void setPermission(String permission) {
    this.permission = permission;
  }
}
