package com.yuntian.poeticlife;

import com.alibaba.fastjson.JSON;
import com.yuntian.poeticlife.model.entity.Menu;
import com.yuntian.poeticlife.model.vo.MenuTreeVO;
import com.yuntian.poeticlife.service.MenuService;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: yuntian
 * @Date: 2019/2/27 0027 21:59
 * @Description:
 */
@Slf4j
public class MenuTest extends BaseTest {

    @Resource
    private MenuService menuService;


    @Test
    public void test() {
        Menu menu = new Menu();

        menu.setPid(4L);
        menu.setMenuLevel((byte) 2);
        menu.setMenuName("根目录");
        menu.setCreateId(1L);
        menu.setUpdateId(1L);

        menuService.save(menu);
    }

    @Test
    public void test1()   {
        List<Menu> list = menuService.findAll();
        List<MenuTreeVO> menuTreeVOList = new ArrayList<>();
        for (Menu menu : list) {
            MenuTreeVO menuTreeVO = new MenuTreeVO();
            BeanUtils.copyProperties(menuTreeVO, menu);
            menuTreeVOList.add(menuTreeVO);
        }
        System.out.println(JSON.toJSON(menuTreeVOList));

        List<MenuTreeVO> menuTreeVO=getTreeMenu(menuTreeVOList);
        System.out.println(JSON.toJSON( menuTreeVO));

        List<MenuTreeVO> list2=new ArrayList<>();

        List<Menu> list3=new ArrayList<>();
        getListMenu(menuTreeVO,list2);
        for (MenuTreeVO menuTreeVOBean : list2) {
            Menu menu=new Menu();
            BeanUtils.copyProperties(menu, menuTreeVOBean);
            list3.add(menu);
        }
        System.out.println(JSON.toJSON(list3));
    }


    public List<MenuTreeVO> getTreeMenu(List<MenuTreeVO> list) {
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
        return list;
    }

//    public void getListMenu(MenuTreeVO menuTreeVO,List<MenuTreeVO> list) {
//        if (menuTreeVO.getPid()==0){
//            list.add(menuTreeVO);
//        }
//        List<MenuTreeVO> treeVOList=menuTreeVO.getChildList();
//        for (MenuTreeVO item: treeVOList) {
//            list.add(item);
//            if (CollectionUtils.isNotEmpty(item.getChildList())){
//                getListMenu(item,list);
//            }
//        }
//
//    }

    public void getListMenu(List<MenuTreeVO> menuTreeVO,List<MenuTreeVO> list) {
        for (MenuTreeVO item: menuTreeVO) {
            list.add(item);
            if (CollectionUtils.isNotEmpty(item.getChildList())){
                getListMenu(item.getChildList(),list);
            }
        }

    }


    @Test
    public void  findChildMenusById(){
        System.out.println(JSON.toJSON(menuService.findChildMenusById(3L)));
    }


    @Test
    public void  deleteById(){
        menuService.deleteById(3L);
    }
}
