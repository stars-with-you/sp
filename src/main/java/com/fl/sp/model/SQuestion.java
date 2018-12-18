package com.fl.sp.model;

public class SQuestion {
    private String qid;
    private String qindex;
    private String qtitle;
    private String qcata;
    private String sid;

    public SQuestion() {
    }

    public SQuestion(String qid, String qindex, String qtitle, String qcata, String sid) {
        this.qid = qid;
        this.qindex = qindex;
        this.qtitle = qtitle;
        this.qcata = qcata;
        this.sid = sid;
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid == null ? null : qid.trim();
    }

    public String getQindex() {
        return qindex;
    }

    public void setQindex(String qindex) {
        this.qindex = qindex == null ? null : qindex.trim();
    }

    public String getQtitle() {
        return qtitle;
    }

    public void setQtitle(String qtitle) {
        this.qtitle = qtitle == null ? null : qtitle.trim();
    }

    public String getQcata() {
        return qcata;
    }

    public void setQcata(String qcata) {
        this.qcata = qcata == null ? null : qcata.trim();
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid == null ? null : sid.trim();
    }
}