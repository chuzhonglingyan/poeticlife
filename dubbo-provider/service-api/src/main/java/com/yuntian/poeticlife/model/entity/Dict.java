package com.yuntian.poeticlife.model.entity;

import java.util.Date;
import javax.persistence.*;

public class Dict {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 字典编码
     */
    @Column(name = "dict_code")
    private String dictCode;

    /**
     * 字典名称
     */
    @Column(name = "dict_name")
    private String dictName;

    /**
     * 字典值
     */
    @Column(name = "dict_vaule")
    private String dictVaule;

    /**
     * 字典说明
     */
    private String discription;

    /**
     * 是否删除 0：否 1：是
     */
    @Column(name = "is_delete")
    private Byte isDelete;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 创建人ID
     */
    @Column(name = "create_id")
    private Long createId;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 更新人ID
     */
    @Column(name = "update_id")
    private Long updateId;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取字典编码
     *
     * @return dict_code - 字典编码
     */
    public String getDictCode() {
        return dictCode;
    }

    /**
     * 设置字典编码
     *
     * @param dictCode 字典编码
     */
    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    /**
     * 获取字典名称
     *
     * @return dict_name - 字典名称
     */
    public String getDictName() {
        return dictName;
    }

    /**
     * 设置字典名称
     *
     * @param dictName 字典名称
     */
    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    /**
     * 获取字典值
     *
     * @return dict_vaule - 字典值
     */
    public String getDictVaule() {
        return dictVaule;
    }

    /**
     * 设置字典值
     *
     * @param dictVaule 字典值
     */
    public void setDictVaule(String dictVaule) {
        this.dictVaule = dictVaule;
    }

    /**
     * 获取字典说明
     *
     * @return discription - 字典说明
     */
    public String getDiscription() {
        return discription;
    }

    /**
     * 设置字典说明
     *
     * @param discription 字典说明
     */
    public void setDiscription(String discription) {
        this.discription = discription;
    }

    /**
     * 获取是否删除 0：否 1：是
     *
     * @return is_delete - 是否删除 0：否 1：是
     */
    public Byte getIsDelete() {
        return isDelete;
    }

    /**
     * 设置是否删除 0：否 1：是
     *
     * @param isDelete 是否删除 0：否 1：是
     */
    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
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
     * 获取创建人ID
     *
     * @return create_id - 创建人ID
     */
    public Long getcreateId() {
        return createId;
    }

    /**
     * 设置创建人ID
     *
     * @param createId 创建人ID
     */
    public void setcreateId(Long createId) {
        this.createId = createId;
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
     * 获取更新人ID
     *
     * @return update_id - 更新人ID
     */
    public Long getupdateId() {
        return updateId;
    }

    /**
     * 设置更新人ID
     *
     * @param updateId 更新人ID
     */
    public void setupdateId(Long updateId) {
        this.updateId = updateId;
    }
}