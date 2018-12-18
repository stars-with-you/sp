package com.fl.sp.model;

import java.util.Date;

public class AppLivelogo {
    private String logoid;

    private String cata;

    private String oriname;

    private String defaultpic;

    private String zoompath;

    private Date addtime;

    private String lguid;

    public String getLogoid() {
        return logoid;
    }

    public void setLogoid(String logoid) {
        this.logoid = logoid == null ? null : logoid.trim();
    }

    public String getCata() {
        return cata;
    }

    public void setCata(String cata) {
        this.cata = cata == null ? null : cata.trim();
    }

    public String getOriname() {
        return oriname;
    }

    public void setOriname(String oriname) {
        this.oriname = oriname == null ? null : oriname.trim();
    }

    public String getDefaultpic() {
        return defaultpic;
    }

    public void setDefaultpic(String defaultpic) {
        this.defaultpic = defaultpic == null ? null : defaultpic.trim();
    }

    public String getZoompath() {
        return zoompath;
    }

    public void setZoompath(String zoompath) {
        this.zoompath = zoompath == null ? null : zoompath.trim();
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public String getLguid() {
        return lguid;
    }

    public void setLguid(String lguid) {
        this.lguid = lguid == null ? null : lguid.trim();
    }
}