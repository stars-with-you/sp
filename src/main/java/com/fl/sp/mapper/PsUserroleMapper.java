package com.fl.sp.mapper;

import java.util.List;

import com.fl.sp.model.PsUserrole;

public interface PsUserroleMapper {
	int DelByRid(String pguid);

	int AddUserRole(List<PsUserrole> model);
}