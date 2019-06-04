package com.yuntian.poeticlife.config;


import com.google.common.collect.Maps;
import com.yuntian.poeticlife.config.shrio.CaptchaFormAuthenticationFilter;
import com.yuntian.poeticlife.config.shrio.ShiroRealm;
import com.yuntian.poeticlife.config.shrio.ShrioRedisUtil;
import com.yuntian.poeticlife.config.shrio.cache.ShiroRedisCacheManager;
import com.yuntian.poeticlife.config.shrio.session.RedisSessionDAO;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.Filter;

/**
 * @Auther: yuntian
 * @Date: 2019/2/20 0020 23:20
 * @Description:
 */
@Configuration
public class ShiroConfig {

    @Resource
    private ShiroProperties shiroProperties;

    @Resource
    private ShrioRedisUtil shrioRedisUtil;


    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // setLoginOutUrl 如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射
        // 登陆界面
        shiroFilterFactoryBean.setLoginUrl("/login");

        //登录成功跳转的页面
        shiroFilterFactoryBean.setSuccessUrl("/index");
        Map<String,Filter> filters = Maps.newHashMap();
        FormAuthenticationFilter loginFilter = new CaptchaFormAuthenticationFilter();
        //对应前端的checkbox的name = rememberMe
        loginFilter.setRememberMeParam(CookieRememberMeManager.DEFAULT_REMEMBER_ME_COOKIE_NAME);
        //此处使用自定义的拦截器,autho默认使用FormAuthenticationFilter拦截器
        filters.put("authc", loginFilter);
        shiroFilterFactoryBean.setFilters(filters);

        // 没有权限访问的界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");


        // 设置拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        // 匿名访问静态资源
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/image/**", "anon");//img
        filterChainDefinitionMap.put("/css/**", "anon");//css
        filterChainDefinitionMap.put("/js/**", "anon");//js

        //游客，开发权限
        filterChainDefinitionMap.put("/guest/**", "anon");
        //用户，需要角色权限 “user”
        filterChainDefinitionMap.put("/user/**", "roles[user]");
        //管理员，需要角色权限 “admin”
        filterChainDefinitionMap.put("/admin/**", "roles[admin]");
        filterChainDefinitionMap.put("/operater/register", "anon");
        filterChainDefinitionMap.put("/register", "anon");
        //其余接口一律拦截 //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截

        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }



    /**
     * 注入 securityManager
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 配置 rememberMeCookie 查看源码可以知道，这里的rememberMeManager就仅仅是一个赋值，所以先执行
        securityManager.setRememberMeManager(rememberMeManager());
        // 配置 缓存管理类 cacheManager，这个cacheManager必须要在前面执行，因为setRealm 和 setSessionManage都有方法初始化了cachemanager,看下源码就知道了
        securityManager.setCacheManager(cacheManager());
        // 设置 realm.
        securityManager.setRealm(customRealm());
        // 配置 sessionManager
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    /**
     * rememberMe cookie 效果是重开浏览器后无需重新登录
     *
     * @return SimpleCookie
     */
    private SimpleCookie rememberMeCookie() {
        // 这里的Cookie的默认名称是CookieRememberMeManager.DEFAULT_REMEMBER_ME_COOKIE_NAME SimpleCookie
        SimpleCookie cookie = new SimpleCookie(CookieRememberMeManager.DEFAULT_REMEMBER_ME_COOKIE_NAME);
        // 是否只在https情况下传输
        cookie.setSecure(false); // 设置 cookie 的过期时间，单位为秒，这里为一天
        cookie.setMaxAge(shiroProperties.getCookieTimeout());
        return cookie;
    }


    /**
     * cookie管理对象
     *
     * @return CookieRememberMeManager
     */
    private CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        // rememberMe cookie 加密的密钥
        cookieRememberMeManager.setCipherKey(Base64.decode(shiroProperties.getCookieCipherKey()));
        return cookieRememberMeManager;
    }


    /**
     * 自定义身份认证 realm;
     * 必须写这个类，并加上 @Bean 注解，目的是注入 ShiroRealm，
     * 否则会影响 CustomRealm类 中其他类的依赖注入
     */
    @Bean
    public ShiroRealm customRealm() {
        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setCachingEnabled(true);
        //启用身份验证缓存，即缓存AuthenticationInfo信息，默认false
        shiroRealm.setAuthenticationCachingEnabled(true);
        //缓存AuthenticationInfo信息的缓存名称 在ehcache-shiro.xml中有对应缓存的配置
        shiroRealm.setAuthenticationCacheName("authenticationCache");
        //启用授权缓存，即缓存AuthorizationInfo信息，默认false
        shiroRealm.setAuthorizationCachingEnabled(true);
        //缓存AuthorizationInfo信息的缓存名称  在ehcache-shiro.xml中有对应缓存的配置
        shiroRealm.setAuthorizationCacheName("authorizationCache");
        return shiroRealm;
    }


    /**
     * 生成一个ShiroRedisCacheManager
     **/
    private ShiroRedisCacheManager cacheManager() {
        ShiroRedisCacheManager redisCacheManager = new ShiroRedisCacheManager(shrioRedisUtil, shiroProperties.getCachePrefix(), shiroProperties.getCacheExpireIn());
        //redis中针对不同用户缓存
        redisCacheManager.setPrincipalIdFieldName(shiroProperties.getPrincipalIdFieldName());
        return redisCacheManager;
    }


    /**
     * session 管理对象
     *
     * @return DefaultWebSessionManager
     */
    private DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //全局会话超时时间（单位毫秒），默认30分钟  暂时设置为10秒钟 用来测试
        sessionManager.setGlobalSessionTimeout(shiroProperties.getSessionTimeout());
        sessionManager.setSessionIdCookie(sessionIdCookie());
        sessionManager.setSessionDAO(sessionDAO());

        //是否开启删除无效的session对象  默认为true
        sessionManager.setDeleteInvalidSessions(true);
        //是否开启定时调度器进行检测过期session 默认为true
        sessionManager.setSessionValidationSchedulerEnabled(true);
        //设置session失效的扫描时间, 清理用户直接关闭浏览器造成的孤立会话 默认为 1个小时
        //设置该属性 就不需要设置 ExecutorServiceSessionValidationScheduler 底层也是默认自动调用ExecutorServiceSessionValidationScheduler
        sessionManager.setSessionValidationInterval(3600000);
        //取消url 后面的 JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }


    /**
     * 配置保存sessionId的cookie
     * 注意：这里的cookie 不是上面的记住我 cookie 记住我需要一个cookie session管理 也需要自己的cookie
     * 默认为: JSESSIONID 问题: 与SERVLET容器名冲突,重新定义为sid
     * @return
     */
    @Bean("sessionIdCookie")
    public SimpleCookie sessionIdCookie(){
        //这个参数是cookie的名称
        SimpleCookie simpleCookie = new SimpleCookie(shiroProperties.getSessionIdName());
        //setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：
        //setcookie()的第七个参数
        //设为true后，只能通过http访问，javascript无法访问
        //防止xss读取cookie
        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/");
        //maxAge=-1表示浏览器关闭时失效此Cookie
        simpleCookie.setMaxAge(-1);
        return simpleCookie;
    }


    /**
     * SessionDAO的作用是为Session提供CRUD并进行持久化的一个shiro组件
     * MemorySessionDAO 直接在内存中进行会话维护
     * EnterpriseCacheSessionDAO  提供了缓存功能的会话维护，默认情况下使用MapCache实现，内部使用ConcurrentHashMap保存缓存的会话。
     *
     * @return
     */
    @Bean
    public SessionDAO sessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(shrioRedisUtil);
        //session在redis中的保存时间,最好大于session会话超时时间
        redisSessionDAO.setExpire(shiroProperties.getSessionExpireIn());
        redisSessionDAO.setKeyPrefix(shiroProperties.getSessionPrefix());
        return redisSessionDAO;
    }


    /**
     * AOP式方法级权限检查
     *
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }


    /**
     * 开启shiro 注解模式
     * 可以在controller中的方法前加上注解
     * 如 @RequiresPermissions("userInfo:add")
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


    /**
     * 配置会话ID生成器
     *
     * @return
     */
    @Bean
    public SessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }


}
