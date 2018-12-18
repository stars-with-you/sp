package com.fl.sp.mapper;

import com.fl.sp.model.PsUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PsUserMapper {
    int insert(PsUser model);
PsUser login(@Param("loginname") String loginname,@Param("loginpwd") String loginpwd);
    /**
     * 根据用户名获取一条用户信息
     * @param loginname
     * @return
     */
    PsUser getSingleByLoginname(@Param("loginname") String loginname);

    /**
     * 根据pguid获取一条用户信息
     * @param pguid
     * @return
     */
    PsUser getSingleByPguid(String pguid);

    /**
     * 根据openid获取一条用户信息
     * @param openid
     * @return
     */
    PsUser getSingleByOpenid(String openid);

    /**
     * 根据用户名、pguid获取一条用户信息
     * @param loginname
     * @param pguid
     * @return
     */
    PsUser getValidate(@Param("loginname") String loginname, @Param("pguid") String pguid);

    List<PsUser> selectOrLoginname(PsUser model);

    int updateByPguid(PsUser model);
    int updateOpenid(@Param("loginname") String loginname, @Param("openid") String openid);

    int deleteByPguid(String pguid);
}