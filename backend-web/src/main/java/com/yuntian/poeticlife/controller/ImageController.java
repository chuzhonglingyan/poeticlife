package com.yuntian.poeticlife.controller;
import com.yuntian.poeticlife.core.Result;
import com.yuntian.poeticlife.core.ResultGenerator;
import com.yuntian.poeticlife.model.entity.Image;
import com.yuntian.poeticlife.service.ImageService;
import com.yuntian.poeticlife.model.vo.PageInfoVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yuntian.poeticlife.model.dto.ImageDTO;
import com.yuntian.poeticlife.model.vo.ImageVO;
import javax.annotation.Resource;


/**
* Created by CodeGenerator on 2019/06/17.
*/
@Controller
@RequestMapping("/image")
public class ImageController extends BaseController{

    @Resource
    private ImageService imageService;

    @RequestMapping("/pageList")
    public String pageList() {
        return "backend/imageList";
    }

    @PostMapping("/add")
    @ResponseBody
    public Result add(ImageVO dto) {
        dto.setCreateId(getUserId());
        dto.setUpdateId(getUserId());
        imageService.saveByDTO(dto);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    @ResponseBody
    public Result delete(ImageVO dto) {
        dto.setCreateId(getUserId());
        dto.setUpdateId(getUserId());
        imageService.deleteByDTO(dto);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    @ResponseBody
    public Result update(ImageVO dto) {
        dto.setCreateId(getUserId());
        dto.setUpdateId(getUserId());
        imageService.updateByDTO(dto);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    @ResponseBody
    public Result detail(@RequestParam Long id) {
        Image image = imageService.findById(id);
        return ResultGenerator.genSuccessResult(image);
    }


    @PostMapping("/list")
    @ResponseBody
    public PageInfoVo<ImageVO> list(ImageDTO dTO) {
        return imageService.queryListByPage(dTO);
    }


}
