package com.yuntian.poeticlife.service;
import com.yuntian.poeticlife.core.Service;
import com.yuntian.poeticlife.model.entity.OpreaterImage;
import com.yuntian.poeticlife.model.vo.PageInfoVo;
import com.yuntian.poeticlife.model.dto.OpreaterImageDTO;

/**
 * Created by CodeGenerator on 2019/06/17.
 */
public interface OpreaterImageService extends Service<OpreaterImageDTO,OpreaterImage> {


    PageInfoVo<OpreaterImage> queryListByPage(OpreaterImageDTO dto);


    void saveByDTO(OpreaterImage vo);


    void deleteByDTO(OpreaterImage vo);


    void updateByDTO(OpreaterImage vo);


}
