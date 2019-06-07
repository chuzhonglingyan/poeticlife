package com.yuntian.poeticlife.controller;
import com.yuntian.poeticlife.core.Result;
import com.yuntian.poeticlife.core.ResultGenerator;
import com.yuntian.poeticlife.model.entity.Image;
import com.yuntian.poeticlife.service.ImageService;
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
* Created by CodeGenerator on 2019/06/06.
*/
@RestController
@RequestMapping("/image")
public class ImageController extends BaseController{

    @Resource
    private ImageService imageService;




    @PostMapping("/add")
    public Result add(Image image) {
        imageService.save(image);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Long id) {
        imageService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(Image image) {
        imageService.update(image);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Long id) {
        Image image = imageService.findById(id);
        return ResultGenerator.genSuccessResult(image);
    }

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Image> childList = imageService.findAll();
        PageInfoVo<Image> pageInfo = new PageInfoVo<>(new PageInfo<>(childList));
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
