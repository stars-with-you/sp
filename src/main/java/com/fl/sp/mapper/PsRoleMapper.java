package com.fl.sp.mapper;

import java.util.List;

import com.fl.sp.model.PsRole;

public interface PsRoleMapper {
	List<PsRole> GetList(PsRole model);

	List<PsRole> GetListByLoginname(String loginname);

	PsRole GetSingle(String rid);

	int Add(PsRole model);

	int UpdateByRcode(PsRole model);

	int DelByRcode(String rid);

}