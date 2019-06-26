package com.yuntian.poeticlife.controller;
import com.yuntian.poeticlife.core.Result;
import com.yuntian.poeticlife.core.ResultGenerator;
import com.yuntian.poeticlife.model.entity.Dict;
import com.yuntian.poeticlife.service.DictService;
import com.yuntian.poeticlife.model.vo.PageInfoVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yuntian.poeticlife.model.dto.DictDTO;
import javax.annotation.Resource;


/**
* Created by CodeGenerator on 2019/06/17.
*/
@Controller
@RequestMapping("/dict")
public class DictController extends BaseController{

    @Resource
    private DictService dictService;

    @RequestMapping("/pageList")
    public String pageList() {
        return "backend/dictList";
    }

    @PostMapping("/add")
    @ResponseBody
    public Result add(Dict dto) {
        dto.setCreateId(getUserId());
        dto.setUpdateId(getUserId());
        dictService.saveByDTO(dto);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    @ResponseBody
    public Result delete(Dict dto) {
        dto.setCreateId(getUserId());
        dto.setUpdateId(getUserId());
        dictService.deleteByDTO(dto);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    @ResponseBody
    public Result update(Dict dto) {
        dto.setCreateId(getUserId());
        dto.setUpdateId(getUserId());
        dictService.updateByDTO(dto);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    @ResponseBody
    public Result detail(@RequestParam Long id) {
        Dict dict = dictService.findById(id);
        return ResultGenerator.genSuccessResult(dict);
    }


    @PostMapping("/list")
    @ResponseBody
    public PageInfoVo<Dict> list(DictDTO dTO) {
        return dictService.queryListByPage(dTO);
    }


}
