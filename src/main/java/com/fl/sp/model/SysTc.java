package com.fl.sp.model;

import java.math.BigDecimal;
import java.util.Date;

public class SysTc {
    private String tguid;
    private Integer count;
    private Integer yxq;
    private Integer cttime;
    private BigDecimal price;
    private String yxbz;
    private String description;
    private Date ctime;

    public String getTguid() {
        return tguid;
    }

    public void setTguid(String tguid) {
        this.tguid = tguid == null ? null : tguid.trim();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getYxq() {
        return yxq;
    }

    public void setYxq(Integer yxq) {
        this.yxq = yxq;
    }

    public Integer getCttime() {
        return cttime;
    }

    public void setCttime(Integer cttime) {
        this.cttime = cttime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getYxbz() {
        return yxbz;
    }

    public void setYxbz(String yxbz) {
        this.yxbz = yxbz == null ? null : yxbz.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
}