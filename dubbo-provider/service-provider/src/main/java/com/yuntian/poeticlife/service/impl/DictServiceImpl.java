package com.yuntian.poeticlife.service.impl;

import com.yuntian.poeticlife.dao.DictMapper;
import com.yuntian.poeticlife.model.dto.DictDTO;
import com.yuntian.poeticlife.model.entity.Article;
import com.yuntian.poeticlife.model.entity.Dict;
import com.yuntian.poeticlife.model.vo.PageInfoVo;
import com.yuntian.poeticlife.service.DictService;
import com.yuntian.poeticlife.core.AbstractService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/06/07.
 */
@Service("dictService")
public class DictServiceImpl extends AbstractService<DictDTO,Dict> implements DictService {
    @Resource
    private DictMapper dictMapper;

    @Override
    public PageInfoVo<Dict> queryListByPage(DictDTO dto) {
        return null;
    }

    @Override
    public void saveByDTO(DictDTO dto) {

    }

    @Override
    public void deleteByDTO(DictDTO dto) {

    }

    @Override
    public void updateByDTO(DictDTO dto) {

    }

}
