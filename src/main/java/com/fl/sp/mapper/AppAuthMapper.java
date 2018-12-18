package com.fl.sp.mapper;

import com.fl.sp.model.AppAuth;
import com.fl.sp.model.AppComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppAuthMapper {
    int insert(AppAuth model);

    int insertMap(List<AppAuth> list);

    int deleteByAuguid(String auguid);

    List<AppAuth> selectList(AppAuth model);
}