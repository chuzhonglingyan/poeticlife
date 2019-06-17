package com.yuntian.poeticlife.service;
import com.yuntian.poeticlife.core.Service;
import com.yuntian.poeticlife.model.vo.PageInfoVo;
import com.yuntian.poeticlife.model.dto.ArticleDTO;
import com.yuntian.poeticlife.model.vo.ArticleVO;

/**
 * Created by CodeGenerator on 2019/06/13.
 */
public interface ArticleService extends Service<ArticleDTO,ArticleVO> {


    PageInfoVo<ArticleVO> queryListByPage(ArticleDTO dto);


    void saveByDTO(ArticleDTO dto);


    void deleteByDTO(ArticleDTO dto);


    void updateByDTO(ArticleDTO dto);


}
