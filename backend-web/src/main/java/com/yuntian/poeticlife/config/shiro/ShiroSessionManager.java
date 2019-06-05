package com.yuntian.poeticlife.config.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

import javax.servlet.ServletRequest;

/**
 * @author: wangsaichao
 * @date: 2018/6/23
 * @description: 解决单次请求需要多次访问redis
 */
public class ShiroSessionManager extends DefaultWebSessionManager {

    private static Logger logger = LoggerFactory.getLogger(DefaultWebSessionManager.class);
    /**
     * 获取session
     * 优化单次请求需要多次访问redis的问题
     * @param sessionKey
     * @return
     * @throws UnknownSessionException
     */
    @Override
    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
        Serializable sessionId = getSessionId(sessionKey);

        ServletRequest request = null;
        if (sessionKey instanceof WebSessionKey) {
            request = ((WebSessionKey) sessionKey).getServletRequest();
        }

        if (request != null && null != sessionId) {
            Object sessionObj = request.getAttribute(sessionId.toString());
            if (sessionObj != null) {
                logger.debug("read session from request");
                return (Session) sessionObj;
            }
        }

        Session session = null;
        try {
            session = super.retrieveSession(sessionKey);
            if (request != null && null != sessionId&&session!=null) {
                request.setAttribute(sessionId.toString(), session);
            }
        } catch (UnknownSessionException e) {
//            e.printStackTrace();
        }
        return session;
    }
}
