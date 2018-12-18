/*
  Created: 方磊
  Date: 2017年9月1日  上午9:46:41

*/
package com.fl.sp.urlpr.service.impl;

import com.fl.sp.common.CommonHelp;
import com.fl.sp.mapper.PsShiroMapper;
import com.fl.sp.model.PsShiro;
import com.fl.sp.security.service.ShiroUpdateService;
import com.fl.sp.urlpr.service.PsShiroService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PsShiroServiceImpl implements PsShiroService {
  @Resource private PsShiroMapper psShiroMapper;

  @Resource private ShiroUpdateService shiroUpdateService;

  @Resource
  private ShiroFilterFactoryBean shiroFilterFactoryBean;
  private static final Logger log =
      org.slf4j.LoggerFactory.getLogger(PsShiroServiceImpl.class.getName());



  public String getData(int currentPage, int pagesize, PsShiro model) {

    PageHelper.startPage(currentPage, pagesize);
    List<PsShiro> list = psShiroMapper.selectList(model);

    PageInfo<PsShiro> pageinfo = new PageInfo<PsShiro>(list);

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

  @Transactional
  public String Add(PsShiro model) {
    int i = psShiroMapper.insert(model);
    shiroUpdateService.updatePermission();
    return String.valueOf(i);
  }

  /**
   * 设置sguid为无效
   *
   * @param sguid
   * @return
   */
  @Transactional
  public String Del(String sguid) {
    PsShiro record = new PsShiro();
    record.setIsdel("1");
    record.setSguid(sguid);
    int i = psShiroMapper.updateIsDel(record);
    shiroUpdateService.updatePermission();
    return String.valueOf(i);
  }

  /**
   * 获取一条url权限拦截
   *
   * @param sguid
   * @return
   */
  public String GetSingle(String sguid) {
    PsShiro model = new PsShiro();
    model.setSguid(sguid);
    PsShiro list = psShiroMapper.selectList(model).get(0);
    String json;
    try {
      json = CommonHelp.MyToJSONString(list);
    } catch (Exception e) {
      json = "";
    }
    return json;
  }

  /**
   * 修改一条权限
   *
   * @param model
   * @return
   */
  public String UpdateBySguid(PsShiro model) {
    int i;
    try {
      i = psShiroMapper.updateSelective(model);
      shiroUpdateService.updatePermission();
    } catch (Exception e) {
      try {
        log.error("url拦截信息修改失败" + CommonHelp.MyToJSONString(model), e);
      } catch (Exception e1) {
      }
      return e.getMessage();
    }
    return String.valueOf(i);
  }
}
