package com.fl.sp.mapper;

import com.fl.sp.model.AppMenu;

import java.util.List;

public interface AppMenuMapper {
	List<AppMenu> selectAll(AppMenu model);

	List<AppMenu> selectList(AppMenu model);

	int deleteByPrimaryKey(String mid);

	int insert(AppMenu model);

	int update(AppMenu model);
}