package com.yuntian.poeticlife.service.impl;

import com.yuntian.poeticlife.dao.MenuMapper;
import com.yuntian.poeticlife.model.entity.Menu;
import com.yuntian.poeticlife.service.MenuService;
import com.yuntian.poeticlife.core.AbstractService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/02/26.
 */
@Service("menuService")
public class MenuServiceImpl extends AbstractService<Menu> implements MenuService {
    @Resource
    private MenuMapper menuMapper;

}
