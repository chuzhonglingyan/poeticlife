package com.yuntian.poeticlife.service.impl;

import com.yuntian.poeticlife.util.AssertUtil;
import com.yuntian.poeticlife.util.CglibBeanCopierUtils;
import com.yuntian.poeticlife.core.AbstractService;
import com.yuntian.poeticlife.dao.OperaterRoleMapper;
import com.yuntian.poeticlife.model.dto.OperaterRoleDTO;
import com.yuntian.poeticlife.model.entity.OperaterRole;
import com.yuntian.poeticlife.model.entity.Role;
import com.yuntian.poeticlife.model.vo.RoleVO;
import com.yuntian.poeticlife.service.OperaterRoleService;
import com.yuntian.poeticlife.service.RoleService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;


/**
 * Created by CodeGenerator on 2019/02/26.
 */
@Service("operaterRoleService")
public class OperaterRoleServiceImpl extends AbstractService<OperaterRole> implements OperaterRoleService {
    @Resource
    private OperaterRoleMapper operaterRoleMapper;

    @Resource
    private RoleService roleService;


    @Override
    public List<Long> getRoleIdListByOperaterId(Long operaterId) {
        Condition condition = new Condition(OperaterRole.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("operaterId", operaterId);
        List<OperaterRole> list = findByCondition(condition);
        return list.stream().map(OperaterRole::getRoleId).collect(Collectors.toList());
    }

    @Override
    public List<RoleVO> getRoleListByOperaterId(Long operaterId) {
        List<Long> roleIdList = getRoleIdListByOperaterId(operaterId);
        List<RoleVO> roleVOList = new ArrayList<>();
        List<Role> roleList = roleService.findEnableRoleList();

        for (Role role : roleList) {
            RoleVO roleVO = new RoleVO();
            CglibBeanCopierUtils.copyProperties(role,roleVO);
            roleVOList.add(roleVO);
        }
        Map<Long, RoleVO> roleVOMap = roleVOList.stream().collect(Collectors.toMap(RoleVO::getId, a -> a,(k1,k2)->k1));
        for (Long roleId : roleIdList) {
            if (roleVOMap.containsKey(roleId)) {
                RoleVO roleVO = roleVOMap.get(roleId);
                roleVO.setCheck(true);
            }
        }
        return roleVOList;
    }

    @Override
    public List<Long> getOperaterIdListByRoleId(Long roleId) {
        Condition condition = new Condition(OperaterRole.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("roleId", roleId);
        List<OperaterRole> list = findByCondition(condition);
        return list.stream().map(OperaterRole::getOperaterId).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveRoleListByOperaterId(OperaterRoleDTO dto) {
        AssertUtil.isNotNull(dto, "参数不能为空");
        AssertUtil.isNotNull(dto.getOperaterId(), "用户id不能为空");
        //删除原先的关联关系
        deleteOperaterId(dto.getOperaterId());
        List<Long> list = dto.getRoleList();
        if (CollectionUtils.isNotEmpty(list)) {
            List<OperaterRole> operaterRoleList = new ArrayList<>();
            for (Long roleId : list) {
                OperaterRole operaterRole = new OperaterRole();
                operaterRole.setOperaterId(dto.getOperaterId());
                operaterRole.setRoleId(roleId);
                operaterRole.setcreateId(dto.getcreateId());
                operaterRole.setupdateId(dto.getupdateId());
                operaterRole.setCreateTime(new Date());
                operaterRole.setUpdateTime(new Date());
                operaterRoleList.add(operaterRole);
            }
            super.save(operaterRoleList);
        }
    }


    @Override
    public void deleteOperaterId(Long operaterId) {
        Condition condition = new Condition(OperaterRole.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("operaterId", operaterId);
        operaterRoleMapper.deleteByCondition(condition);
    }


}
