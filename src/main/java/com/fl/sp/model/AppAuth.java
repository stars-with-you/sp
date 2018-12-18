package com.fl.sp.model;

import java.util.Date;

public class AppAuth {
    private String auguid;

    private String lguid;

    private String pguid;

    private String cata;

    private Date sqtime;

    public String getAuguid() {
        return auguid;
    }

    public void setAuguid(String auguid) {
        this.auguid = auguid == null ? null : auguid.trim();
    }

    public String getLguid() {
        return lguid;
    }

    public void setLguid(String lguid) {
        this.lguid = lguid == null ? null : lguid.trim();
    }

    public String getPguid() {
        return pguid;
    }

    public void setPguid(String pguid) {
        this.pguid = pguid == null ? null : pguid.trim();
    }

    public String getCata() {
        return cata;
    }

    public void setCata(String cata) {
        this.cata = cata == null ? null : cata.trim();
    }

    public Date getSqtime() {
        return sqtime;
    }

    public void setSqtime(Date sqtime) {
        this.sqtime = sqtime;
    }
}