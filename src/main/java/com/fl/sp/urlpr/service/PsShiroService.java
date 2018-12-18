package com.fl.sp.urlpr.service;

import com.fl.sp.model.PsShiro;

public interface PsShiroService {

    public String getData(int currentPage, int pagesize, PsShiro model);

    public String Add(PsShiro model) ;

    /**
     * 设置sguid为无效
     *
     * @param sguid
     * @return
     */
    public String Del(String sguid);

    /**
     * 获取一条url权限拦截
     *
     * @param sguid
     * @return
     */
    public String GetSingle(String sguid);

    public String UpdateBySguid(PsShiro model);
}