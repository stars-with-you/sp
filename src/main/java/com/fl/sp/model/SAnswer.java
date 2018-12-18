package com.fl.sp.model;

public class SAnswer {
    private String aid;
    private String qid;
    private String atext;
    private String avalue;
    private String asort;
    private String sid;

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid == null ? null : aid.trim();
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid == null ? null : qid.trim();
    }

    public String getAtext() {
        return atext;
    }

    public void setAtext(String atext) {
        this.atext = atext == null ? null : atext.trim();
    }

    public String getAvalue() {
        return avalue;
    }

    public void setAvalue(String avalue) {
        this.avalue = avalue == null ? null : avalue.trim();
    }

    public String getAsort() {
        return asort;
    }

    public void setAsort(String asort) {
        this.asort = asort == null ? null : asort.trim();
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}