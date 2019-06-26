package com.yuntian.poeticlife.service;

import com.yuntian.poeticlife.core.Service;
import com.yuntian.poeticlife.model.dto.MenuDTO;
import com.yuntian.poeticlife.model.entity.Menu;
import com.yuntian.poeticlife.model.vo.MenuTreeVO;

import java.util.List;


/**
 * Created by CodeGenerator on 2019/02/26.
 */
public interface MenuService extends Service<MenuDTO, Menu> {

    List<Menu> findEnableMenus();

    List<Menu> findChildMenusById(Long id);

    List<Menu> findEnableMenuByOperaterId(Long operaterId);

    Menu findEnableMenuById(Long id);

    void isEnable(Menu dto);

    void isStop(Menu dto);


    List<MenuTreeVO> getMenuTreeVOList(List<Menu> menuList);


}
