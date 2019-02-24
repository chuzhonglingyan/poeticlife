package com.yuntian.poeticlife.controller;

import com.yuntian.poeticlife.core.ResultGenerator;
import com.yuntian.poeticlife.util.AjaxUtil;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @ Author     ：guangleilei.
 * @ Date       ：Created in 14:10 2018/11/13
 * @ Description：${自定义错误页面}
 */
@Controller
public class MainErrorController implements ErrorController {


    private static final String PATH = "/error";


    @RequestMapping(PATH)
    public String handleError(HttpServletRequest request) { //获取statusCode:401,404,500
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String msg = (String) request.getAttribute(" javax.servlet.error.message");
        if (AjaxUtil.isAjax(request)) {
            return ResultGenerator.genFailResult(statusCode, msg).toString();
        } else {
            if (statusCode == 401) {
                return "error/401";
            } else if (statusCode == 404) {
                return "error/404";
            } else if (statusCode == 403) {
                return "error/403";
            } else {
                return "error/500";
            }
        }

    }

    @Override
    public String getErrorPath() {
        return PATH;
    }

}
