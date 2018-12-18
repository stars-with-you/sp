package com.fl.sp.mapper;

import java.util.List;

import com.fl.sp.model.PsPermission;

public interface PsPermissionMapper {
	List<PsPermission> GetListAll();

	List<PsPermission> GetListOrPname(PsPermission model);

	List<PsPermission> getPermissionsByLoginname(String loginname);

	PsPermission GetSingle(String pid);

	List<PsPermission> GetListByRcode(String rcode);

	int Add(PsPermission model);

	int UpdateByPid(PsPermission model);

	int DelByPid(String pid);
}