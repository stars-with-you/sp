package com.fl.sp.user.controller;

import com.fl.sp.common.CommonHelp;
import com.fl.sp.common.ImgUtil;
import com.fl.sp.common.TypeUtils;
import com.fl.sp.common.controller.BaseController;
import com.fl.sp.model.PsUser;
import com.fl.sp.user.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;

@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {

  @Resource private UserService userService;

  @Resource private HttpServletRequest request;
    @Value("${file.uploadFolder}")
    private String uploadFolder;
  /**
   * 登录页面
   *
   * @return
   */
  @RequestMapping(value = "/login")
  public String Login() {
    return "user/login";
  }

  /**
   * 登录操作
   *
   * @param loginname
   * @param loginpwd
   * @param code
   * @return
   */
  @RequestMapping(value = "/startlogin", method = RequestMethod.POST)
  @ResponseBody
  public String StartLogin(String loginname, String loginpwd, String code) {
    return userService.StartLogin(loginname, loginpwd, code);
  }
  /**
   * react 登录操作
   *
   * @param loginname
   * @param loginpwd
   * @param code
   * @return
   */
  @RequestMapping(value = "/startloginreact", method = RequestMethod.POST)
  @ResponseBody
  public String StartLoginReact(String loginname, String loginpwd, String code) {
    return userService.StartLoginReact(loginname, loginpwd, code);
  }

  /**
   * 后台个人中心
   *
   * @return
   */
  @RequestMapping(value = "/detail")
  public String Detail() {
    return "user/detail";
  }

  /**
   * 修改用户信息页面
   *
   * @param pguid
   * @return
   */
  @RequestMapping(value = "/getsingle")
  @ResponseBody
  public String getsingle(String pguid) {
    return userService.selectSingle(pguid);
  }

  /**
   * 修改用户信息
   *
   * @param model
   * @return
   */
  @RequestMapping(value = "/update", method = RequestMethod.POST)
  @ResponseBody
  public String Update(PsUser model, MultipartFile picfile) {
    if (picfile != null) {
      String newname = picfile.getOriginalFilename();
      if (picfile.getSize() > 0 && TypeUtils.isImage(newname)) {
        try {
          String fguid = CommonHelp.getUuid();
          String newfilename = fguid + CommonHelp.getHZMDot(newname);
          // 文件保存路径
          String xdlj = "/upload/userlogo/" + fguid + "/";
          // String jdlj = request.getSession().getServletContext().getRealPath(xdlj);
          String jdlj = uploadFolder + xdlj;
          System.out.println(jdlj);
          File uploadFile = new File(jdlj);
          // 判断文件是否上传，如果上传的话将会创建该目录
          if (!uploadFile.exists()) {
            uploadFile.mkdirs(); // 创建该目录
          }
          ImgUtil imgUtil = new ImgUtil();
          imgUtil.ThumbnailsImgMultipartFile(1280, picfile, jdlj + newfilename);
          model.setLogo(xdlj + newfilename);
        } catch (Exception e) {
          e.printStackTrace();
          model.setLogo("/upload/userlogo/default.png");
        }
      }
      }

    model.setUpdatetime(new Date());
    String rst = userService.update(model);
    if (rst.equals("1")) {
      PsUser user = (PsUser) request.getSession().getAttribute("user");
      String pguid = user.getPguid();
      String loginname = model.getLoginname();
      String mString = model.getPguid();
      if (pguid.equals(mString)) {
        request.getSession().setAttribute("user", userService.getSingleByLoginname(loginname));
      }
    }
    return rst;
  }

  @RequestMapping(value = "/validate2")
  @ResponseBody
  public String validate2(String loginname, String pguid) {
    return userService.validate2(loginname, pguid);
  }

  /**
   * 修改密码
   *
   * @param model
   * @return
   */
  @RequestMapping(value = "/upwd", method = RequestMethod.POST)
  @ResponseBody
  public String upwd(PsUser model) {
    try {
      int rows = userService.upwd(model);
      if (rows == 1) {
        return "1";
      } else {
        return "修改失败";
      }
    } catch (Exception ex) {
      return ex.getMessage();
    }
  }

  @RequestMapping(value = "/exit")
  public String Exit() {
    SecurityUtils.getSubject().logout();
    return "redirect:/user/login";
  }
}
