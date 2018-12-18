package com.fl.sp.user.controller;

import com.fl.sp.common.CommonHelp;
import com.fl.sp.common.ImgUtil;
import com.fl.sp.common.TypeUtils;
import com.fl.sp.common.controller.BaseController;
import com.fl.sp.model.PsUser;
import com.fl.sp.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;

@Controller
@RequestMapping(value="/manager")
public class ManagerController extends BaseController {
    @Resource
    private UserService userService;

    @Resource
    private HttpServletRequest request;
    @Value("${file.uploadFolder}")
    private String uploadFolder;
    /**
     * 用户列表页面
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String List() {
        return "user/list";
    }
    /**
     * 添加用户
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String Add(PsUser model, MultipartFile picfile) {
        if (picfile != null) {
            String newname = picfile.getOriginalFilename();
            if (picfile.getSize() > 0 && TypeUtils.isImage(newname)) {
                try {
                    String fguid = CommonHelp.getUuid();
                    String newfilename = fguid + CommonHelp.getHZMDot(newname);
                    // 文件保存路径
                    String xdlj = "/upload/userlogo/" + fguid + "/";
                    //String jdlj = request.getSession().getServletContext().getRealPath(xdlj);
                    String jdlj = uploadFolder+xdlj;
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
        } else {
            model.setLogo("/upload/userlogo/default.png");
        }
        return userService.insert(model);
    }

    /**
     * 验证用户名是否存在
     *
     * @param loginname
     * @return
     */
    @RequestMapping(value = "/validate")
    @ResponseBody
    public String validate(String loginname) {
        return userService.validate(loginname);
    }

    /**
     * 验证用户名是否存在
     *
     * @param loginname
     * @return
     */
    @RequestMapping(value = "/validate2")
    @ResponseBody
    public String validate2(String loginname,String pguid) {
        return userService.validate2(loginname,pguid);
    }

    /**
     * 获取用户信息列表
     *
     * @param currentPage
     * @param model
     * @return
     */
    @RequestMapping(value = "/data", method = RequestMethod.POST)
    @ResponseBody
    public String getData(int currentPage, int pagesize, PsUser model) {
        return userService.getData(currentPage, pagesize, model);
    }
    /**
     * 删除管理员帐号
     *
     * @param pguid
     * @return
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public String delete(String pguid) {
        try {
            int rows = userService.delete(pguid);
            if (rows > 0) {
                return "1";
            } else {
                return "0";
            }
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
}
