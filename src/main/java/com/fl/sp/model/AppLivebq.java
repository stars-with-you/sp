package com.fl.sp.model;

public class AppLivebq {
    private String bid;
    private String dguid;
    private String bguid;
    private String bq;
    private String lguid;

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid == null ? null : bid.trim();
    }

    public String getDguid() {
        return dguid;
    }

    public void setDguid(String dguid) {
        this.dguid = dguid == null ? null : dguid.trim();
    }

    public String getBguid() {
        return bguid;
    }

    public void setBguid(String bguid) {
        this.bguid = bguid;
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