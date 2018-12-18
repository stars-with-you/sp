package com.fl.sp.user.service;

import com.fl.sp.model.PsPermission;
import com.fl.sp.model.PsRole;
import com.fl.sp.model.PsUser;

import java.util.List;

public interface UserService {
    /**
     * 获取一条用户信息
     */
    public PsUser getSingleByLoginname(String pguid);
    public PsUser selectByOpenid(String openid);
    public String selectSingle(String pguid);

    public List<PsRole> getRoles(String loginname);

    public List<PsPermission> getPermissions(String loginname);

    public String insert(PsUser model);

    /**
     * 修改密码
     */
    public int upwd(PsUser model);
    /*
    删除用户
     */

    public int delete(String pguid);

    public String getData(int currentPage, int pagesize, PsUser model);

    public String StartLogin(String loginname, String loginpwd, String code);
    public String StartLoginReact(String loginname, String loginpwd, String code);
    public String WXStartLogin(String openid);
    /**
     * 修改用户基本信息
     *
     * @param model
     * @return
     */
    public String update(PsUser model);

    /**
     * 验证用户名是否存在
     *
     * @param loginname 将要保存的用户名
     * @return 存在的话，返回false,验证不通过。
     */
    public String validate(String loginname);

    public String validate2(String loginname,String pguid);
}
