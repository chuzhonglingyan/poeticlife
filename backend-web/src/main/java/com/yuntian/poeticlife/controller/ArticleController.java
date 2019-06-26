package com.yuntian.poeticlife.controller;
import com.yuntian.poeticlife.core.Result;
import com.yuntian.poeticlife.core.ResultGenerator;
import com.yuntian.poeticlife.model.entity.Article;
import com.yuntian.poeticlife.service.ArticleService;
import com.yuntian.poeticlife.model.vo.PageInfoVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yuntian.poeticlife.model.dto.ArticleDTO;
import javax.annotation.Resource;
import org.apache.shiro.authz.annotation.RequiresPermissions;

/**
* Created by CodeGenerator on 2019/06/26.
*/
@Controller
@RequestMapping("/article")
public class ArticleController extends BaseController{

    @Resource
    private ArticleService articleService;

    @RequestMapping("/pageList")
    public String pageList() {
        return "backend/articleList";
    }

    @RequiresPermissions(value = {"article:add"})
    @PostMapping("/add")
    @ResponseBody
    public Result add(Article dto) {
        dto.setCreateId(getUserId());
        dto.setUpdateId(getUserId());
        articleService.saveByDTO(dto);
        return ResultGenerator.genSuccessResult();
    }

    @RequiresPermissions(value = {"article:delete"})
    @PostMapping("/delete")
    @ResponseBody
    public Result delete(Article dto) {
        dto.setCreateId(getUserId());
        dto.setUpdateId(getUserId());
        articleService.deleteByDTO(dto);
        return ResultGenerator.genSuccessResult();
    }

    @RequiresPermissions(value = {"article:update"})
    @PostMapping("/update")
    @ResponseBody
    public Result update(Article dto) {
        dto.setCreateId(getUserId());
        dto.setUpdateId(getUserId());
        articleService.updateByDTO(dto);
        return ResultGenerator.genSuccessResult();
    }

    @RequiresPermissions(value = {"article:view"})
    @PostMapping("/detail")
    @ResponseBody
    public Result detail(@RequestParam Long id) {
        Article article = articleService.findById(id);
        return ResultGenerator.genSuccessResult(article);
    }


    @PostMapping("/list")
    @ResponseBody
    public PageInfoVo<Article> list(ArticleDTO dTO) {
        return articleService.queryListByPage(dTO);
    }


}
