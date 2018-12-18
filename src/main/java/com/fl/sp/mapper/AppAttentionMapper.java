package com.fl.sp.mapper;

import com.fl.sp.model.AppAttention;
import org.apache.ibatis.annotations.Param;

public interface AppAttentionMapper {
    int insert(AppAttention record);

    int delete(@Param("pguid") String pguid, @Param("lguid") String lguid);

    AppAttention selectSingle(@Param("pguid") String pguid, @Param("lguid") String lguid);
}