package com.yuntian.poeticlife.model.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "role_menu")
public class RoleMenu {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 菜单ID
     */
    @Column(name = "menu_id")
    private Long menuId;

    /**
     * 是否选中（0-未选中 1-选中 树形结构父节点处于半选中状态表示未选中）
     */
    @Column(name = "is_checked")
    private Byte isChecked;

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
     * 获取主键ID
     *
     * @return id - 主键ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键ID
     *
     * @param id 主键ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取角色ID
     *
     * @return role_id - 角色ID
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * 设置角色ID
     *
     * @param roleId 角色ID
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取菜单ID
     *
     * @return menu_id - 菜单ID
     */
    public Long getMenuId() {
        return menuId;
    }

    /**
     * 设置菜单ID
     *
     * @param menuId 菜单ID
     */
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    /**
     * 获取是否选中（0-未选中 1-选中 树形结构父节点处于半选中状态表示未选中）
     *
     * @return is_checked - 是否选中（0-未选中 1-选中 树形结构父节点处于半选中状态表示未选中）
     */
    public Byte getIsChecked() {
        return isChecked;
    }

    /**
     * 设置是否选中（0-未选中 1-选中 树形结构父节点处于半选中状态表示未选中）
     *
     * @param isChecked 是否选中（0-未选中 1-选中 树形结构父节点处于半选中状态表示未选中）
     */
    public void setIsChecked(Byte isChecked) {
        this.isChecked = isChecked;
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
}