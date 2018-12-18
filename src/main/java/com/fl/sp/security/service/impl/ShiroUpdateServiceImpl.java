package com.fl.sp.security.service.impl;

import com.fl.sp.mapper.PsShiroMapper;
import com.fl.sp.model.PsShiro;
import com.fl.sp.security.service.ShiroUpdateService;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShiroUpdateServiceImpl implements ShiroUpdateService {
    @Resource
    private ShiroFilterFactoryBean shiroFilterFactoryBean;

    @Resource
    private PsShiroMapper psShiroMapper;

    private static final Logger log = LoggerFactory.getLogger(ShiroUpdateServiceImpl.class);
    /** 更新url权限过滤配置 */
    public void updatePermission() {
        synchronized (shiroFilterFactoryBean) { // 强制同步，控制线程安全
            AbstractShiroFilter shiroFilter = null;
            try {
                shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            // 获取过滤管理器
            PathMatchingFilterChainResolver filterChainResolver =
                    (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
            DefaultFilterChainManager manager =
                    (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();
            // （0）清空初始权限配置
            manager.getFilterChains().clear();
            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
            System.out.println("shiro重新加载url拦截信息");
            // （1）加载配置文件中的权限配置
            List<PsShiro> list = psShiroMapper.selectAll();
            Map<String, String> map = new LinkedHashMap<String, String>();
            for (int i = 0; i < list.size(); i++) {
                map.put(list.get(i).getSkey(), list.get(i).getSvalue());
            }
            shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        }
    }
}
