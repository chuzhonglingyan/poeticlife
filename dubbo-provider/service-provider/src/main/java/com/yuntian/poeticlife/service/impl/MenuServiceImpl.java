package com.yuntian.poeticlife.service.impl;

import com.yuntian.poeticlife.core.AbstractService;
import com.yuntian.poeticlife.dao.MenuMapper;
import com.yuntian.poeticlife.exception.BusinessException;
import com.yuntian.poeticlife.model.dto.MenuDTO;
import com.yuntian.poeticlife.model.entity.Article;
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
public class MenuServiceImpl extends AbstractService<MenuDTO,Menu> implements MenuService {


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
    public void saveByDTO(MenuDTO dto) {

    }

    @Override
    public void deleteByDTO(MenuDTO dto) {

    }

    @Override
    public void updateByDTO(MenuDTO dto) {

    }

    @Override
    public void save(Menu model) {
        AssertUtil.isNotNull(model.getPid(), "父级id不能为空");
        AssertUtil.isNotNull(model.getPid() < 0, "父级id参数有问题");
        AssertUtil.isNotBlank(model.getMenuName(), "菜单名字不能为空");
        AssertUtil.isNotBlank(model.getMenuUrl(), "菜单值不能为空");
        AssertUtil.isNotBlank(model.getMenuCode(), "菜单权限不能为空");
        AssertUtil.isNotNull(model.getMenuType(), "菜单类型不能为空");
        AssertUtil.isNotNull(model.getcreateId(), "创建人不能为空");
        AssertUtil.isNotNull(model.getUpdateId(), "更新人不能为空");

        if (model.getPid() == 0) {
            model.setMenuLevel((byte) 1);
        } else {
            Menu parentMenu = findById(model.getPid());
            model.setMenuLevel((byte) (parentMenu.getMenuLevel() + 1));
            if (parentMenu.getMenuType() == 2) {
                BusinessException.throwMessage("操作类型菜单不能添加子级菜单");
            }
        }
        super.save(model);
    }

    @Override
    public int update(Menu model) {
        AssertUtil.isNotNull(model.getId(), "菜单id不能为空");
        AssertUtil.isNotBlank(model.getMenuName(), "菜单名字不能为空");
        AssertUtil.isNotBlank(model.getMenuUrl(), "菜单值不能为空");
        AssertUtil.isNotBlank(model.getMenuCode(), "菜单权限不能为空");
        AssertUtil.isNotNull(model.getcreateId(), "创建人不能为空");
        AssertUtil.isNotNull(model.getUpdateId(), "更新人不能为空");
        Menu  menuTemp=findById(model.getId());
        if (menuTemp==null){
            BusinessException.throwMessage("该菜单不存在");
        }
        menuTemp.setMenuName(model.getMenuName());
        menuTemp.setMenuUrl(model.getMenuUrl());
        menuTemp.setMenuCode(model.getMenuCode());
        menuTemp.setUpdateId(model.getUpdateId());
        return super.update(menuTemp);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Long id) {
        Menu menuVO = findById(id);
        if (Objects.isNull(menuVO)) {
            BusinessException.throwMessage("菜单不存在，请刷新页面");
        }
        if (menuVO.getMenuStatus() == 1) {
            BusinessException.throwMessage("菜单处于冻结状态，无法删除.");
        }
        List<Menu> list = findChildMenusById(id);
        AssertUtil.isEmpty(list, "存在子菜单，不能删除");
        menuVO.setIsDelete((byte) 1);
        super.update(menuVO);
    }


    @Override
    public void isEnableMenu(Long id) {
        Menu menu = new Menu();
        menu.setId(id);
        menu.setMenuStatus((byte) 1);
        super.update(menu);
    }

    @Override
    public void isStopMenu(Long id) {
        Menu menu = new Menu();
        menu.setId(id);
        menu.setMenuStatus((byte) 0);
        super.update(menu);
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
                List<MenuTreeVO> menuVOList;
                if (menuTreeVO.getChildList() == null) {
                    menuVOList = new ArrayList<>();
                    menuTreeVO.setChildList(menuVOList);
                } else {
                    menuVOList = menuTreeVO.getChildList();
                }
                if (menuTreeVO.getId().equals(child.getPid())) {
                    menuVOList.add(child);
                }
            }
        }
    }

}
