package com.yuntian.poeticlife.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuntian.basecommon.util.PasswordUtil;
import com.yuntian.basecommon.util.encrypt.use.MD5Util;
import com.yuntian.poeticlife.AssertUtil;
import com.yuntian.poeticlife.core.AbstractService;
import com.yuntian.poeticlife.dao.BackendOperaterMapper;
import com.yuntian.poeticlife.exception.BusinessException;
import com.yuntian.poeticlife.model.dto.OperaterDTO;
import com.yuntian.poeticlife.model.entity.BackendOperater;
import com.yuntian.poeticlife.model.entity.Menu;
import com.yuntian.poeticlife.model.entity.OperaterRole;
import com.yuntian.poeticlife.model.entity.Role;
import com.yuntian.poeticlife.model.vo.MenuTreeVO;
import com.yuntian.poeticlife.model.vo.MenuVO;
import com.yuntian.poeticlife.model.vo.PageInfoVo;
import com.yuntian.poeticlife.service.BackendOperaterService;
import com.yuntian.poeticlife.service.MenuService;
import com.yuntian.poeticlife.service.OperaterRoleService;
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
 * Created by CodeGenerator on 2019/02/23.
 */
@Service("backendOperaterService")
public class BackendOperaterServiceImpl extends AbstractService<BackendOperater> implements BackendOperaterService {

    @Resource
    private OperaterRoleService operaterRoleService;
    @Resource
    private RoleService roleService;

    @Resource
    private BackendOperaterMapper backendOperaterMapper;


    @Override
    public void save(BackendOperater model) {
        AssertUtil.isNotNull(model, "参数不能为空");
        AssertUtil.isNotNull(model.getAccountName(), "账号不能为空");
        AssertUtil.isNotNull(model.getUserName(), "用户名不能为空");
        AssertUtil.isNotNull(model.getEmail(), "邮箱不能为空");
        AssertUtil.isNotNull(model.getCreateBy(), "创建人不能为空");
        AssertUtil.isNotNull(model.getUpdateBy(), "更新人不能为空");
        BackendOperater backendOperater = findOperaterByAccount(model.getAccountName());
        if (!Objects.isNull(backendOperater)) {
            BusinessException.throwMessage("已经存在该账号");
        }
        //设置，默认密码
        model.setPassWord(MD5Util.encoderByMd5("123456"));
        super.save(model);
    }


    @Override
    public void update(BackendOperater model) {
        AssertUtil.isNotNull(model, "参数不能为空");
        AssertUtil.isNotNull(model.getId(), "用户id不能为空");
        AssertUtil.isNotNull(model.getUserName(), "用户名不能为空");
        AssertUtil.isNotNull(model.getEmail(), "邮箱不能为空");
        AssertUtil.isNotNull(model.getUpdateBy(), "更新人不能为空");

        BackendOperater backendOperater = findById(model.getId());
        if (Objects.isNull(backendOperater)) {
            BusinessException.throwMessage("不存在该用户,请刷新页面");
        }
        if (StringUtils.isBlank(model.getPassWord())){
            model.setPassWord(null);
        }else {
            model.setPassWord(PasswordUtil.md5HexWithSalt(model.getPassWord()));
        }
        super.update(model);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Long id) {
        BackendOperater backendOperater = findById(id);
        if (Objects.isNull(backendOperater)) {
            BusinessException.throwMessage("用户不存在，请刷新页面");
        }
        //todo 要删除和用户的关联关系
        backendOperater.setIsDelete((byte) 1);
        super.update(backendOperater);
    }


    @Override
    public BackendOperater findById(Long id) {
        Condition condition = new Condition(Role.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("id", id);
        criteria.andEqualTo("isDelete", 0);
        List<BackendOperater> roleList = findByCondition(condition);
        if (CollectionUtils.isNotEmpty(roleList)) {
            return roleList.get(0);
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
    public void isEnable(BackendOperater model) {
        AssertUtil.isNotNull(model, "参数不能为空");
        AssertUtil.isNotNull(model.getId(), "用户id不能为空");
        AssertUtil.isNotNull(model.getUpdateBy(), "更新人不能为空");
        model.setIsEnable((byte) 1);
        super.update(model);
    }

    @Override
    public void isStop(BackendOperater model) {
        AssertUtil.isNotNull(model, "参数不能为空");
        AssertUtil.isNotNull(model.getId(), "用户id不能为空");
        AssertUtil.isNotNull(model.getUpdateBy(), "更新人不能为空");
        model.setIsEnable((byte) 0);
        super.update(model);
    }

    @Override
    public PageInfoVo<BackendOperater> queryRoleListByPage(OperaterDTO dto) {
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
    public String getRole(Long operaterId) {
        OperaterRole operaterRole = operaterRoleService.findBy("operaterId", operaterId);
        Role role = roleService.findBy("id", operaterRole.getRoleId());
        return role.getRoleName();
    }


    @Resource
    private MenuService menuService;


    @Override
    public List<Menu> getNavMenuListByOperater(Long operaterId) {
        return menuService.findEnableMenuByOperaterId(operaterId);
    }

    @Override
    public List<MenuTreeVO> getMenuTreeVOListByOperater(Long operaterId) {
        List<Menu> menuList= getNavMenuListByOperater(operaterId);
        return menuService.getMenuTreeVOList(menuList);
    }


}
