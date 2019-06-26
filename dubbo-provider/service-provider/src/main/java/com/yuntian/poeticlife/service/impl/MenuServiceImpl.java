package com.yuntian.poeticlife.service.impl;

import com.yuntian.poeticlife.core.AbstractService;
import com.yuntian.poeticlife.dao.MenuMapper;
import com.yuntian.poeticlife.exception.BusinessException;
import com.yuntian.poeticlife.model.dto.MenuDTO;
import com.yuntian.poeticlife.model.entity.Menu;
import com.yuntian.poeticlife.model.vo.MenuTreeVO;
import com.yuntian.poeticlife.model.vo.PageInfoVo;
import com.yuntian.poeticlife.service.MenuService;
import com.yuntian.poeticlife.service.OperaterRoleService;
import com.yuntian.poeticlife.service.RoleMenuService;
import com.yuntian.poeticlife.util.AssertUtil;
import com.yuntian.poeticlife.util.CglibBeanCopierUtils;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;


/**
 * Created by CodeGenerator on 2019/02/26.
 */
@Service("menuService")
public class MenuServiceImpl extends AbstractService<MenuDTO, Menu> implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private OperaterRoleService operaterRoleService;

    @Resource
    private RoleMenuService roleMenuService;


    @Override
    public Menu findById(Long id) {
        Condition condition = new Condition(Menu.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("id", id);
        criteria.andEqualTo("isDelete", 0);
        List<Menu> menuList = findByCondition(condition);
        if (CollectionUtils.isNotEmpty(menuList)) {
            return menuList.get(0);
        }
        return null;
    }



    @Override
    public List<Menu> findChildMenusById(Long id) {
        Condition condition = new Condition(Menu.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("pid", id);
        criteria.andEqualTo("isDelete", 0);
        return findByCondition(condition);
    }

    @Override
    public Menu findEnableMenuById(Long id) {
        Condition condition = new Condition(Menu.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("id", id);
        criteria.andEqualTo("isDelete", 0);
        criteria.andEqualTo("menuStatus", 1);
        List<Menu> menuList = findByCondition(condition);
        if (CollectionUtils.isNotEmpty(menuList)) {
            return menuList.get(0);
        }
        return null;
    }

    /**
     * 查找用户所用户的权限菜单
     *
     * @param operaterId
     * @return
     */
    @Override
    public List<Menu> findEnableMenuByOperaterId(Long operaterId) {
        List<Long> roleIdList = operaterRoleService.getRoleIdListByOperaterId(operaterId);
        if (CollectionUtils.isNotEmpty(roleIdList)) {
            Long roleId = roleIdList.get(0);
            List<Long> menuIds = roleMenuService.getMenuIdListByRoleId(roleId);
            if (CollectionUtils.isNotEmpty(menuIds)) {
                Condition condition = new Condition(Menu.class);
                Example.Criteria criteria = condition.createCriteria();
                criteria.andIn("id", menuIds);
                criteria.andEqualTo("isDelete", 0);
                criteria.andEqualTo("menuStatus", 1);
                return findByCondition(condition);
            }
        }
        return null;
    }


    @Override
    public List<Menu> findEnableMenus() {
        Condition condition = new Condition(Menu.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("isDelete", 0);
        criteria.andEqualTo("menuStatus", 1);
        return findByCondition(condition);
    }

    @Override
    public List<Menu> findAll() {
        Condition condition = new Condition(Menu.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("isDelete", 0);
        return findByCondition(condition);
    }

    @Override
    public PageInfoVo<Menu> queryListByPage(MenuDTO dto) {
        return null;
    }


    @Override
    public void saveByDTO(Menu dto) {
        AssertUtil.isNotNull(dto.getPid(), "父级id不能为空");
        AssertUtil.isNotNull(dto.getPid() < 0, "父级id参数有问题");
        AssertUtil.isNotBlank(dto.getMenuName(), "菜单名字不能为空");
        AssertUtil.isNotBlank(dto.getMenuUrl(), "菜单值不能为空");
        AssertUtil.isNotBlank(dto.getMenuCode(), "菜单权限不能为空");
        AssertUtil.isNotNull(dto.getMenuType(), "菜单类型不能为空");
        AssertUtil.isNotNull(dto.getcreateId(), "创建人不能为空");
        AssertUtil.isNotNull(dto.getUpdateId(), "更新人不能为空");

        if (dto.getPid() == 0) {
            dto.setMenuLevel((byte) 1);
        } else {
            Menu parentMenu = findById(dto.getPid());
            dto.setMenuLevel((byte) (parentMenu.getMenuLevel() + 1));
            if (parentMenu.getMenuType() == 2) {
                BusinessException.throwMessage("操作类型菜单不能添加子级菜单");
            }
        }
        save(dto);
    }



    @Override
    public void updateByDTO(Menu dto) {
        AssertUtil.isNotNull(dto.getId(), "菜单id不能为空");
        AssertUtil.isNotBlank(dto.getMenuName(), "菜单名字不能为空");
        AssertUtil.isNotBlank(dto.getMenuUrl(), "菜单值不能为空");
        AssertUtil.isNotBlank(dto.getMenuCode(), "菜单权限不能为空");
        AssertUtil.isNotNull(dto.getUpdateId(), "更新人不能为空");
        Menu  menuTemp=findById(dto.getId());
        if (menuTemp==null){
            BusinessException.throwMessage("该菜单不存在");
        }
        int count=update(dto);
        if(count!=1){
            BusinessException.throwMessage("更新该菜单失败,请刷新页面");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByDTO(Menu dto) {
        Menu Menu = findById(dto.getId());
        if (Objects.isNull(Menu)) {
            BusinessException.throwMessage("菜单不存在，请刷新页面");
        }
        if (Menu.getMenuStatus() == 1) {
            BusinessException.throwMessage("菜单处于冻结状态，无法删除.");
        }
        List<Menu> list = findChildMenusById(dto.getId());
        AssertUtil.isEmpty(list, "存在子菜单，不能删除");
        Menu.setIsDelete((byte) 1);
        super.update(dto);
    }


    @Override
    public void isEnable(Menu dto) {
        dto.setMenuStatus((byte) 1);
        super.update(dto);
    }

    @Override
    public void isStop(Menu dto) {
        dto.setMenuStatus((byte) 0);
        super.update(dto);
    }

    @Override
    public List<MenuTreeVO> getMenuTreeVOList(List<Menu> menuList) {
        List<MenuTreeVO> menuTreeVOList = new ArrayList<>();
        for (Menu menu : menuList) {
            MenuTreeVO menuTreeVO = new MenuTreeVO();
            CglibBeanCopierUtils.copyProperties(menu, menuTreeVO);
            menuTreeVOList.add(menuTreeVO);
        }
        getTreeMenu(menuTreeVOList);
        //删除掉所以不为顶级目录的元素
        menuTreeVOList.removeIf(roleAuthVO -> roleAuthVO.getPid() != null && roleAuthVO.getPid() != 0);
        return menuTreeVOList;
    }

    private void getTreeMenu(List<MenuTreeVO> list) {
        for (MenuTreeVO menuTreeVO : list) {
            for (MenuTreeVO child : list) {
                List<MenuTreeVO> MenuList;
                if (menuTreeVO.getChildList() == null) {
                    MenuList = new ArrayList<>();
                    menuTreeVO.setChildList(MenuList);
                } else {
                    MenuList = menuTreeVO.getChildList();
                }
                if (menuTreeVO.getId().equals(child.getPid())) {
                    MenuList.add(child);
                }
            }
        }
    }


}
