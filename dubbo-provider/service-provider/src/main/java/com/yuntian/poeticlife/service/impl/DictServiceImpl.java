package com.yuntian.poeticlife.service.impl;

import com.yuntian.poeticlife.dao.DictMapper;
import com.yuntian.poeticlife.model.entity.Dict;
import com.yuntian.poeticlife.service.DictService;
import com.yuntian.poeticlife.core.AbstractService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/06/07.
 */
@Service("dictService")
public class DictServiceImpl extends AbstractService<Dict> implements DictService {
    @Resource
    private DictMapper dictMapper;

}
