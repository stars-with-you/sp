package com.fl.sp.mapper;

import com.fl.sp.model.AppLiveattach;
import com.fl.sp.model.AppLivedetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppLiveattachMapper {
    List<AppLiveattach> selectList(AppLiveattach record);

    List<AppLiveattach> selectListByLguid(String lguid);

    int insert(List<AppLiveattach> list);

    AppLiveattach selectSingle(String aguid);

    int update(AppLiveattach record);

    int delete(String aguid);

    int deleteByDguid(String dguid);
    int deleteByLguid(String lguid);
}