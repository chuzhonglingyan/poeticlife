package com.yuntian.poeticlife.model.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * @Auther: yuntian
 * @Date: 2018/8/22 21:36
 * @Description: 后台角色
 */
public class BackendOperater {

    private Long id;

    private String userName;

    private String accountName;

    @JSONField(serialize = false)
    private String passWord;

    private String email;

    private Long createBy;

    private Long updateBy;

    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date createTime;

    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date updateTime;

    private Byte isEnable;

    private Byte isDelete;

    private Byte isSupper;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Byte isEnable) {
        this.isEnable = isEnable;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    public Byte getIsSupper() {
        return isSupper;
    }

    public void setIsSupper(Byte isSupper) {
        this.isSupper = isSupper;
    }
}
