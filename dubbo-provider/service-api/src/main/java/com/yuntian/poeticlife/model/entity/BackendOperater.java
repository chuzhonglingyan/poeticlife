package com.yuntian.poeticlife.model.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "backend_operater")
public class BackendOperater implements Serializable {


    private static final long serialVersionUID = 4477423196488196386L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 账号
     */
    @Column(name = "account_name")
    private String accountName;

    /**
     * 用户名
     */
    @Column(name = "user_name")
    private String userName;

    @Column(name = "pass_word")
    @JSONField(serialize = false)
    private String passWord;

    /**
     * 邮箱
     */
    private String email;


    /**
     * 电话
     */
    private String phone;


    /**
     * 创建人
     */
    @Column(name = "create_by")
    private Long createBy;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新人
     */
    @Column(name = "update_by")
    private Long updateBy;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 是否超级管理员，0-否，1-是，默认为0
     */
    @Column(name = "is_supper")
    private Byte isSupper;

    /**
     * 是否启用，0-禁用，1-启用，默认为0
     */
    @Column(name = "is_enable")
    private Byte isEnable;

    /**
     * 是否删除，0-未删除，1-删除，默认为0
     */
    @Column(name = "is_delete")
    private Byte isDelete;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取账号
     *
     * @return account_name - 账号
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * 设置账号
     *
     * @param accountName 账号
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * 获取用户名
     *
     * @return user_name - 用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户名
     *
     * @param userName 用户名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return pass_word
     */
    public String getPassWord() {
        return passWord;
    }

    /**
     * @param passWord
     */
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取创建人
     *
     * @return create_by - 创建人
     */
    public Long getCreateBy() {
        return createBy;
    }

    /**
     * 设置创建人
     *
     * @param createBy 创建人
     */
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新人
     *
     * @return update_by - 更新人
     */
    public Long getUpdateBy() {
        return updateBy;
    }

    /**
     * 设置更新人
     *
     * @param updateBy 更新人
     */
    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取是否超级管理员，0-否，1-是，默认为0
     *
     * @return is_supper - 是否超级管理员，0-否，1-是，默认为0
     */
    public Byte getIsSupper() {
        return isSupper;
    }

    /**
     * 设置是否超级管理员，0-否，1-是，默认为0
     *
     * @param isSupper 是否超级管理员，0-否，1-是，默认为0
     */
    public void setIsSupper(Byte isSupper) {
        this.isSupper = isSupper;
    }

    /**
     * 获取是否启用，0-禁用，1-启用，默认为0
     *
     * @return is_enable - 是否启用，0-禁用，1-启用，默认为0
     */
    public Byte getIsEnable() {
        return isEnable;
    }

    /**
     * 设置是否启用，0-禁用，1-启用，默认为0
     *
     * @param isEnable 是否启用，0-禁用，1-启用，默认为0
     */
    public void setIsEnable(Byte isEnable) {
        this.isEnable = isEnable;
    }

    /**
     * 获取是否删除，0-未删除，1-删除，默认为0
     *
     * @return is_delete - 是否删除，0-未删除，1-删除，默认为0
     */
    public Byte getIsDelete() {
        return isDelete;
    }

    /**
     * 设置是否删除，0-未删除，1-删除，默认为0
     *
     * @param isDelete 是否删除，0-未删除，1-删除，默认为0
     */
    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}