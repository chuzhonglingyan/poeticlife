package com.yuntian.poeticlife.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuntian.basecommon.util.PasswordUtil;
import com.yuntian.poeticlife.core.AbstractService;
import com.yuntian.poeticlife.dao.BackendOperaterMapper;
import com.yuntian.poeticlife.exception.BusinessException;
import com.yuntian.poeticlife.model.dto.BackendOperaterDTO;
import com.yuntian.poeticlife.model.entity.BackendOperater;
import com.yuntian.poeticlife.model.entity.Menu;
import com.yuntian.poeticlife.model.entity.Role;
import com.yuntian.poeticlife.model.vo.MenuTreeVO;
import com.yuntian.poeticlife.model.vo.PageInfoVo;
import com.yuntian.poeticlife.service.BackendOperaterService;
import com.yuntian.poeticlife.service.MenuService;
import com.yuntian.poeticlife.service.OperaterRoleService;
import com.yuntian.poeticlife.service.RoleService;
import com.yuntian.poeticlife.util.AssertUtil;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;


/**
 * Created by CodeGenerator on 2019/02/23.
 */
@Service("backendOperaterService")
public class BackendOperaterServiceImpl extends AbstractService<BackendOperaterDTO, BackendOperater> implements BackendOperaterService {

    @Resource
    private OperaterRoleService operaterRoleService;

    @Resource
    private RoleService roleService;

    @Resource
    private BackendOperaterMapper backendOperaterMapper;

    @Resource
    private MenuService menuService;


    @Override
    public PageInfoVo<BackendOperater> queryListByPage(BackendOperaterDTO dto) {
        AssertUtil.isNotNull(dto, "参数不能为空");
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        Condition condition = new Condition(BackendOperater.class);
        condition.orderBy("updateTime").desc();
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("isDelete", 0);
        List<BackendOperater> list = findByCondition(condition);
        return new PageInfoVo<>(new PageInfo<>(list));
    }


    @Override
    public void saveByDTO(BackendOperater dto) {
        AssertUtil.isNotNull(dto, "参数不能为空");
        AssertUtil.isNotNull(dto.getAccountName(), "账号不能为空");
        AssertUtil.isNotNull(dto.getUserName(), "用户名不能为空");
        AssertUtil.isNotNull(dto.getEmail(), "邮箱不能为空");
        AssertUtil.isNotNull(dto.getcreateId(), "创建人不能为空");
        AssertUtil.isNotNull(dto.getUpdateId(), "更新人不能为空");
        BackendOperater backendOperater = findOperaterByAccount(dto.getAccountName());
        if (!Objects.isNull(backendOperater)) {
            BusinessException.throwMessage("已经存在该账号");
        }
        //设置，默认密码
        dto.setPassWord(PasswordUtil.md5HexWithSalt("123456"));
        save(dto);
    }


    @Override
    public void updateByDTO(BackendOperater dto) {
        AssertUtil.isNotNull(dto, "参数不能为空");
        AssertUtil.isNotNull(dto.getId(), "用户id不能为空");
        AssertUtil.isNotNull(dto.getUpdateId(), "更新人不能为空");
        BackendOperater backendOperater = findById(dto.getId());
        if (Objects.isNull(backendOperater)) {
            BusinessException.throwMessage("不存在该用户");
        }
        if (StringUtils.isBlank(dto.getPassWord())) {
            dto.setPassWord(null);
        } else {
            dto.setPassWord(PasswordUtil.md5HexWithSalt(dto.getPassWord()));
        }
        int count = update(dto);
        if (count != 1) {
            BusinessException.throwMessage("更新该用户失败,请刷新页面");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByDTO(BackendOperater dto) {
        AssertUtil.isNotNull(dto, "参数不能为空");
        AssertUtil.isNotNull(dto.getId(), "文章id不能为空");
        dto.setIsDelete((byte) 1);
        int count = update(dto);
        if (count != 1) {
            BusinessException.throwMessage("删除该用户失败,请刷新页面");
        }
    }


    @Override
    public BackendOperater findById(Long id) {
        Condition condition = new Condition(BackendOperater.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("id", id);
        criteria.andEqualTo("isDelete", 0);
        List<BackendOperater> list = findByCondition(condition);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }


    @Override
    public BackendOperater findOperaterByAccount(String accountName) {
        Condition condition = new Condition(BackendOperater.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("isDelete", 0);
        criteria.andEqualTo("accountName", accountName);
        List<BackendOperater> list = findByCondition(condition);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }


    @Override
    public void isEnable(BackendOperater dto) {
        AssertUtil.isNotNull(dto, "参数不能为空");
        AssertUtil.isNotNull(dto.getId(), "用户id不能为空");
        AssertUtil.isNotNull(dto.getUpdateId(), "更新人不能为空");
        dto.setIsEnable((byte) 1);
        updateByDTO(dto);
    }

    @Override
    public void isStop(BackendOperater dto) {
        AssertUtil.isNotNull(dto, "参数不能为空");
        AssertUtil.isNotNull(dto.getId(), "用户id不能为空");
        AssertUtil.isNotNull(dto.getUpdateId(), "更新人不能为空");
        dto.setIsEnable((byte) 0);
        updateByDTO(dto);
    }


    @Override
    public String getRole(Long operaterId) {
        List<Long> list = operaterRoleService.getRoleIdListByOperaterId(operaterId);
        if (CollectionUtils.isNotEmpty(list)) {
            Role role = roleService.findBy("id", list.get(0));
            return role.getRole();
        }
        return null;
    }

    @Override
    public Long getRoleId(Long operaterId) {
        List<Long> list = operaterRoleService.getRoleIdListByOperaterId(operaterId);
        if (CollectionUtils.isNotEmpty(list)) {
            Role role = roleService.findBy("id", list.get(0));
            return role.getId();
        }
        return null;
    }


    @Override
    public List<Menu> getNavMenuListByOperater(Long operaterId) {
        return menuService.findEnableMenuByOperaterId(operaterId);
    }

    @Override
    public List<MenuTreeVO> getMenuTreeVOListByOperater(Long operaterId) {
        List<Menu> menuList = getNavMenuListByOperater(operaterId);
        if (CollectionUtils.isEmpty(menuList)) {
            return new ArrayList<>();
        }
        return menuService.getMenuTreeVOList(menuList);
    }


}
