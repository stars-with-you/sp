package com.fl.sp.mapper;

import com.fl.sp.model.AppLive;
import com.fl.sp.model.AppLiveSwy;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppLiveMapper {
    List<AppLive> selectList(AppLive record);

    List<AppLive> selectListWX(AppLive record);

    List<AppLive> selectListWXSQ(AppLive record);

    List<AppLive> selectListWXGZ(AppLive record);

    List<AppLive> selectAuth(@Param("lguid") String lguid, @Param("pguid") String pguid);

    List<AppLiveSwy> getswy(@Param("istj") String istj, @Param("endnum") int endnum, @Param("startnum") int startnum);

    int insert(AppLive record);

    AppLive selectSingle(String lguid);

    int update(AppLive record);

    int updateZan(String lguid);

    int updateAccess(String lguid);

    int delete(String lguid);
}