package com.yuntian.poeticlife.controller;
import com.yuntian.poeticlife.core.Result;
import com.yuntian.poeticlife.core.ResultGenerator;
import com.yuntian.poeticlife.model.entity.OpreaterImage;
import com.yuntian.poeticlife.service.OpreaterImageService;
import com.yuntian.poeticlife.model.vo.PageInfoVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yuntian.poeticlife.model.dto.OpreaterImageDTO;
import javax.annotation.Resource;


/**
* Created by CodeGenerator on 2019/06/17.
*/
@Controller
@RequestMapping("/opreater/image")
public class OpreaterImageController extends BaseController{

    @Resource
    private OpreaterImageService opreaterImageService;

    @RequestMapping("/pageList")
    public String pageList() {
        return "backend/opreaterImageList";
    }

    @PostMapping("/add")
    @ResponseBody
    public Result add(OpreaterImage dto) {
        dto.setCreateId(getUserId());
        dto.setUpdateId(getUserId());
        opreaterImageService.saveByDTO(dto);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    @ResponseBody
    public Result delete(OpreaterImage dto) {
        dto.setCreateId(getUserId());
        dto.setUpdateId(getUserId());
        opreaterImageService.deleteByDTO(dto);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    @ResponseBody
    public Result update(OpreaterImage dto) {
        dto.setCreateId(getUserId());
        dto.setUpdateId(getUserId());
        opreaterImageService.updateByDTO(dto);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    @ResponseBody
    public Result detail(@RequestParam Long id) {
        OpreaterImage opreaterImage = opreaterImageService.findById(id);
        return ResultGenerator.genSuccessResult(opreaterImage);
    }


    @PostMapping("/list")
    @ResponseBody
    public PageInfoVo<OpreaterImage> list(OpreaterImageDTO dTO) {
        return opreaterImageService.queryListByPage(dTO);
    }


}
