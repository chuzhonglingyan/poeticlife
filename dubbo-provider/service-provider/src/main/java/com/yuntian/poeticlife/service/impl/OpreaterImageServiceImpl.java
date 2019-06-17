package com.yuntian.poeticlife.service.impl;

import com.yuntian.poeticlife.dao.OpreaterImageMapper;
import com.yuntian.poeticlife.model.dto.OpreaterImageDTO;
import com.yuntian.poeticlife.model.entity.Article;
import com.yuntian.poeticlife.model.entity.OpreaterImage;
import com.yuntian.poeticlife.model.vo.PageInfoVo;
import com.yuntian.poeticlife.service.OpreaterImageService;
import com.yuntian.poeticlife.core.AbstractService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/03/31.
 */
@Service("opreaterImageService")
public class OpreaterImageServiceImpl extends AbstractService<OpreaterImageDTO,OpreaterImage> implements OpreaterImageService {
    @Resource
    private OpreaterImageMapper opreaterImageMapper;


    @Override
    public PageInfoVo<OpreaterImage> queryListByPage(OpreaterImageDTO dto) {
        return null;
    }

    @Override
    public void saveByDTO(OpreaterImageDTO dto) {

    }

    @Override
    public void deleteByDTO(OpreaterImageDTO dto) {

    }

    @Override
    public void updateByDTO(OpreaterImageDTO dto) {

    }


}
