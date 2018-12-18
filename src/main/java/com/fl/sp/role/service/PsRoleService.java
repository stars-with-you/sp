/*
  Created: 方磊
  Date: 2017年8月28日  下午4:25:20

*/
package com.fl.sp.role.service;

import com.fl.sp.model.PsRole;

public interface PsRoleService {
	/**
	 * 添加一条角色信息
	 * 
	 * @param md
	 * @return
	 */
	public String add(PsRole md);

	/**
	 * 获取权限列表
	 * 
	 * @param pn(当前页)
	 * @param ps(页面显示条数)
	 * @param model(查询条件)
	 * @return
	 */
	public String GetList(int pn, int ps, PsRole model);

	/**
	 * 修改一条角色信息
	 * 
	 * @param md
	 * @return
	 */
	public String UpdateByRcode(PsRole md);

	/**
	 * 根据用户名获取所有角色信息
	 * 
	 * @param loginname(用户名)
	 * @return
	 */
	public String GetListByLoginname(String loginname);

	/**
	 * 获取一条角色信息
	 * 
	 * @param rcode(角色代码)
	 * @return
	 */
	public String GetSingle(String rcode);

	/**
	 * 根据角色代码删除一条信息,并且删除角色所拥有的权限
	 * 
	 * @param rcode(角色代码)
	 * @return
	 */
	public String DelByRcode(String rcode);

	/**
	 * 添加修改角色的权限
	 * 
	 * @param rcode(角色代码)
	 * @param pidstr(权限字符串)
	 * @return
	 */
	public String AddRolePerm(String rcode, String pidstr);

	/**
	 * 添加修改用户的角色
	 * 
	 * @param pguid(用户名)
	 * @param rcodestr(角色字符串)
	 * @return
	 */

	public String AddUserRole(String pguid, String rcodestr);

}
