package com.yuntian.poeticlife.service.impl;

import com.yuntian.poeticlife.AssertUtil;
import com.yuntian.poeticlife.core.AbstractService;
import com.yuntian.poeticlife.dao.MenuMapper;
import com.yuntian.poeticlife.exception.BusinessException;
import com.yuntian.poeticlife.model.entity.Menu;
import com.yuntian.poeticlife.service.MenuService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.annotation.Resource;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;


/**
 * Created by CodeGenerator on 2019/02/26.
 */
@Service("menuService")
public class MenuServiceImpl extends AbstractService<Menu> implements MenuService {

    @Resource
    private MenuMapper menuMapper;


    @Override
    public Menu findById(Long id) {
        Condition condition = new Condition(Menu.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("id", id);
        criteria.andEqualTo("isDelete", 0);
        criteria.andEqualTo("isFrozen", 0);
        List<Menu> menuList = findByCondition(condition);
        AssertUtil.isNotEmpty(menuList, "不存在该菜单");
        return menuList.get(0);
    }


    @Override
    public List<Menu> findChildMenusById(Long id) {
        Condition condition = new Condition(Menu.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("pid", id);
        return findByCondition(condition);
    }

    @Override
    public List<Menu> findAll() {
        Condition condition = new Condition(Menu.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("isDelete", 0);
        criteria.andEqualTo("isFrozen", 0);
        return findByCondition(condition);
    }


    @Override
    public void save(Menu model) {
        AssertUtil.isNotNull(model.getPid(), "父级id不能为空");
        AssertUtil.isNotNull(model.getPid() < 0, "父级id参数有问题");
        AssertUtil.isNotBlank(model.getMenuName(), "菜单名字不能为空");
        AssertUtil.isNotBlank(model.getMenuUrl(), "菜单值不能为空");
        AssertUtil.isNotNull(model.getMenuType(), "菜单类型不能为空");
        AssertUtil.isNotNull(model.getCreateBy(), "创建人不能为空");
        AssertUtil.isNotNull(model.getUpdateBy(), "更新人不能为空");

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
    public void update(Menu model) {
        AssertUtil.isNotNull(model.getId(), "菜单id不能为空");
        AssertUtil.isNotBlank(model.getMenuName(), "菜单名字不能为空");
        AssertUtil.isNotBlank(model.getMenuUrl(), "菜单值不能为空");
        AssertUtil.isNotNull(model.getCreateBy(), "创建人不能为空");
        AssertUtil.isNotNull(model.getUpdateBy(), "更新人不能为空");
        findById(model.getId());
        super.update(model);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Long id) {
        List<Menu> list = findChildMenusById(id);
        AssertUtil.isEmpty(list, "存在子菜单，不能删除");
        Menu menu = new Menu();
        menu.setId(id);
        menu.setIsDelete((byte) 1);
        super.update(menu);
    }


    @Override
    public void isEnableMenu(Long id) {
        Menu menu = new Menu();
        menu.setId(id);
        menu.setIsFrozen((byte) 0);
        super.update(menu);
    }

    @Override
    public void isStopMenu(Long id) {
        Menu menu = new Menu();
        menu.setId(id);
        menu.setIsFrozen((byte) 1);
        super.update(menu);
    }


}
