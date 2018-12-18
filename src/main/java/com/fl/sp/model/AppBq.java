package com.fl.sp.model;

public class AppBq {
    private String bguid;

    private String bq;

    private String lguid;

    public String getBguid() {
        return bguid;
    }

    public void setBguid(String bguid) {
        this.bguid = bguid == null ? null : bguid.trim();
    }

    public String getBq() {
        return bq;
    }

    public void setBq(String bq) {
        this.bq = bq == null ? null : bq.trim();
    }

    public String getLguid() {
        return lguid;
    }

    public void setLguid(String lguid) {
        this.lguid = lguid == null ? null : lguid.trim();
    }
}