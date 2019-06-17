package com.yuntian.poeticlife.service.impl;

import com.yuntian.poeticlife.model.entity.Article;
import com.yuntian.poeticlife.model.vo.PageInfoVo;
import com.yuntian.poeticlife.util.AssertUtil;
import com.yuntian.poeticlife.util.CglibBeanCopierUtils;
import com.yuntian.poeticlife.core.AbstractService;
import com.yuntian.poeticlife.dao.RoleMenuMapper;
import com.yuntian.poeticlife.model.dto.RoleMenuDTO;
import com.yuntian.poeticlife.model.entity.Menu;
import com.yuntian.poeticlife.model.entity.RoleMenu;
import com.yuntian.poeticlife.model.vo.MenuVO;
import com.yuntian.poeticlife.service.MenuService;
import com.yuntian.poeticlife.service.RoleMenuService;

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
@Service("roleMenuService")
public class RoleMenuServiceImpl extends AbstractService<RoleMenuDTO,RoleMenu> implements RoleMenuService {
    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private MenuService menuService;

    @Override
    public List<Long> getMenuIdListByRoleId(Long roleId) {
        Condition condition = new Condition(RoleMenu.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("roleId", roleId);
        List<RoleMenu> roleMenuList = findByCondition(condition);
        return roleMenuList.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
    }

    @Override
    public List<MenuVO> getMenuListByRoleId(Long roleId) {
        List<Long> menuIdList = getMenuIdListByRoleId(roleId);
        List<MenuVO> menuVOList = new ArrayList<>();
        List<Menu> menuList=menuService.findEnableMenus();

        for (Menu menu: menuList) {
            MenuVO menuVO=new MenuVO();
            CglibBeanCopierUtils.copyProperties(menu,menuVO);
            menuVOList.add(menuVO);
        }
        Map<Long, MenuVO> menuVOMap = menuVOList.stream().collect(Collectors.toMap(MenuVO::getId, a -> a,(k1, k2)->k1));
        for (Long menuId: menuIdList) {
            if (menuVOMap.containsKey(menuId)){
                MenuVO menu=menuVOMap.get(menuId);
                menu.setCheck(true);
            }
        }
        return menuVOList;
    }

    @Override
    public List<RoleMenu> findByRoleId(Long roleId) {
        Condition condition = new Condition(RoleMenu.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("roleId", roleId);
        return findByCondition(condition);
    }

    @Override
    public void deleteByRoleId(Long roleId) {
        Condition condition = new Condition(RoleMenu.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("roleId", roleId);
        roleMenuMapper.deleteByCondition(condition);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveMenuListByRoleId(RoleMenuDTO roleMenuDTO) {
        AssertUtil.isNotNull(roleMenuDTO, "参数不能为空");
        AssertUtil.isNotNull(roleMenuDTO.getRoleId(), "角色id不能为空");
        AssertUtil.isNotNull(roleMenuDTO.getMenuIdList(), "选择菜单不能为空");
        //删除原先的关联关系
        deleteByRoleId(roleMenuDTO.getRoleId());
        List<Long> list = roleMenuDTO.getMenuIdList();
        List<RoleMenu> roleMenuList = new ArrayList<>();
        for (Long menuId : list) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleMenuDTO.getRoleId());
            roleMenu.setMenuId(menuId);
            roleMenu.setIsChecked((byte) 1);
            roleMenu.setcreateId(roleMenuDTO.getCreateId());
            roleMenu.setupdateId(roleMenuDTO.getUpdateId());
            roleMenu.setCreateTime(new Date());
            roleMenu.setUpdateTime(new Date());
            roleMenuList.add(roleMenu);
        }
        save(roleMenuList);
    }


    @Override
    public void saveByDTO(RoleMenuDTO dto) {

    }

    @Override
    public void deleteByDTO(RoleMenuDTO dto) {

    }

    @Override
    public void updateByDTO(RoleMenuDTO dto) {

    }

    @Override
    public void save(List<RoleMenu> models) {
        AssertUtil.isNotNull(models, "参数不能为空");
        for (RoleMenu roleMenu : models) {
            AssertUtil.isNotNull(roleMenu.getMenuId(), "菜单id不能为空");
        }
        super.save(models);
    }

    @Override
    public PageInfoVo<RoleMenu> queryListByPage(RoleMenuDTO dto) {
        return null;
    }


}
