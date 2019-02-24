package com.yuntian.poeticlife.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ Author     ：guangleilei.
 * @ Date       ：Created in 15:33 2018/11/9
 * @ Description：${description}
 */
@Controller
@RequestMapping(value = "/guest")
public class GuestController {

    @RequestMapping("/test")
    public String index(ModelMap map) {
        // 加入一个属性，用来在模板中读取
        map.addAttribute("host", "http://blog.yuntian.com");
        // return模板文件的名称，对应src/main/resources/templates/index.ftl
        return "views/index";
    }




}