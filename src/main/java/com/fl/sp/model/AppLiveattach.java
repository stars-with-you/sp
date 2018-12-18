package com.fl.sp.model;

import java.util.Date;

public class AppLiveattach {
    private String aguid;
    private String dguid;
    private String type;
    private String path;
    private String zoompath;
    private String filename;
    private Date createtime;
    private String lguid;

    public String getLguid() {
        return lguid;
    }

    public void setLguid(String lguid) {
        this.lguid = lguid;
    }

    public String getAguid() {
        return aguid;
    }

    public void setAguid(String aguid) {
        this.aguid = aguid == null ? null : aguid.trim();
    }

    public String getDguid() {
        return dguid;
    }

    public void setDguid(String dguid) {
        this.dguid = dguid == null ? null : dguid.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getZoompath() {
        return zoompath;
    }

    public void setZoompath(String zoompath) {
        this.zoompath = zoompath == null ? null : zoompath.trim();
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename == null ? null : filename.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}