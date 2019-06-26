package com.yuntian.poeticlife.model.entity;

import java.util.Date;
import javax.persistence.*;

public class Image {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 图片模块
     */
    @Column(name = "image_module")
    private Byte imageModule;

    /**
     * 图片名
     */
    @Column(name = "image_name")
    private String imageName;

    /**
     * 图片类型
     */
    @Column(name = "image_type")
    private Byte imageType;

    /**
     * 图片尺寸
     */
    @Column(name = "image_size")
    private String imageSize;

    /**
     * 图片md5值
     */
    @Column(name = "image_md5")
    private String imageMd5;

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
     * 图片内容
     */
    @Column(name = "image_content")
    private byte[] imageContent;

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
     * 获取图片模块
     *
     * @return image_module - 图片模块
     */
    public Byte getImageModule() {
        return imageModule;
    }

    /**
     * 设置图片模块
     *
     * @param imageModule 图片模块
     */
    public void setImageModule(Byte imageModule) {
        this.imageModule = imageModule;
    }

    /**
     * 获取图片名
     *
     * @return image_name - 图片名
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * 设置图片名
     *
     * @param imageName 图片名
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    /**
     * 获取图片类型
     *
     * @return image_type - 图片类型
     */
    public Byte getImageType() {
        return imageType;
    }

    /**
     * 设置图片类型
     *
     * @param imageType 图片类型
     */
    public void setImageType(Byte imageType) {
        this.imageType = imageType;
    }

    /**
     * 获取图片尺寸
     *
     * @return image_size - 图片尺寸
     */
    public String getImageSize() {
        return imageSize;
    }

    /**
     * 设置图片尺寸
     *
     * @param imageSize 图片尺寸
     */
    public void setImageSize(String imageSize) {
        this.imageSize = imageSize;
    }

    /**
     * 获取图片md5值
     *
     * @return image_md5 - 图片md5值
     */
    public String getImageMd5() {
        return imageMd5;
    }

    /**
     * 设置图片md5值
     *
     * @param imageMd5 图片md5值
     */
    public void setImageMd5(String imageMd5) {
        this.imageMd5 = imageMd5;
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

    /**
     * 获取图片内容
     *
     * @return image_content - 图片内容
     */
    public byte[] getImageContent() {
        return imageContent;
    }

    /**
     * 设置图片内容
     *
     * @param imageContent 图片内容
     */
    public void setImageContent(byte[] imageContent) {
        this.imageContent = imageContent;
    }
}