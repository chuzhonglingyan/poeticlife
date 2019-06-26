package com.yuntian.poeticlife.model.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "schedule_job_log")
public class ScheduleJobLog implements Serializable {


    private static final long serialVersionUID = -5875481109536489749L;
    /**
     * 任务日志id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 任务id
     */
    @Column(name = "job_id")
    private Long jobId;

    /**
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
     * 任务状态    0：成功    1：失败
     */
    private Byte status;

    /**
     * 失败信息
     */
    private String error;

    /**
     * 执行机器
     */
    private String ip;

    /**
     * 耗时(单位：毫秒)
     */
    private Long times;

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
     * 创建人
     */
    @Column(name = "create_id")
    private Long createId;

    /**
     * 修改人
     */
    @Column(name = "update_id")
    private Long updateId;

    /**
     * 获取任务日志id
     *
     * @return id - 任务日志id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置任务日志id
     *
     * @param id 任务日志id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取任务id
     *
     * @return job_id - 任务id
     */
    public Long getJobId() {
        return jobId;
    }

    /**
     * 设置任务id
     *
     * @param jobId 任务id
     */
    public void setJobId(Long jobId) {
        this.jobId = jobId;
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
     * 获取任务状态    0：成功    1：失败
     *
     * @return status - 任务状态    0：成功    1：失败
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置任务状态    0：成功    1：失败
     *
     * @param status 任务状态    0：成功    1：失败
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取失败信息
     *
     * @return error - 失败信息
     */
    public String getError() {
        return error;
    }

    /**
     * 设置失败信息
     *
     * @param error 失败信息
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * 获取执行机器
     *
     * @return ip - 执行机器
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置执行机器
     *
     * @param ip 执行机器
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getTimes() {
        return times;
    }

    public void setTimes(Long times) {
        this.times = times;
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

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }
}