package com.fl.sp.model;

import java.util.Date;

public class AppAttention {
    private String gzid;

    private String pguid;

    private String displayname;

    private String lguid;

    private String title;

    private Date addtime;

    public String getGzid() {
        return gzid;
    }

    public void setGzid(String gzid) {
        this.gzid = gzid == null ? null : gzid.trim();
    }

    public String getPguid() {
        return pguid;
    }

    public void setPguid(String pguid) {
        this.pguid = pguid == null ? null : pguid.trim();
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname == null ? null : displayname.trim();
    }

    public String getLguid() {
        return lguid;
    }

    public void setLguid(String lguid) {
        this.lguid = lguid == null ? null : lguid.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }
}