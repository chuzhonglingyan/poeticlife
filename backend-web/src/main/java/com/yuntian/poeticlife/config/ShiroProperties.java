package com.yuntian.poeticlife.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Auther: yuntian
 * @Date: 2019/6/2 0002 19:24
 * @Description:
 */
@Component
@ConfigurationProperties(prefix = "yuntian.shiro")
public class ShiroProperties {

    private String cachePrefix;
    private String principalIdFieldName;
    private int cacheExpireIn;
    private String sessionPrefix;
    private int sessionExpireIn;
    private long sessionTimeout;
    private int cookieTimeout;
    private String cookieCipherKey;
    private String anonUrl;
    private String loginUrl;
    private String loginOutUrl;
    private String successUrl;
    private String unauthorizedUrl;
    private String sessionIdName;

    public String getCachePrefix() {
        return cachePrefix;
    }

    public String getSessionPrefix() {
        return sessionPrefix;
    }

    public void setSessionPrefix(String sessionPrefix) {
        this.sessionPrefix = sessionPrefix;
    }

    public void setCachePrefix(String cachePrefix) {
        this.cachePrefix = cachePrefix;
    }

    public String getPrincipalIdFieldName() {
        return principalIdFieldName;
    }

    public void setPrincipalIdFieldName(String principalIdFieldName) {
        this.principalIdFieldName = principalIdFieldName;
    }

    public int getSessionExpireIn() {
        return sessionExpireIn;
    }

    public void setSessionExpireIn(int sessionExpireIn) {
        this.sessionExpireIn = sessionExpireIn;
    }

    public int getCacheExpireIn() {
        return cacheExpireIn;
    }

    public void setCacheExpireIn(int cacheExpireIn) {
        this.cacheExpireIn = cacheExpireIn;
    }

    public long getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(long sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public int getCookieTimeout() {
        return cookieTimeout;
    }

    public void setCookieTimeout(int cookieTimeout) {
        this.cookieTimeout = cookieTimeout;
    }

    public String getCookieCipherKey() {
        return cookieCipherKey;
    }

    public void setCookieCipherKey(String cookieCipherKey) {
        this.cookieCipherKey = cookieCipherKey;
    }

    public String getAnonUrl() {
        return anonUrl;
    }

    public void setAnonUrl(String anonUrl) {
        this.anonUrl = anonUrl;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getLoginOutUrl() {
        return loginOutUrl;
    }

    public void setLoginOutUrl(String loginOutUrl) {
        this.loginOutUrl = loginOutUrl;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getUnauthorizedUrl() {
        return unauthorizedUrl;
    }

    public void setUnauthorizedUrl(String unauthorizedUrl) {
        this.unauthorizedUrl = unauthorizedUrl;
    }

    public String getSessionIdName() {
        return sessionIdName;
    }

    public void setSessionIdName(String sessionIdName) {
        this.sessionIdName = sessionIdName;
    }
}
