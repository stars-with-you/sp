package com.fl.sp.mapper;

import java.util.List;

import com.fl.sp.model.PsRolepermission;

public interface PsRolepermissionMapper {
	int DelByRid(String rid);

	int AddRolePerm(List<PsRolepermission> list);
}