package com.yuntian.poeticlife.model.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

public class Menu implements Serializable {


    private static final long serialVersionUID = 5890598419923742867L;
    /**
     * 菜单ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 授权(多个用逗号分隔，如：user:list,user:create)
     */
    @Column(name = "menu_code")
    private String menuCode;

    /**
     * 菜单名称
     */
    @Column(name = "menu_name")
    private String menuName;

    /**
     * 菜单URL
     */
    @Column(name = "menu_url")
    private String menuUrl;

    /**
     * 菜单类型，1：菜单，2：操作
     */
    @Column(name = "menu_type")
    private Integer menuType;

    /**
     * 父菜单ID
     */
    @Column(name = "menu_parent_id")
    private Long menuParentId;

    /**
     * 菜单等级 1一级菜单  2 二级菜单  3 三级菜单
     */
    @Column(name = "menu_level")
    private Byte menuLevel;

    /**
     * 打开方式 1：框架内打开  2：新开页面
     */
    @Column(name = "open_type")
    private Byte openType;

    /**
     * 是否删除 0:未删除 1:已删除
     */
    @Column(name = "is_delete")
    private Byte isDelete;

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
     * 0：启用  1：冻结
     */
    @Column(name = "is_frozen")
    private Byte isFrozen;

    /**
     * 获取菜单ID
     *
     * @return id - 菜单ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置菜单ID
     *
     * @param id 菜单ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取授权(多个用逗号分隔，如：user:list,user:create)
     *
     * @return menu_code - 授权(多个用逗号分隔，如：user:list,user:create)
     */
    public String getMenuCode() {
        return menuCode;
    }

    /**
     * 设置授权(多个用逗号分隔，如：user:list,user:create)
     *
     * @param menuCode 授权(多个用逗号分隔，如：user:list,user:create)
     */
    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    /**
     * 获取菜单名称
     *
     * @return menu_name - 菜单名称
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * 设置菜单名称
     *
     * @param menuName 菜单名称
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    /**
     * 获取菜单URL
     *
     * @return menu_url - 菜单URL
     */
    public String getMenuUrl() {
        return menuUrl;
    }

    /**
     * 设置菜单URL
     *
     * @param menuUrl 菜单URL
     */
    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    /**
     * 获取菜单类型，1：菜单，2：操作
     *
     * @return menu_type - 菜单类型，1：菜单，2：操作
     */
    public Integer getMenuType() {
        return menuType;
    }

    /**
     * 设置菜单类型，1：菜单，2：操作
     *
     * @param menuType 菜单类型，1：菜单，2：操作
     */
    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    /**
     * 获取父菜单ID
     *
     * @return menu_parent_id - 父菜单ID
     */
    public Long getMenuParentId() {
        return menuParentId;
    }

    /**
     * 设置父菜单ID
     *
     * @param menuParentId 父菜单ID
     */
    public void setMenuParentId(Long menuParentId) {
        this.menuParentId = menuParentId;
    }

    /**
     * 获取菜单等级 1一级菜单  2 二级菜单  3 三级菜单
     *
     * @return menu_level - 菜单等级 1一级菜单  2 二级菜单  3 三级菜单
     */
    public Byte getMenuLevel() {
        return menuLevel;
    }

    /**
     * 设置菜单等级 1一级菜单  2 二级菜单  3 三级菜单
     *
     * @param menuLevel 菜单等级 1一级菜单  2 二级菜单  3 三级菜单
     */
    public void setMenuLevel(Byte menuLevel) {
        this.menuLevel = menuLevel;
    }

    /**
     * 获取打开方式 1：框架内打开  2：新开页面
     *
     * @return open_type - 打开方式 1：框架内打开  2：新开页面
     */
    public Byte getOpenType() {
        return openType;
    }

    /**
     * 设置打开方式 1：框架内打开  2：新开页面
     *
     * @param openType 打开方式 1：框架内打开  2：新开页面
     */
    public void setOpenType(Byte openType) {
        this.openType = openType;
    }

    /**
     * 获取是否删除 0:未删除 1:已删除
     *
     * @return is_delete - 是否删除 0:未删除 1:已删除
     */
    public Byte getIsDelete() {
        return isDelete;
    }

    /**
     * 设置是否删除 0:未删除 1:已删除
     *
     * @param isDelete 是否删除 0:未删除 1:已删除
     */
    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
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
     * 获取0：启用  1：冻结
     *
     * @return is_frozen - 0：启用  1：冻结
     */
    public Byte getIsFrozen() {
        return isFrozen;
    }

    /**
     * 设置0：启用  1：冻结
     *
     * @param isFrozen 0：启用  1：冻结
     */
    public void setIsFrozen(Byte isFrozen) {
        this.isFrozen = isFrozen;
    }
}