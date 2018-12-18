package com.fl.sp.model;

import java.util.Date;

public class AppBuy {
    private String bguid;

    private String pguid;

    private String tguid;

    private Date btime;

    public String getBguid() {
        return bguid;
    }

    public void setBguid(String bguid) {
        this.bguid = bguid == null ? null : bguid.trim();
    }

    public String getPguid() {
        return pguid;
    }

    public void setPguid(String pguid) {
        this.pguid = pguid == null ? null : pguid.trim();
    }

    public String getTguid() {
        return tguid;
    }

    public void setTguid(String tguid) {
        this.tguid = tguid == null ? null : tguid.trim();
    }

    public Date getBtime() {
        return btime;
    }

    public void setBtime(Date btime) {
        this.btime = btime;
    }
}