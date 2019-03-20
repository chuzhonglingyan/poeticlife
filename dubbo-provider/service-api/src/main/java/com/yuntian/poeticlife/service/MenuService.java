package com.yuntian.poeticlife.service;

import com.yuntian.poeticlife.core.Service;
import com.yuntian.poeticlife.model.entity.Menu;
import com.yuntian.poeticlife.model.vo.MenuTreeVO;

import java.util.List;


/**
 * Created by CodeGenerator on 2019/02/26.
 */
public interface MenuService extends Service<Menu> {

    public List<Menu> findEnableMenus();

    List<Menu> findChildMenusById(Long id);

    List<Menu> findEnableMenuByOperaterId(Long operaterId);

    Menu findEnableMenuById(Long id);


    void isEnableMenu(Long id);

    void isStopMenu(Long id);

    public List<MenuTreeVO> getMenuTreeVOList(List<Menu> menuList);


}
