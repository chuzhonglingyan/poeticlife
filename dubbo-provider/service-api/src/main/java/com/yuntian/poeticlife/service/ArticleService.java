package com.yuntian.poeticlife.service;
import com.yuntian.poeticlife.core.Service;
import com.yuntian.poeticlife.model.vo.PageInfoVo;
import com.yuntian.poeticlife.model.dto.ArticleDTO;
import com.yuntian.poeticlife.model.entity.Article;

/**
 * Created by CodeGenerator on 2019/06/26.
 */
public interface ArticleService extends Service<ArticleDTO,Article> {


    PageInfoVo<Article> queryListByPage(ArticleDTO dto);


    void saveByDTO(Article vo);


    void deleteByDTO(Article vo);


    void updateByDTO(Article vo);


}
