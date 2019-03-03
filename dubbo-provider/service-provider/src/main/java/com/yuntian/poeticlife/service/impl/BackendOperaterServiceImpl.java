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
    public List<Menu> getMenuListByOperater(Long operaterId) {
        return menuService.findAll();
    }

    @Override
    public List<MenuTreeVO> getMenuTreeVOListByOperater(Long operaterId) {
        List<Menu> menuList=getMenuListByOperater(operaterId);
        List<MenuTreeVO> menuTreeVOList = new ArrayList<>();
        for (Menu menu : menuList) {
            MenuTreeVO menuTreeVO = new MenuTreeVO();
            try {
                BeanUtils.copyProperties(menuTreeVO, menu);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            menuTreeVOList.add(menuTreeVO);
        }
        getTreeMenu(menuTreeVOList);
        //删除掉所以不为顶级目录的元素
        menuTreeVOList.removeIf(roleAuthVO -> roleAuthVO.getMenuParentId() != null && roleAuthVO.getMenuParentId() != 0);
        return menuTreeVOList;
    }

    private void getTreeMenu(List<MenuTreeVO> list) {
        for (MenuTreeVO menuTreeVO : list) {
            for (MenuTreeVO child : list) {
                List<MenuTreeVO> menuVOList;
                if (menuTreeVO.getChildList() == null) {
                    menuVOList = new ArrayList<>();
                    menuTreeVO.setChildList(menuVOList);
                } else {
                    menuVOList = menuTreeVO.getChildList();
                }
                if (menuTreeVO.getId().equals(child.getMenuParentId())) {
                    menuVOList.add(child);
                }
            }
        }
    }

}
