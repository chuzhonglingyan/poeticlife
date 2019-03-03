package com.yuntian.poeticlife.config;


import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

/**
 * @Auther: yuntian
 * @Date: 2019/2/20 0020 23:20
 * @Description:
 */
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // setLoginUrl 如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射
        // 登陆界面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 成功登陆后的界面
        shiroFilterFactoryBean.setSuccessUrl("/index");
        // 没有权限访问的界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");

//        Map<String, Filter > filters=new HashMap<>();
//
//        filters.put("authc",shiroFormAuthenticationFilter());
//        shiroFilterFactoryBean.setFilters(filters);


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
        //开放登陆接口
        filterChainDefinitionMap.put("/operater/login", "anon");


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
        // 设置 realm.
        securityManager.setRealm(customRealm());
        return securityManager;
    }

    /**
     * 自定义身份认证 realm;
     * <p>
     * 必须写这个类，并加上 @Bean 注解，目的是注入 CustomRealm，
     * 否则会影响 CustomRealm类 中其他类的依赖注入
     */
    @Bean
    public CustomRealm customRealm() {
        return new CustomRealm();
    }

    @Bean
    public FormAuthenticationFilter  shiroFormAuthenticationFilter(){
        return new ShiroFormAuthenticationFilter();
    }

}
