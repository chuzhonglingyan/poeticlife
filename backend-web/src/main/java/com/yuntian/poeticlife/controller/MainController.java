package com.yuntian.poeticlife.controller;

import com.yuntian.poeticlife.model.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ Author     ：guangleilei.
 * @ Date       ：Created in 15:33 2018/11/9
 * @ Description：${description}
 */
@Controller
public class MainController {

    @RequestMapping("/")
    public String index(ModelMap map) {
        // 加入一个属性，用来在模板中读取
        map.addAttribute("host", "http://blog.yuntian.com");
        // return模板文件的名称，对应src/main/resources/templates/index.ftl
        return "/index";
    }

    @RequestMapping("/login")
    public String login() {
        return "backend/login";
    }

    @RequestMapping("/user")
    public String userInfo(Model map) {
        User user = new User();
        user.setNickName("哈哈");
        map.addAttribute("user", user);

        // return模板文件的名称，对应src/main/resources/templates/index.ftl
        return "/user";
    }


    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String hello(@RequestParam String name) {
        return "Hello " + name;
    }


}