package com.yuntian.poeticlife.controller;
import com.yuntian.poeticlife.core.Result;
import com.yuntian.poeticlife.core.ResultGenerator;
import com.yuntian.poeticlife.model.entity.OpreaterImage;
import com.yuntian.poeticlife.service.OpreaterImageService;
import com.github.pagehelper.PageHelper;
import com.yuntian.poeticlife.model.vo.PageInfoVo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by CodeGenerator on 2019/03/31.
*/
@RestController
@RequestMapping("/opreater/image")
public class OpreaterImageController extends BaseController{

    @Resource
    private OpreaterImageService opreaterImageService;

    @PostMapping("/add")
    public Result add(OpreaterImage opreaterImage) {
        opreaterImageService.save(opreaterImage);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Long id) {
        opreaterImageService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(OpreaterImage opreaterImage) {
        opreaterImageService.update(opreaterImage);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Long id) {
        OpreaterImage opreaterImage = opreaterImageService.findById(id);
        return ResultGenerator.genSuccessResult(opreaterImage);
    }

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<OpreaterImage> childList = opreaterImageService.findAll();
        PageInfoVo<OpreaterImage> pageInfo = new PageInfoVo<>(new PageInfo<>(childList));
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
