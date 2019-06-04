package com.yuntian.poeticlife.controller;

import com.yuntian.poeticlife.core.Result;
import com.yuntian.poeticlife.core.ResultGenerator;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: yuntian
 * @Date: 2019/6/4 0004 01:35
 * @Description:
 */
@RestController
public class LoginController  extends BaseController  {

    /**
     * 登陆
     *
     * @param userName 用户名
     * @param passWord 密码
     */
    @PostMapping("/login.json")
    public Result login(@RequestParam String userName, @RequestParam String passWord) {
        // 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        // 执行认证登陆
        UsernamePasswordToken token = new UsernamePasswordToken(userName, passWord);
        subject.login(token);
        return ResultGenerator.genSuccessResult("登录成功");
    }

    /**
     * 本地登出页面
     */
    @GetMapping("/loginOut")
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        return ResultGenerator.genSuccessResult("退出登录成功");
    }

}
