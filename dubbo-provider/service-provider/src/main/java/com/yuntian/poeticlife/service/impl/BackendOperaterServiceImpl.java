package com.yuntian.poeticlife.service.impl;

import com.yuntian.poeticlife.core.AbstractService;
import com.yuntian.poeticlife.dao.BackendOperaterMapper;
import com.yuntian.poeticlife.model.entity.BackendOperater;
import com.yuntian.poeticlife.model.entity.Menu;
import com.yuntian.poeticlife.model.entity.OperaterRole;
import com.yuntian.poeticlife.model.entity.Role;
import com.yuntian.poeticlife.model.vo.MenuTreeVO;
import com.yuntian.poeticlife.service.BackendOperaterService;
import com.yuntian.poeticlife.service.MenuService;
import com.yuntian.poeticlife.service.OperaterRoleService;
import com.yuntian.poeticlife.service.RoleService;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;


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
    public String getRole(Long operaterId) {
        OperaterRole operaterRole= operaterRoleService.findBy("operaterId",operaterId);
        Role role=roleService.findBy("id",operaterRole.getRoleId());
        return role.getRoleName();
    }


    @Resource
    private MenuService menuService;


    @Override
    public List<Menu> getNavMenuListByOperater(Long operaterId) {
        return menuService.findEnableMenus();
    }

    @Override
    public List<MenuTreeVO> getMenuTreeVOListByOperater(Long operaterId) {
        List<Menu> menuList= getNavMenuListByOperater(operaterId);
        return menuService.getMenuTreeVOList(menuList);
    }



}
