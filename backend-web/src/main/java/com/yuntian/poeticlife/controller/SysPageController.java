package com.yuntian.poeticlife.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.slf4j.Slf4j;

/**
 * @ Author     ：guangleilei.
 * @ Date       ：Created in 15:33 2018/11/9
 * @ Description：${description}
 */
@Slf4j
@Controller
public class SysPageController extends BaseController {

    @RequestMapping("/index")
    public String index() {
        return "backend/index";
    }

    @RequestMapping("/login")
    public String login() {
        return "backend/login";
    }

    @RequestMapping("/register")
    public String register() {
        return "backend/register";
    }

    @RequestMapping("/main")
    public String main() {
        return "backend/main";
    }

    @RequestMapping("/menu01")
    public String menu01() {
        return "backend/menu01";
    }


    @RequestMapping("/menu02")
    public String menu02() {
        return "backend/menu02";
    }

    @RequestMapping("/menuManager")
    public String menuManager() {
        return "backend/menuManager";
    }


    @RequestMapping("/operaterManager")
    public String operaterList() {
        return "backend/operaterManager";
    }
}