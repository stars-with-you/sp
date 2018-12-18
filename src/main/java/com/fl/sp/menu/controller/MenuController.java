/*
  Created: 方磊
  Date: 2017年8月3日  上午8:36:46

*/
package com.fl.sp.menu.controller;

import com.alibaba.fastjson.JSON;
import com.fl.sp.common.CommonHelp;
import com.fl.sp.menu.service.AppMenuService;
import com.fl.sp.model.AppMenu;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "/menu")
public class MenuController {

  @Resource private AppMenuService menuService;
  @Resource private HttpServletRequest request;

  /**
   * 展示菜单列表页
   *
   * @return
   */
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public String List() {
    return "manager/menu";
  }

  @RequestMapping(value = "/data", method = RequestMethod.POST)
  @ResponseBody
  public String getData(int currentPage, int pagesize, AppMenu model) {
    try {
      if (currentPage < 1) {
        currentPage = 1;
      }
      return menuService.getData(currentPage, pagesize, model);
    } catch (Exception e) {

      return "";
    }
  }

  @RequestMapping(value = "/getmenu", method = RequestMethod.POST)
  @ResponseBody
  public String getMenu() {
    return menuService.getMenu();
  }

  /**
   * 添加菜单
   *
   * @param model
   * @return 1:添加成功 其他：添加失败
   */
  @RequestMapping(value = "/add", method = RequestMethod.POST)
  @ResponseBody
  public String Add(AppMenu model) {
    model.setMid(CommonHelp.getUuid());
    String parentcode = model.getCata();
    int cata = parentcode.length() / 3 + 1;
    model.setCata(String.valueOf(cata));
    model.setParentcode(parentcode);
    if (model.getUrl().isEmpty()) {
      model.setUrl("#");
    }

    String rst = menuService.Add(model);
    String json = menuService.getMenu();
    List<AppMenu> list = (List<AppMenu>) JSON.parse(json);
    request.getSession().setAttribute("menu", list);
    return rst;
  }

  @RequestMapping(value = "/delbymid", method = RequestMethod.POST)
  @ResponseBody
  public String DelByMid(String mid) {

    String rst = menuService.Del(mid);
    String json = menuService.getMenu();
    List<AppMenu> list = (List<AppMenu>) JSON.parse(json);
    request.getSession().setAttribute("menu", list);
    return rst;
  }

  @RequestMapping(value = "/getsingle", method = RequestMethod.POST)
  @ResponseBody
  public String GetSingle(String mid) {
    return menuService.GetSingle(mid);
  }

  @RequestMapping(value = "/updatebymid", method = RequestMethod.POST)
  @ResponseBody
  public String UpdateByMid(AppMenu model) {
    String parentcode = model.getCata();
    if (parentcode.isEmpty()) { // 没有上级菜单
      model.setCata("1");
      model.setParentcode("");
    } else {
      model.setCata("2");
      model.setParentcode(parentcode);
      if (model.getUrl().isEmpty()) {
        model.setUrl("#");
      }
    }

    String rst = menuService.UpdateByMid(model);
    String json = menuService.getMenu();
    List<AppMenu> list = (List<AppMenu>) JSON.parse(json);

    request.getSession().setAttribute("menu", list);
    return rst;
  }
}
