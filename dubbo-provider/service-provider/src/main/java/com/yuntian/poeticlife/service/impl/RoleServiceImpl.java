package com.yuntian.poeticlife.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuntian.poeticlife.util.AssertUtil;
import com.yuntian.poeticlife.core.AbstractService;
import com.yuntian.poeticlife.dao.RoleMapper;
import com.yuntian.poeticlife.exception.BusinessException;
import com.yuntian.poeticlife.model.dto.RoleDTO;
import com.yuntian.poeticlife.model.entity.Role;
import com.yuntian.poeticlife.model.vo.PageInfoVo;
import com.yuntian.poeticlife.service.OperaterRoleService;
import com.yuntian.poeticlife.service.RoleMenuService;
import com.yuntian.poeticlife.service.RoleService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;


/**
 * Created by CodeGenerator on 2019/02/26.
 */
@Service("roleService")
public class RoleServiceImpl extends AbstractService<Role> implements RoleService {
    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleMenuService roleMenuService;

    @Resource
    private OperaterRoleService operaterRoleService;

    @Override
    public List<Role>  findEnableRoleList() {
        Condition condition = new Condition(Role.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("isDelete", 0);
        criteria.andEqualTo("roleStatus", 1);
        return findByCondition(condition);
    }


    @Override
    public Role findById(Long id) {
        Condition condition = new Condition(Role.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("id", id);
        criteria.andEqualTo("isDelete", 0);
        List<Role> roleList = findByCondition(condition);
        if (CollectionUtils.isNotEmpty(roleList)) {
            return roleList.get(0);
        }
        return null;
    }

    @Override
    public void save(Role model) {
        AssertUtil.isNotNull(model, "参数不能为空");
        AssertUtil.isNotNull(model.getRoleName(), "角色名不能为空");
        AssertUtil.isNotNull(model.getcreateId(), "创建人不能为空");
        AssertUtil.isNotNull(model.getupdateId(), "更新人不能为空");
        String roleName = StringUtils.trim(model.getRoleName());
        model.setRoleName(roleName);
        Role role = findRoleByName(model.getRoleName());
        if (!Objects.isNull(role)) {
            BusinessException.throwMessage("已经存在该角色");
        }
        super.save(model);
    }


    @Override
    public void update(Role model) {
        AssertUtil.isNotNull(model, "参数不能为空");
        AssertUtil.isNotNull(model.getId(), "角色id不能为空");
        AssertUtil.isNotNull(model.getRoleName(), "角色名不能为空");
        AssertUtil.isNotNull(model.getupdateId(), "更新人不能为空");
        Role role = findById(model.getId());
        if (Objects.isNull(role)) {
            BusinessException.throwMessage("不存在该角色,请刷新页面");
        }
        super.update(model);
    }


    @Override
    public void isEnable(Role model) {
        AssertUtil.isNotNull(model, "参数不能为空");
        AssertUtil.isNotNull(model.getId(), "角色id不能为空");
        AssertUtil.isNotNull(model.getupdateId(), "更新人不能为空");
        model.setRoleStatus(1);
        super.update(model);
    }

    @Override
    public void isStop(Role model) {
        AssertUtil.isNotNull(model, "参数不能为空");
        AssertUtil.isNotNull(model.getId(), "角色id不能为空");
        AssertUtil.isNotNull(model.getupdateId(), "更新人不能为空");

        List<Long> operaterIdList= operaterRoleService.getOperaterIdListByRoleId(model.getId());
        if (CollectionUtils.isNotEmpty(operaterIdList)){
            BusinessException.throwMessage("角色下关联着用户,不能禁用");
        }
        model.setRoleStatus(0);
        super.update(model);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Long id) {
        Role role = findById(id);
        if (Objects.isNull(role)) {
            BusinessException.throwMessage("角色不存在，请刷新页面");
        }
        List<Long> operaterIdList= operaterRoleService.getOperaterIdListByRoleId(id);
        if (CollectionUtils.isNotEmpty(operaterIdList)){
            BusinessException.throwMessage("角色下关联着用户,不能删除");
        }
        roleMenuService.deleteByRoleId(role.getId());
        role.setIsDelete((byte) 1);
        super.update(role);
    }


    @Override
    public Role findRoleByName(String roleName) {
        Condition condition = new Condition(Role.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("isDelete", 0);
        criteria.andEqualTo("roleName", roleName);
        List<Role> roleList = findByCondition(condition);
        if (CollectionUtils.isNotEmpty(roleList)) {
            return roleList.get(0);
        }
        return null;
    }

    @Override
    public PageInfoVo<Role> queryRoleListByPage(RoleDTO roleDTO) {
        AssertUtil.isNotNull(roleDTO, "参数不能为空");
        PageHelper.startPage(roleDTO.getPageNum(), roleDTO.getPageSize());
        Condition condition = new Condition(Role.class);
        condition.orderBy("updateTime").desc();
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("isDelete", 0);
        List<Role> roleList = findByCondition(condition);
        return new PageInfoVo<>(new PageInfo<>(roleList));
    }


}
