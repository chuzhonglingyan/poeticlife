package com.yuntian.poeticlife.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "schedule_job")
public class ScheduleJob implements Serializable {


    private static final long serialVersionUID = 8938672964821197143L;
    /**
     * 任务id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 组名称
     */
    @Column(name = "group_name")
    private String groupName;

    /*
     * spring bean名称
     */
    @Column(name = "bean_name")
    private String beanName;

    /**
     * 方法名
     */
    @Column(name = "method_name")
    private String methodName;

    /**
     * 参数
     */
    private String params;

    /**
     * cron表达式
     */
    @Column(name = "cron_expression")
    private String cronExpression;

    /**
     * 任务状态  1：正常  0：暂停
     */
    private Byte status;

    /**
     * 是否删除0：否1是
     */
    @Column(name = "is_delete")
    private Byte isDelete;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 创建人ID
     */
    @Column(name = "create_by")
    private Long createBy;

    /**
     * 修改人ID
     */
    @Column(name = "update_by")
    private Long updateBy;

    /**
     * 获取任务id
     *
     * @return id - 任务id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置任务id
     *
     * @param id 任务id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取spring bean名称
     *
     * @return bean_name - spring bean名称
     */
    public String getBeanName() {
        return beanName;
    }

    /**
     * 设置spring bean名称
     *
     * @param beanName spring bean名称
     */
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    /**
     * 获取方法名
     *
     * @return method_name - 方法名
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * 设置方法名
     *
     * @param methodName 方法名
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * 获取参数
     *
     * @return params - 参数
     */
    public String getParams() {
        return params;
    }

    /**
     * 设置参数
     *
     * @param params 参数
     */
    public void setParams(String params) {
        this.params = params;
    }

    /**
     * 获取cron表达式
     *
     * @return cron_expression - cron表达式
     */
    public String getCronExpression() {
        return cronExpression;
    }

    /**
     * 设置cron表达式
     *
     * @param cronExpression cron表达式
     */
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    /**
     * 获取任务状态  1：正常  0：暂停
     *
     * @return status - 任务状态  1：正常  0：暂停
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置任务状态  1：正常  0：暂停
     *
     * @param status 任务状态  1：正常  0：暂停
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取是否删除0：否1是
     *
     * @return is_delete - 是否删除0：否1是
     */
    public Byte getIsDelete() {
        return isDelete;
    }

    /**
     * 设置是否删除0：否1是
     *
     * @param isDelete 是否删除0：否1是
     */
    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
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
     * 获取修改时间
     *
     * @return update_time - 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     *
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取创建人ID
     *
     * @return create_by - 创建人ID
     */
    public Long getCreateBy() {
        return createBy;
    }

    /**
     * 设置创建人ID
     *
     * @param createBy 创建人ID
     */
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取修改人ID
     *
     * @return update_by - 修改人ID
     */
    public Long getUpdateBy() {
        return updateBy;
    }

    /**
     * 设置修改人ID
     *
     * @param updateBy 修改人ID
     */
    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}