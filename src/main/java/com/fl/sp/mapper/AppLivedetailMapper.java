package com.fl.sp.mapper;

import com.fl.sp.model.AppLivedetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppLivedetailMapper {
    List<AppLivedetail> selectList(AppLivedetail record);

    List<AppLivedetail> selectListFile(@Param("endnum") int endnum, @Param("lguid") String lguid, @Param("startnum") int startnum, @Param("isfb") String isfb, @Param("bq") String bq);

    AppLivedetail selectSingle(String dguid);

    int insert(AppLivedetail record);

    int update(AppLivedetail record);

    int delete(String dguid);

    int deleteByLguid(String lguid);
}