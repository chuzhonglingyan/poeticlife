package com.yuntian.poeticlife.config.shiro;

import com.yuntian.poeticlife.util.Servlets;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.resource.ResourceUrlProvider;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author: wangsaichao
 * @date: 2018/6/22
 * @description: 参考 shiro-redis 开源项目 Git地址 https://github.com/alexxiyang/shiro-redis
 */
public class RedisSessionDAO extends AbstractSessionDAO {

    @Resource
    private ResourceUrlProvider resourceUrlProvider;


    private static Logger logger = LoggerFactory.getLogger(RedisSessionDAO.class);

    private static final String DEFAULT_SESSION_KEY_PREFIX = "shiro:session:";
    private String keyPrefix = DEFAULT_SESSION_KEY_PREFIX;

    private static final long DEFAULT_SESSION_IN_MEMORY_TIMEOUT = 1000L;
    /**
     * doReadSession be called about 10 times when login.
     * Save Session in ThreadLocal to resolve this problem. sessionInMemoryTimeout is expiration of Session in ThreadLocal.
     * The default value is 1000 milliseconds (1s).
     * Most of time, you don't need to change it.
     */
    private long sessionInMemoryTimeout = DEFAULT_SESSION_IN_MEMORY_TIMEOUT;

    /**
     * expire time in seconds
     */
    private static final int DEFAULT_EXPIRE = -2;
    private static final int NO_EXPIRE = -1;

    /**
     * Please make sure expire is longer than sesion.getTimeout()
     */
    private int expire = DEFAULT_EXPIRE;

    private static final int MILLISECONDS_IN_A_SECOND = 1000;

    private RedisManager redisManager;
    private static ThreadLocal<Map<Serializable, SessionInMemory>> sessionsInThread = new ThreadLocal<>();


    /**
     * 是否包括离线（最后访问时间大于3分钟为离线会话）
     */
    private  boolean includeLeave=false;

    public RedisSessionDAO() {

    }

    public boolean isIncludeLeave() {
        return includeLeave;
    }

    public void setIncludeLeave(boolean includeLeave) {
        this.includeLeave = includeLeave;
    }

    /**
     * save session
     *
     * @param session
     * @throws UnknownSessionException
     */
    private void saveSession(Session session) throws UnknownSessionException {
        if (session == null || session.getId() == null) {
            logger.error("session or session id is null");
            throw new UnknownSessionException("session or session id is null");
        }
        String key = getRedisSessionKey(session.getId());
        if (expire == DEFAULT_EXPIRE) {
            this.redisManager.set(key, session, (int) (session.getTimeout() / MILLISECONDS_IN_A_SECOND));
            return;
        }
        if (expire != NO_EXPIRE && expire * MILLISECONDS_IN_A_SECOND < session.getTimeout()) {
            logger.warn("Redis session expire time: "
                    + (expire * MILLISECONDS_IN_A_SECOND)
                    + " is less than Session timeout: "
                    + session.getTimeout()
                    + " . It may cause some problems.");
        }
        this.redisManager.set(key, session, expire);
    }


    @Override
    public void update(Session session) throws UnknownSessionException {
        //如果会话过期/停止 没必要再更新了
        try {
            if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
                return;
            }

            if (session instanceof ShiroSession) {
                // 如果没有主要字段(除lastAccessTime以外其他字段)发生改变
                ShiroSession ss = (ShiroSession) session;
                if (!ss.isChanged()) {
                    return;
                }
                //如果没有返回 证明有调用 setAttribute往redis 放的时候永远设置为false
                ss.setChanged(false);
            }
            HttpServletRequest request = Servlets.getRequest();
            if (request != null) {
                String uri = request.getServletPath();
                if (isStaticFile(uri)) {
                    return ;
                }
            }
            this.saveSession(session);
        } catch (Exception e) {
            logger.warn("update Session is failed", e);
        }
    }

    @Override
    public void delete(Session session) {
        if (session == null || session.getId() == null) {
            logger.error("session or session id is null");
            return;
        }
        try {
            sessionsInThread.remove();
            redisManager.del(getRedisSessionKey(session.getId()));
        } catch (Exception e) {
            logger.error("delete session error. session id= {}", session.getId());
        }
    }

    /**
     * 获取活动会话
     * @return
     */
    @Override
    public Collection<Session> getActiveSessions() {
        Set<Session> sessions = new HashSet<>();
        try {
            Set<String> keys = redisManager.scan(this.keyPrefix + "*");
            if (keys != null && keys.size() > 0) {
                for (String key : keys) {
                    Session session = (Session) redisManager.get(key);
                    if (session!=null){
                        boolean isActiveSession = false;
                        // 不包括离线并符合最后访问时间小于等于3分钟条件。
                        if (includeLeave || pastMinutes(session.getLastAccessTime()) <= 3) {
                            isActiveSession = true;
                        }
                        if (isActiveSession) {
                            sessions.add(session);
                        }
                    }
                }
            }else {
                logger.error("搜索"+getKeyPrefix()+":没有关联的会话.");
            }
        } catch (Exception e) {
            logger.error("get active sessions error."+e.getMessage());
        }
        return sessions;
    }



    public Long getActiveSessionsSize() {
        Long size = 0L;
        try {
            size = redisManager.scanSize(this.keyPrefix + "*");
        } catch (Exception e) {
            logger.error("get active sessions error.");
        }
        return size;
    }

    @Override
    protected Serializable doCreate(Session session) {
        if (session == null) {
            logger.error("session is null");
            throw new UnknownSessionException("session is null");
        }
        HttpServletRequest request = Servlets.getRequest();
        if (request != null) {
            String uri = request.getServletPath();
            if (isStaticFile(uri)) {
                return null;
            }
        }
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);

        this.saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null) {
            logger.warn("session id is null");
            return null;
        }
        Session s = null;
        HttpServletRequest request = Servlets.getRequest();
        if (request != null) {
            String uri = request.getServletPath();
            s = (Session) request.getAttribute(sessionId.toString());
            if (isStaticFile(uri)) {
                return null;
            }
        }
        if (s == null) {
            s = getSessionFromThreadLocal(sessionId);
        }
        if (s != null) {
            return s;
        }
        logger.debug("read session from redis");
        try {
            s = (Session) redisManager.get(getRedisSessionKey(sessionId));
            if (s != null) {
                setSessionToThreadLocal(sessionId, s);
            }
        } catch (Exception e) {
            logger.error("read session error. settionId= {}", sessionId);
        }
        return s;
    }

    private void setSessionToThreadLocal(Serializable sessionId, Session s) {
        Map<Serializable, SessionInMemory> sessionMap = sessionsInThread.get();
        if (sessionMap == null) {
            sessionMap = new HashMap<>();
            sessionsInThread.set(sessionMap);
        }
        SessionInMemory sessionInMemory = new SessionInMemory();
        sessionInMemory.setCreateTime(new Date());
        sessionInMemory.setSession(s);
        sessionMap.put(sessionId, sessionInMemory);
    }

    private Session getSessionFromThreadLocal(Serializable sessionId) {
        Session s = null;

        if (sessionsInThread.get() == null) {
            return null;
        }

        Map<Serializable, SessionInMemory> sessionMap = sessionsInThread.get();
        SessionInMemory sessionInMemory = sessionMap.get(sessionId);
        if (sessionInMemory == null) {
            return null;
        }
        Date now = new Date();
        long duration = now.getTime() - sessionInMemory.getCreateTime().getTime();
        if (duration < sessionInMemoryTimeout) {
            s = sessionInMemory.getSession();
            logger.debug("read session from memory");
        } else {
            sessionMap.remove(sessionId);
        }
        return s;
    }


    @Override
    public Session readSession(Serializable sessionId) throws UnknownSessionException {
        try {
            return super.readSession(sessionId);
        } catch (UnknownSessionException e) {
            return null;
        }
    }


    private String getRedisSessionKey(Serializable sessionId) {
        return this.keyPrefix + sessionId;
    }

    public RedisManager getRedisManager() {
        return redisManager;
    }

    public void setRedisManager(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public long getSessionInMemoryTimeout() {
        return sessionInMemoryTimeout;
    }

    public void setSessionInMemoryTimeout(long sessionInMemoryTimeout) {
        this.sessionInMemoryTimeout = sessionInMemoryTimeout;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    private boolean isStaticFile(String path) {
        String staticUri = resourceUrlProvider.getForLookupPath(path);
        return staticUri != null;
    }



    /**
     * 获取过去的分钟
     *
     * @param date
     * @return
     */
    public static long pastMinutes(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 1000);
    }



}

