package com.yuntian.poeticlife.service;
import com.yuntian.poeticlife.model.entity.BackendOperater;
import com.yuntian.poeticlife.core.Service;
import com.yuntian.poeticlife.model.entity.Menu;
import com.yuntian.poeticlife.model.vo.MenuTreeVO;

import java.util.List;


/**
 * Created by CodeGenerator on 2019/02/23.
 */
public interface BackendOperaterService extends Service<BackendOperater> {


    String getRole(Long operaterId);

    List<Menu> getMenuListByOperater(Long operaterId);


    List<MenuTreeVO> getMenuTreeVOListByOperater(Long operaterId);

}
