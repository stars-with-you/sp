/*
  Created: 方磊
  Date: 2017年8月25日  下午5:27:28

*/
package com.fl.sp.security.service.impl;

import com.fl.sp.mapper.PsShiroMapper;
import com.fl.sp.model.PsShiro;
import com.fl.sp.security.service.MyUrlFilterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** 用来更新拦截权限 */
@Service(value = "myUrlFilterService")
public class MyUrlFilterServiceImpl implements MyUrlFilterService {
  private static final Logger log = LoggerFactory.getLogger(MyUrlFilterServiceImpl.class);
  @Resource
  private PsShiroMapper psShiroMapper;


  /**
   * 获取所有的shiro url拦截信息
   *
   * @return
   */
  public Map<String, String> SelectAll() {
    List<PsShiro> list = psShiroMapper.selectAll();
    Map<String, String> map = new LinkedHashMap<String, String>();
    for (int i = 0; i < list.size(); i++) {
      map.put(list.get(i).getSkey(), list.get(i).getSvalue());
    }
    return map;
  }

}
