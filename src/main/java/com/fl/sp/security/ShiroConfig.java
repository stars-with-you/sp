package com.fl.sp.security;

import com.fl.sp.redis.RedisConfig;
import com.fl.sp.security.filter.Filter_Authc;
import com.fl.sp.security.filter.Filter_Perms;
import com.fl.sp.security.filter.Filter_Roles;
import com.fl.sp.security.realm.UserRealm;
import com.fl.sp.security.service.MyUrlFilterService;
import com.jagregory.shiro.freemarker.ShiroTags;
import freemarker.template.TemplateException;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.Filter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// https://blog.csdn.net/m0_37962779/article/details/78605478
@Configuration
public class ShiroConfig {
  @Bean
  public FreeMarkerConfigurer freeMarkerConfigurer() throws IOException, TemplateException {
    FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
    freeMarkerConfigurer.setTemplateLoaderPath("classpath:templates/");
    freemarker.template.Configuration configuration = freeMarkerConfigurer.createConfiguration();
    configuration.setDefaultEncoding("UTF-8");
    //这里可以添加其他共享变量 比如sso登录地址
    configuration.setSharedVariable("shiro", new ShiroTags());
    freeMarkerConfigurer.setConfiguration(configuration);
    return freeMarkerConfigurer;
  }
  /********************************redis 配置**********************************************/
  @Bean
  public RedisConfig redisConfig(){
    return new RedisConfig();
  }
  @Bean
  public RedisManager redisManager(){
    RedisManager redisManager = new RedisManager();     // crazycake 实现
    redisManager.setHost(redisConfig().getHost());
    redisManager.setTimeout(redisConfig().getTimeout());
    return redisManager;
  }
  @Bean
  public JavaUuidSessionIdGenerator sessionIdGenerator(){
    return new JavaUuidSessionIdGenerator();
  }

  @Bean
  public RedisSessionDAO sessionDAO(){
    RedisSessionDAO sessionDAO = new RedisSessionDAO(); // crazycake 实现
    sessionDAO.setRedisManager(redisManager());
    sessionDAO.setSessionIdGenerator(sessionIdGenerator()); //  Session ID 生成器
    return sessionDAO;
  }

  @Bean
  public SimpleCookie cookie(){
    SimpleCookie cookie = new SimpleCookie("SHAREJSESSIONID"); //  cookie的name,对应的默认是 JSESSIONID
    cookie.setHttpOnly(true);
    cookie.setPath("/");        //  path为 / 用于多个系统共享JSESSIONID
    return cookie;
  }
  @Bean
  public DefaultWebSessionManager sessionManager(){
    DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
    sessionManager.setGlobalSessionTimeout(redisConfig().getTimeout());    // 设置session超时
    sessionManager.setDeleteInvalidSessions(true);      // 删除无效session
    sessionManager.setSessionIdCookie(cookie());            // 设置JSESSIONID
    sessionManager.setSessionDAO(sessionDAO());         // 设置sessionDAO
    return sessionManager;
  }
  @Bean
  public RedisCacheManager redisCacheManager(){
    RedisCacheManager cacheManager = new RedisCacheManager();   // crazycake 实现
    cacheManager.setRedisManager(redisManager());
    cacheManager.setPrincipalIdFieldName("pguid");
    cacheManager.getCache("11");
    return cacheManager;
  }

  /********************************redis 配置**********************************************/
  @Bean(name = "userRealm")
  public UserRealm userRealm() { // 1、获取配置的Realm，之所以没使用注解配置，是因为此处需要考虑到加密处理
    UserRealm realm = new UserRealm();
    return realm;
  }

//  /**
//   * 缓存配置
//   *
//   * @return
//   */
//  @Bean(name = "ehCacheManager")
//  public EhCacheManagerFactoryBean ehCacheManager() {
//    EhCacheManagerFactoryBean cacheManager = new EhCacheManagerFactoryBean();
//    // "classpath:config/ehcache.xml"
//    Resource resource = new ClassPathResource("config/ehcache.xml");
//    cacheManager.setConfigLocation(resource);
//    return cacheManager;
//  }
//
//  @Bean(name = "shiroCacheManager")
//  public org.apache.shiro.cache.ehcache.EhCacheManager shiroCacheManager() {
//    org.apache.shiro.cache.ehcache.EhCacheManager ec =
//        new org.apache.shiro.cache.ehcache.EhCacheManager();
//    ec.setCacheManager(ehCacheManager().getObject());
//    return ec;
//  }



  @Bean(name = "securityManager")
  public SecurityManager securityManager() {
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    securityManager.setCacheManager(redisCacheManager());
    securityManager.setRealm(userRealm());
    securityManager.setSessionManager(sessionManager());
    return securityManager;
  }

  @Bean("shiroFilter")
  public ShiroFilterFactoryBean getShiroFilterFactoryBean(
      SecurityManager securityManager, MyUrlFilterService myUrlFilterService) {
    ShiroFilterFactoryBean sb = new ShiroFilterFactoryBean();
    sb.setSecurityManager(securityManager);
    Map<String,String> map=myUrlFilterService.SelectAll();
    System.out.println(map);
    sb.setFilterChainDefinitionMap(map);
    // sb.setSuccessUrl("/index"); // 设置跳转成功页
    sb.setLoginUrl("/user/login");
    sb.setUnauthorizedUrl("/user/login?nosession"); // 授权错误页
    Map<String, Filter> filters = new HashMap<String, Filter>();
    filters.put("authc", new Filter_Authc());
    //filters.put("roles", filter_roles());//不能使用这种方式，https://www.cnblogs.com/gj1990/p/8057348.html
    filters.put("roles", new Filter_Roles());
    filters.put("perms", new Filter_Perms());
    //filters.put("kickout",kickoutSessionControlFilter());
    sb.setFilters(filters);

    return sb;
  }

  /**
   * 其它filter定义要放在shiroFilter后面，否则报错：No SecurityManager accessible to the calling code
   *
   * @return
   */

  /**
   *Shiro生命周期处理器
   * @return
   */
  @Bean(name = "lifecycleBeanPostProcessor")
  public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
    return new LifecycleBeanPostProcessor();
  }

  @Bean
  @DependsOn("lifecycleBeanPostProcessor")
  public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
    DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
    daap.setProxyTargetClass(true);
    return daap;
  }
  /***
   * 使授权注解起作用不如不想配置可以在pom文件中加入
   * <dependency>
   *<groupId>org.springframework.boot</groupId>
   *<artifactId>spring-boot-starter-aop</artifactId>
   *</dependency>
   * @param
   * @return
   */
  @Bean
  public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
    AuthorizationAttributeSourceAdvisor au=new AuthorizationAttributeSourceAdvisor();
    au.setSecurityManager(securityManager());
    return  au;
  }
}
