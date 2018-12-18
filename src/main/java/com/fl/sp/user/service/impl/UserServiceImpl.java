package com.fl.sp.user.service.impl;

import com.fl.sp.common.CommonHelp;
import com.fl.sp.common.EncodeUtils;
import com.fl.sp.common.TypeUtils;
import com.fl.sp.mapper.*;
import com.fl.sp.model.PsPermission;
import com.fl.sp.model.PsRole;
import com.fl.sp.model.PsUser;
import com.fl.sp.security.CustomUsernamePasswordToken;
import com.fl.sp.user.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
  @Resource private PsUserMapper userMapper;
  @Resource private PsRoleMapper roleMapper;
  @Resource private PsPermissionMapper permissionMapper;
  @Resource private PsUserroleMapper userrolemapper;

  @Resource private AppMenuMapper appMenuMapper;
  private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class.getName());
  /** 获取一条用户信息 */
  public PsUser getSingleByLoginname(String loginname) {
    return userMapper.getSingleByLoginname(loginname);
  }

  public PsUser selectByOpenid(String openid) {
    return userMapper.getSingleByOpenid(openid);
  }

  public String selectSingle(String pguid) {
    return CommonHelp.MyToJSONString(userMapper.getSingleByPguid(pguid));
  }

  public List<PsRole> getRoles(String pguid) {
    return roleMapper.GetListByLoginname(pguid);
  }

  public List<PsPermission> getPermissions(String pguid) {
    return permissionMapper.getPermissionsByLoginname(pguid);
  }

  public String insert(PsUser model) {
    model.setLoginpwd(EncodeUtils.getMD5(model.getLoginpwd()));
    model.setAddtime(new Date());
    // 生成用户唯一键
    model.setPguid(CommonHelp.getUuid());
    // 空的话，设置成普通用户
    String cata = model.getCata();
    if (TypeUtils.isEmpty(cata)) {
      model.setCata("1");
    }
    try {
      model.setUpdatetime(CommonHelp.getNowTime());
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    try {
      int rows = userMapper.insert(model);
      if (rows == 1) {
        return "1";
      } else {
        return "保存失败";
      }
    } catch (Exception ex) {
      return ex.getMessage();
    }
  }

  /** 修改密码 */
  public int upwd(PsUser model) {
    String pwd = model.getLoginpwd();
    model.setLoginpwd(EncodeUtils.getMD5(pwd));
    int rows = userMapper.updateByPguid(model);
    return rows;
  }

  public int delete(String pguid) {
    userrolemapper.DelByRid(pguid);
    return userMapper.deleteByPguid(pguid);
  }

  public String getData(int currentPage, int pagesize, PsUser model) {
    if (!TypeUtils.isNumeric(String.valueOf(currentPage))) {
      currentPage = 1;
    }
    if (!TypeUtils.isNumeric(String.valueOf(pagesize))) {
      pagesize = 10;
    }
    PageHelper.startPage(currentPage, pagesize);
    List<PsUser> list = userMapper.selectOrLoginname(model);
    PageInfo<PsUser> pageinfo = new PageInfo<PsUser>(list);
    int totalcount = (int) pageinfo.getTotal();
    String json;
    try {
      json =
          "{\"code\": \"0\", \"msg\": \"\",\"count\": \""
              + totalcount
              + "\",\"data\":"
              + CommonHelp.MyToJSONString(list)
              + "}";
    } catch (Exception e) {
      json = "{\"code\": \"1\", \"msg\": \"\",\"count\":0,data:[]}";
    }
    return json;
  }

  public String StartLogin(String loginname, String loginpwd, String code) {
    String logintype = "";
    // Properties properties = CommonHelp.getProperty("config/shiro/login.properties");
    // logintype = CommonHelp.getPropertyKey(properties, "login.usermanager");
    CustomUsernamePasswordToken token =
        new CustomUsernamePasswordToken(
            loginname.trim(), EncodeUtils.getMD5(loginpwd.trim()), false, "", code, logintype);
    org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
    // if (!subject.isAuthenticated()) {
    Session session = subject.getSession();
    try {
      String validatecode = (String) session.getAttribute("validatecode");
      // String validatecode = redisService.get("validatecode");
      if (!code.toUpperCase().trim().equals(validatecode.toUpperCase())) {
        return "2";
      }
      try {
        subject.login(token);
        return "1";
      } catch (Exception ex) {
        log.error("登录失败", ex);
        return "0";
      }
    } catch (Exception ex) {
      log.error("", ex);
      return "3";
    }
    // }
    // return "1";
  }

  public String StartLoginReact(String loginname, String loginpwd, String code) {
    String logintype = "";
    CustomUsernamePasswordToken token =
        new CustomUsernamePasswordToken(
            loginname.trim(), EncodeUtils.getMD5(loginpwd.trim()), false, "", code, logintype);
    org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
    Session session = subject.getSession();
    if (!subject.isAuthenticated()) {
      try {
        String validatecode = (String) session.getAttribute("validatecode");
        if (!code.toUpperCase().trim().equals(validatecode.toUpperCase())) {
          return "{\"bz\":\"2\",\"msg\":\"验证码错误\"}";
        }
          subject.login(token);
           PsUser user=(PsUser) session.getAttribute("user");
           String userJson=CommonHelp.MyToJSONString(user);
           System.out.println("1:"+userJson);
          return "{\"bz\":\"1\",\"msg\":"+userJson+"}";
      } catch (Exception ex) {
          return "{\"bz\":\"2\",\"msg\":\"登录出现异常！\"}";
      }
    } else {
        PsUser user=(PsUser) session.getAttribute("user");
        String userJson=CommonHelp.MyToJSONString(user);
        System.out.println("2:"+userJson);
        return "{\"bz\":\"1\",\"msg\":"+userJson+"}";
    }
  }
  /**
   * 根据openid进行登录
   *
   * @param openid
   * @return
   */
  public String WXStartLogin(String openid) {
    org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
    //        if(subject.isAuthenticated())//已经登录
    //        {
    //            return "1";
    //        }
    //        else {
    PsUser model = userMapper.getSingleByOpenid(openid);
    if (model != null) {
      String logintype = "";
      // Properties properties = CommonHelp.getProperty("config/shiro/login.properties");
      // logintype = CommonHelp.getPropertyKey(properties, "login.usermanager");
      CustomUsernamePasswordToken token =
          new CustomUsernamePasswordToken(
              model.getLoginname(), model.getLoginpwd(), false, "", "", logintype);
      Session session = subject.getSession();
      try {
        try {
          subject.login(token);
          return "1";
        } catch (Exception ex) {
          log.error("登录失败", ex);
          return "0";
        }
      } catch (Exception ex) {
        log.error("", ex);
        return "3";
      }
    } else {
      return "0";
    }
    //        }
  }
  /**
   * 修改用户基本信息
   *
   * @param model
   * @return
   */
  public String update(PsUser model) {
    try {
      int rows = userMapper.updateByPguid(model);
      if (rows == 1) {
        return "1";
      } else {
        return "修改失败";
      }
    } catch (Exception ex) {
      log.error("用户信息用户失败", ex);
      return ex.getMessage();
    }
  }

  /**
   * 验证用户名是否存在
   *
   * @param loginname
   * @return 存在的话，返回false,验证不通过。
   */
  public String validate(String loginname) {
    try {
      PsUser user = userMapper.getSingleByLoginname(loginname);
      if (user != null) {
        return "false";
      } else {
        return "true";
      }
    } catch (Exception ex) {
      return "false";
    }
  }

  public String validate2(String loginname, String pguid) {
    try {
      PsUser user = userMapper.getValidate(loginname, pguid);
      if (user != null) {
        return "false";
      } else {
        return "true";
      }
    } catch (Exception ex) {
      return "false";
    }
  }
}
