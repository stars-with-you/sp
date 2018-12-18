package com.fl.sp.model;

import java.util.Date;

public class AppComment {
    private String cguid;

    private String lguid;

    private String nickname;

    private String openid;

    private String ip;

    private String ipplace;

    private String comment;

    private String status;

    private Date createtime;

    private Integer zan;

    private String hfguid;

    private Integer cata;

    private String shrguid;

    private Date shtime;
    private String headurl;
    public AppComment() {
    }

    public AppComment(String cguid, String lguid, String nickname, String openid, String ip, String ipplace, String comment, String status, Date createtime, Integer zan, String hfguid, Integer cata, String shrguid, Date shtime,String headurl) {
        this.cguid = cguid;
        this.lguid = lguid;
        this.nickname = nickname;
        this.openid = openid;
        this.ip = ip;
        this.ipplace = ipplace;
        this.comment = comment;
        this.status = status;
        this.createtime = createtime;
        this.zan = zan;
        this.hfguid = hfguid;
        this.cata = cata;
        this.shrguid = shrguid;
        this.shtime = shtime;
        this.headurl=headurl;
    }

    public String getCguid() {
        return cguid;
    }

    public void setCguid(String cguid) {
        this.cguid = cguid == null ? null : cguid.trim();
    }

    public String getLguid() {
        return lguid;
    }

    public void setLguid(String lguid) {
        this.lguid = lguid == null ? null : lguid.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getIpplace() {
        return ipplace;
    }

    public void setIpplace(String ipplace) {
        this.ipplace = ipplace == null ? null : ipplace.trim();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getZan() {
        return zan;
    }

    public void setZan(Integer zan) {
        this.zan = zan;
    }

    public String getHfguid() {
        return hfguid;
    }

    public void setHfguid(String hfguid) {
        this.hfguid = hfguid == null ? null : hfguid.trim();
    }

    public Integer getCata() {
        return cata;
    }

    public void setCata(Integer cata) {
        this.cata = cata;
    }

    public String getShrguid() {
        return shrguid;
    }

    public void setShrguid(String shrguid) {
        this.shrguid = shrguid == null ? null : shrguid.trim();
    }

    public Date getShtime() {
        return shtime;
    }

    public void setShtime(Date shtime) {
        this.shtime = shtime;
    }

    public String getHeadurl() {
        return headurl;
    }

    public void setHeadurl(String headurl) {
        this.headurl = headurl;
    }
}