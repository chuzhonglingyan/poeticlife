package com.yuntian.poeticlife.service;
import com.yuntian.poeticlife.core.Service;
import com.yuntian.poeticlife.model.vo.PageInfoVo;
import com.yuntian.poeticlife.model.dto.ImageDTO;
import com.yuntian.poeticlife.model.vo.ImageVO;

/**
 * Created by CodeGenerator on 2019/06/17.
 */
public interface ImageService extends Service<ImageDTO,ImageVO> {


    PageInfoVo<ImageVO> queryListByPage(ImageDTO dto);


    void saveByDTO(ImageVO vo);


    void deleteByDTO(ImageVO vo);


    void updateByDTO(ImageVO vo);


}
