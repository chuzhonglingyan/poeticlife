package com.yuntian.poeticlife.model.entity;

import java.util.Date;
import javax.persistence.*;

public class Article {
    /**
     * 文章id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 0 html源码 1md源码
     */
    @Column(name = "content_type")
    private Byte contentType;

    /**
     * 发布时间
     */
    @Column(name = "publishTime")
    private Date publishtime;

    /**
     * 截止时间
     */
    @Column(name = "editTime")
    private Date edittime;

    /**
     * 0表示草稿箱，1表示已发表，2表示已删除
     */
    private Byte state;

    /**
     * 浏览量
     */
    @Column(name = "look_count")
    private Long lookCount;

    /**
     * 评论总数
     */
    @Column(name = "comment_count")
    private Long commentCount;

    /**
     * 收藏总数
     */
    @Column(name = "favorite_count")
    private Long favoriteCount;

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
     * 是否删除，0-未删除，1-删除，默认为0
     */
    @Column(name = "is_delete")
    private Byte isDelete;

    /**
     * md文件源码
     */
    private String content;

    /**
     * 获取文章id
     *
     * @return id - 文章id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置文章id
     *
     * @param id 文章id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取文章标题
     *
     * @return title - 文章标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置文章标题
     *
     * @param title 文章标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取摘要
     *
     * @return summary - 摘要
     */
    public String getSummary() {
        return summary;
    }

    /**
     * 设置摘要
     *
     * @param summary 摘要
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * 获取0 html源码 1md源码
     *
     * @return content_type - 0 html源码 1md源码
     */
    public Byte getContentType() {
        return contentType;
    }

    /**
     * 设置0 html源码 1md源码
     *
     * @param contentType 0 html源码 1md源码
     */
    public void setContentType(Byte contentType) {
        this.contentType = contentType;
    }

    /**
     * 获取发布时间
     *
     * @return publishTime - 发布时间
     */
    public Date getPublishtime() {
        return publishtime;
    }

    /**
     * 设置发布时间
     *
     * @param publishtime 发布时间
     */
    public void setPublishtime(Date publishtime) {
        this.publishtime = publishtime;
    }

    /**
     * 获取截止时间
     *
     * @return editTime - 截止时间
     */
    public Date getEdittime() {
        return edittime;
    }

    /**
     * 设置截止时间
     *
     * @param edittime 截止时间
     */
    public void setEdittime(Date edittime) {
        this.edittime = edittime;
    }

    /**
     * 获取0表示草稿箱，1表示已发表，2表示已删除
     *
     * @return state - 0表示草稿箱，1表示已发表，2表示已删除
     */
    public Byte getState() {
        return state;
    }

    /**
     * 设置0表示草稿箱，1表示已发表，2表示已删除
     *
     * @param state 0表示草稿箱，1表示已发表，2表示已删除
     */
    public void setState(Byte state) {
        this.state = state;
    }

    /**
     * 获取浏览量
     *
     * @return look_count - 浏览量
     */
    public Long getLookCount() {
        return lookCount;
    }

    /**
     * 设置浏览量
     *
     * @param lookCount 浏览量
     */
    public void setLookCount(Long lookCount) {
        this.lookCount = lookCount;
    }

    /**
     * 获取评论总数
     *
     * @return comment_count - 评论总数
     */
    public Long getCommentCount() {
        return commentCount;
    }

    /**
     * 设置评论总数
     *
     * @param commentCount 评论总数
     */
    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

    /**
     * 获取收藏总数
     *
     * @return favorite_count - 收藏总数
     */
    public Long getFavoriteCount() {
        return favoriteCount;
    }

    /**
     * 设置收藏总数
     *
     * @param favoriteCount 收藏总数
     */
    public void setFavoriteCount(Long favoriteCount) {
        this.favoriteCount = favoriteCount;
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

    /**
     * 获取md文件源码
     *
     * @return content - md文件源码
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置md文件源码
     *
     * @param content md文件源码
     */
    public void setContent(String content) {
        this.content = content;
    }
}