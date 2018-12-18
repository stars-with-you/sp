package com.fl.sp.mapper;

import java.util.List;

import com.fl.sp.model.AppNewsinfo;

public interface AppNewsinfoMapper {
	List<AppNewsinfo> selectList(AppNewsinfo model);

	AppNewsinfo selectSingle(String nide);

	int deleteByPrimaryKey(String mid);

	int insert(AppNewsinfo model);

	int update(AppNewsinfo model);
}