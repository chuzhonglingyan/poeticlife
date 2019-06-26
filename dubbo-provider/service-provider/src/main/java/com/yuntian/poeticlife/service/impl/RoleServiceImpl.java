package com.yuntian.poeticlife.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuntian.poeticlife.core.AbstractService;
import com.yuntian.poeticlife.dao.RoleMapper;
import com.yuntian.poeticlife.exception.BusinessException;
import com.yuntian.poeticlife.model.dto.RoleDTO;
import com.yuntian.poeticlife.model.entity.Role;
import com.yuntian.poeticlife.model.vo.PageInfoVo;
import com.yuntian.poeticlife.service.OperaterRoleService;
import com.yuntian.poeticlife.service.RoleMenuService;
import com.yuntian.poeticlife.service.RoleService;
import com.yuntian.poeticlife.util.AssertUtil;

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
public class RoleServiceImpl extends AbstractService<RoleDTO, Role> implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleMenuService roleMenuService;

    @Resource
    private OperaterRoleService operaterRoleService;

    @Override
    public List<Role> findEnableList() {
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
    public PageInfoVo<Role> queryListByPage(RoleDTO dto) {
        return null;
    }

    @Override
    public void saveByDTO(Role dto) {
        AssertUtil.isNotNull(dto, "参数不能为空");
        AssertUtil.isNotNull(dto.getRoleName(), "角色名不能为空");
        AssertUtil.isNotNull(dto.getCreateId(), "创建人不能为空");
        AssertUtil.isNotNull(dto.getUpdateId(), "更新人不能为空");
        String roleName = StringUtils.trim(dto.getRoleName());
        dto.setRoleName(roleName);
        Role role = findRoleByName(dto.getRoleName());
        if (!Objects.isNull(role)) {
            BusinessException.throwMessage("已经存在该角色");
        }
        super.save(dto);
    }


    @Override
    public void updateByDTO(Role dto) {
        AssertUtil.isNotNull(dto, "参数不能为空");
        AssertUtil.isNotNull(dto.getId(), "角色id不能为空");
        AssertUtil.isNotNull(dto.getRoleName(), "角色名不能为空");
        AssertUtil.isNotNull(dto.getUpdateId(), "更新人不能为空");
        Role role = findById(dto.getId());
        if (Objects.isNull(role)) {
            BusinessException.throwMessage("不存在该角色,请刷新页面");
        }
    }


    @Override
    public void isEnable(Role dto) {
        AssertUtil.isNotNull(dto, "参数不能为空");
        AssertUtil.isNotNull(dto.getId(), "角色id不能为空");
        AssertUtil.isNotNull(dto.getUpdateId(), "更新人不能为空");
        dto.setRoleStatus(1);
        super.update(dto);
    }

    @Override
    public void isStop(Role dto) {
        AssertUtil.isNotNull(dto, "参数不能为空");
        AssertUtil.isNotNull(dto.getId(), "角色id不能为空");
        AssertUtil.isNotNull(dto.getUpdateId(), "更新人不能为空");
        List<Long> operaterIdList = operaterRoleService.getOperaterIdListByRoleId(dto.getId());
        if (CollectionUtils.isNotEmpty(operaterIdList)) {
            BusinessException.throwMessage("角色下关联着用户,不能禁用");
        }
        dto.setRoleStatus(0);
        super.update(dto);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByDTO(Role dto) {
        Role role = findById(dto.getId());
        if (Objects.isNull(role)) {
            BusinessException.throwMessage("角色不存在，请刷新页面");
        }
        List<Long> operaterIdList = operaterRoleService.getOperaterIdListByRoleId(dto.getId());
        if (CollectionUtils.isNotEmpty(operaterIdList)) {
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
