package com.yuntian.poeticlife.controller;

import com.yuntian.basecommon.Result;
import com.yuntian.basecommon.util.ResultUtil;
import com.yuntian.poeticlife.service.HelloService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @ Author     ：guangleilei.
 * @ Date       ：Created in 16:16 2018/12/20
 * @ Description：${hello}
 */
@Controller
public class HelloController {

    @Resource
    private HelloService helloService;

    @RequestMapping("/say")
    @ResponseBody
    public Result say(@RequestParam String msg) {
        helloService.say(msg);
        return ResultUtil.createSuccess();
    }


}
