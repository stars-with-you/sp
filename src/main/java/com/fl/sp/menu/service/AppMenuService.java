/*
  Created: 方磊
  Date: 2018年3月29日  上午9:01:31

*/
package com.fl.sp.menu.service;

import com.fl.sp.model.AppMenu;

public interface AppMenuService {
	public String getData(int currentPage, int pagesize, AppMenu model);

	public String Add(AppMenu model);

	public String Del(String mid);

	public String GetSingle(String mid);

	public String UpdateByMid(AppMenu model);

	public String getMenu();
}
