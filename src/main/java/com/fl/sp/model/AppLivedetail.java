package com.fl.sp.model;

import java.util.Date;

public class AppLivedetail {
    private String dguid;
    private String lguid;
    private String description;
    private Date createtime;
    private Date updatetime;
    private String pguid;
    private String isfb;
    private String iszd;
    private String bq;

    public AppLivedetail() {
    }

    public AppLivedetail(String dguid, String lguid, String description, Date createtime, Date updatetime, String pguid, String isfb, String iszd, String bq) {
        this.dguid = dguid;
        this.lguid = lguid;
        this.description = description;
        this.createtime = createtime;
        this.updatetime = updatetime;
        this.pguid = pguid;
        this.isfb = isfb;
        this.iszd = iszd;
        this.bq = bq;
    }

    public String getDguid() {
        return dguid;
    }

    public void setDguid(String dguid) {
        this.dguid = dguid == null ? null : dguid.trim();
    }

    public String getLguid() {
        return lguid;
    }

    public void setLguid(String lguid) {
        this.lguid = lguid == null ? null : lguid.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getPguid() {
        return pguid;
    }

    public void setPguid(String pguid) {
        this.pguid = pguid == null ? null : pguid.trim();
    }

    public String getIsfb() {
        return isfb;
    }

    public void setIsfb(String isfb) {
        this.isfb = isfb;
    }

    public String getIszd() {
        return iszd;
    }

    public void setIszd(String iszd) {
        this.iszd = iszd;
    }

    public String getBq() {
        return bq;
    }

    public void setBq(String bq) {
        this.bq = bq;
    }
}