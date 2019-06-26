package com.yuntian.poeticlife.model.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "opreater_image")
public class OpreaterImage {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 后台用户id
     */
    @Column(name = "opreater_id")
    private Long opreaterId;

    /**
     * 图片记录id
     */
    @Column(name = "image_id")
    private String imageId;

    /**
     * 创建人
     */
    @Column(name = "create_id")
    private Long createId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新人
     */
    @Column(name = "update_id")
    private Long updateId;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 0未删除 1删除
     */
    @Column(name = "is_delete")
    private Byte isDelete;

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
     * 获取后台用户id
     *
     * @return opreater_id - 后台用户id
     */
    public Long getOpreaterId() {
        return opreaterId;
    }

    /**
     * 设置后台用户id
     *
     * @param opreaterId 后台用户id
     */
    public void setOpreaterId(Long opreaterId) {
        this.opreaterId = opreaterId;
    }

    /**
     * 获取图片记录id
     *
     * @return image_id - 图片记录id
     */
    public String getImageId() {
        return imageId;
    }

    /**
     * 设置图片记录id
     *
     * @param imageId 图片记录id
     */
    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    /**
     * 获取创建人
     *
     * @return create_id - 创建人
     */
    public Long getCreateId() {
        return createId;
    }

    /**
     * 设置创建人
     *
     * @param createId 创建人
     */
    public void setCreateId(Long createId) {
        this.createId = createId;
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
     * @return update_id - 更新人
     */
    public Long getUpdateId() {
        return updateId;
    }

    /**
     * 设置更新人
     *
     * @param updateId 更新人
     */
    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
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
     * 获取0未删除 1删除
     *
     * @return is_delete - 0未删除 1删除
     */
    public Byte getIsDelete() {
        return isDelete;
    }

    /**
     * 设置0未删除 1删除
     *
     * @param isDelete 0未删除 1删除
     */
    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }
}