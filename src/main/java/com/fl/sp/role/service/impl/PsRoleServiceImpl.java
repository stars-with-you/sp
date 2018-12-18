/*
  Created: 方磊
  Date: 2017年7月31日  下午3:03:13

*/
package com.fl.sp.role.service.impl;

import com.fl.sp.common.CommonHelp;
import com.fl.sp.common.TypeUtils;
import com.fl.sp.mapper.PsRoleMapper;
import com.fl.sp.mapper.PsRolepermissionMapper;
import com.fl.sp.mapper.PsUserroleMapper;
import com.fl.sp.model.PsRole;
import com.fl.sp.model.PsRolepermission;
import com.fl.sp.model.PsUserrole;
import com.fl.sp.role.service.PsRoleService;
import com.fl.sp.security.realm.UserRealm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PsRoleServiceImpl implements PsRoleService {
    @Resource
    private PsRoleMapper rolemapper;
    @Resource
    private PsRolepermissionMapper rolepermmapper;
    @Resource
    private PsUserroleMapper userrolemapper;
    @Resource
    private UserRealm userRealm;
    private static final Logger logger = LoggerFactory.getLogger(PsRoleServiceImpl.class.getName());

    /**
     * 添加一条角色信息
     *
     * @param md
     * @return
     */
    public String add(PsRole md) {
        md.setAddtime(new Date());
        md.setUpdatetime(new Date());
        md.setYxbz("1");
        try {
            int rows = rolemapper.Add(md);
            if (rows == 1) {
                return "1";
            } else {
                return "角色添加失败";
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("角色添加失败：", e);
            return "角色添加失败：" + e.getMessage();
        }
    }

    /**
     * 获取权限列表
     *
     * @param pn(当前页)
     * @param ps(页面显示条数)
     * @param model(查询条件)
     * @return
     */
    public String GetList(int pn, int ps, PsRole model) {
        if (!TypeUtils.isNumeric(String.valueOf(pn))) {
            pn = 1;
        }
        if (!TypeUtils.isNumeric(String.valueOf(ps))) {
            ps = 10;
        }
        List<PsRole> list = null;
        String result = "[]";
        int totalcount = 0;
        try {
           // PageHelper.startPage(pn, ps);
            list = rolemapper.GetList(model);
           // PageInfo<PsRole> pageinfo = new PageInfo<PsRole>(list.ftl);
           // totalcount = (int) pageinfo.getTotal();
            totalcount=list.size();
        } catch (Exception e1) {
            logger.error("角色列表获取", e1);
        }
        String json;
        try {
            json = "{\"code\": \"0\", \"msg\": \"\",\"count\": \"" + totalcount + "\",\"data\":"
                    + CommonHelp.MyToJSONString(list) + "}";
        } catch (Exception e) {
            json = "{\"code\": \"1\", \"msg\": \"\",\"count\":0,data:[]}";
        }
        return json;
    }

    /**
     * 修改一条角色信息
     *
     * @param md
     * @return
     */
    public String UpdateByRcode(PsRole md) {
        try {
            md.setUpdatetime(new Date());
            int rows = rolemapper.UpdateByRcode(md);
            if (rows == 1) {
                return "1";
            } else {
                return "角色修改失败";
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("角色修改失败：", e);
            return "角色修改失败：" + e.getMessage();
        }
    }

    /**
     * 根据用户名获取所有角色信息
     *
     * @param pguid
     * @return
     */
    public String GetListByLoginname(String pguid) {
        List<PsRole> list = rolemapper.GetListByLoginname(pguid);
        String result = "";
        if (list != null && list.size() > 0) {
            try {
                result = CommonHelp.MyToJSONString(list);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                logger.error("单条角色转成json时出错：", e);
            }
        }
        return result;
    }

    /**
     * 获取一条角色信息
     *
     * @param rcode(角色代码)
     * @return
     */
    public String GetSingle(String rcode) {
        PsRole mv = rolemapper.GetSingle(rcode);
        String result = "";
        if (mv != null) {
            try {
                result = CommonHelp.MyToJSONString(mv);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                logger.error("单条角色转成json时出错：", e);
            }
        }
        return result;
    }

    /**
     * 根据角色代码删除一条信息,并且删除角色所拥有的权限
     *
     * @param rcode(角色代码)
     * @return
     */
    public String DelByRcode(String rcode) {
        rolepermmapper.DelByRid(rcode);
        int rows;
        try {
            rows = rolemapper.DelByRcode(rcode);
            if (rows == 1) {
                userRealm.clearCache();
                return "1";
            } else {
                return "角色删除失败";
            }
        } catch (Exception e) {
            logger.error("角色删除失败", e);
            return "角色删除失败:" + e.getMessage();
        }
    }

    /**
     * 添加修改角色的权限
     *
     * @param rcode(角色代码)
     * @param pidstr(权限字符串)
     * @return
     */
    public String AddRolePerm(String rcode, String pidstr) {
        rolepermmapper.DelByRid(rcode);
        if (!TypeUtils.isEmpty(pidstr)) {
            pidstr = pidstr.substring(0, pidstr.length() - 1);
            String[] arr = pidstr.split(";");
            List<PsRolepermission> list = new ArrayList<PsRolepermission>();
            for (String item : arr) {
                list.add(new PsRolepermission(rcode, item));
            }
            rolepermmapper.AddRolePerm(list);
            userRealm.clearCache();
        }
        return "1";
    }

    /**
     * 添加修改用户的角色
     *
     * @param pguid(用户名)
     * @param rcodestr(角色字符串)
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String AddUserRole(String pguid, String rcodestr) {
        String rString = "";
        userrolemapper.DelByRid(pguid);
        if (!TypeUtils.isEmpty(rcodestr)) {
            rcodestr = rcodestr.substring(0, rcodestr.length() - 1);
            String[] arr = rcodestr.split(";");
            List<PsUserrole> list = new ArrayList<PsUserrole>();
            for (String item : arr) {
                list.add(new PsUserrole(pguid, item));
            }
            userrolemapper.AddUserRole(list);
            userRealm.clearCache();
        }
        rString = "1";
        return rString;
    }
}
