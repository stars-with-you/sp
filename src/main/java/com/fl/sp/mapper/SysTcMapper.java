package com.fl.sp.mapper;

import com.fl.sp.model.SysTc;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysTcMapper {
    int insert(SysTc record);

    SysTc selectSingle(String tguid);

    List<SysTc> selectList(SysTc model);

    int update(SysTc record);
}