package com.yuntian.poeticlife.service;
import com.yuntian.poeticlife.model.entity.Menu;
import com.yuntian.poeticlife.core.Service;

import java.util.List;


/**
 * Created by CodeGenerator on 2019/02/26.
 */
public interface MenuService extends Service<Menu> {



    List<Menu> findChildMenusById(Long  id);


    void isEnableMenu(Long  id);

    void isStopMenu(Long  id);


}
