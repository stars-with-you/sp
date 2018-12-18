/*
  Created: 方磊
  Date: 2017年8月3日  上午8:45:30

*/
package com.fl.sp.menu.service.impl;

import com.fl.sp.common.CommonHelp;
import com.fl.sp.mapper.AppMenuMapper;
import com.fl.sp.menu.service.AppMenuService;
import com.fl.sp.model.AppMenu;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AppMenuServiceImpl implements AppMenuService {

	@Resource
	private AppMenuMapper appMenuMapper;

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(AppMenuServiceImpl.class.getName());

	public String getData(int currentPage, int pagesize, AppMenu model) {

		PageHelper.startPage(currentPage, pagesize);
		List<AppMenu> list = appMenuMapper.selectList(model);

		PageInfo<AppMenu> pageinfo = new PageInfo<AppMenu>(list);

		int totalcount = (int) pageinfo.getTotal();
		String json;
		try {

			json = "{\"code\": \"0\", \"msg\": \"\",\"count\": \"" + totalcount + "\",\"data\":"
					+ CommonHelp.MyToJSONString(list)+ "}";
		} catch (Exception e) {
			json = "{\"code\": \"1\", \"msg\": \"\",\"count\":0,data:[]}";
		}
		return json;
	}

	public String Add(AppMenu model) {
		// AppMenu md = new AppMenu();
		// md.setMenucode(model.getMenucode());
		// List<AppMenu> list.ftl = appMenuMapper.selectAll(md);
		// if (list.ftl.size() > 0) {
		// return "2";
		// }
		int i;
		try {
			i = appMenuMapper.insert(model);
		} catch (Exception e) {
			try {
				log.error("菜单信息添加失败" + CommonHelp.MyToJSONString(model), e);
			} catch (Exception e1) {
			}
			return e.getMessage();
		}
		return String.valueOf(i);
	}

	public String Del(String mid) {
		int i;
		try {
			i = appMenuMapper.deleteByPrimaryKey(mid);
		} catch (Exception e) {
			try {
				log.error("菜单信息删除失败" + mid, e);
			} catch (Exception e1) {
			}
			return e.getMessage();
		}
		return String.valueOf(i);
	}

	public String GetSingle(String mid) {
		AppMenu model = new AppMenu();
		model.setMid(mid);
		AppMenu list = appMenuMapper.selectAll(model).get(0);
		String json;
		try {
			json = CommonHelp.MyToJSONString(list);
		} catch (Exception e) {
			json = "";
		}
		return json;
	}

	public String UpdateByMid(AppMenu model) {
		int i;
		try {
			i = appMenuMapper.update(model);
		} catch (Exception e) {
			try {
				log.error("菜单信息删除失败" +CommonHelp.MyToJSONString(model), e);
			} catch (Exception e1) {
			}
			return e.getMessage();
		}
		return String.valueOf(i);
	}

	public String getMenu() {
		AppMenu model = new AppMenu();
		List<AppMenu> list = appMenuMapper.selectAll(model);
		String json;
		try {
			json = CommonHelp.MyToJSONString(list);

		} catch (Exception e) {
			return "";
		}
		return json;
	}

}
