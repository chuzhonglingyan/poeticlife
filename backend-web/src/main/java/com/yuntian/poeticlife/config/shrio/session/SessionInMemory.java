package com.yuntian.poeticlife.config.shrio.session;

import org.apache.shiro.session.Session;

import java.util.Date;

/**
 * @Auther: yuntian
 * @Date: 2019/6/3 0003 21:48
 * @Description:
 */
public class SessionInMemory {


    private Session session;
    private Date createTime;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}