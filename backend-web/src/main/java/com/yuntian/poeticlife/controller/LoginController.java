package com.yuntian.poeticlife.controller;

import com.yuntian.poeticlife.config.shiro.RedisSessionDAO;
import com.yuntian.poeticlife.config.shiro.RetryLimitHashedCredentialsMatcher;
import com.yuntian.poeticlife.core.Result;
import com.yuntian.poeticlife.core.ResultGenerator;
import com.yuntian.poeticlife.model.entity.BackendOperater;
import com.yuntian.poeticlife.service.BackendOperaterService;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @Auther: yuntian
 * @Date: 2019/6/4 0004 01:35
 * @Description:
 */
@Controller
public class LoginController extends BaseController {

    @Resource
    private BackendOperaterService backendOperaterService;


    @Resource
    private RedisSessionDAO redisSessionDAO;
    //统计在线人数,不再使用shiroSessionListener监听
    //private ShiroSessionListener shiroSessionListener;


    @Resource
    private RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher;

    /**
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root(Model model) {
        if (getUserId() == null) {
            return "backend/login";
        } else {
            return "redirect:index";
        }
    }


    /**
     * 跳转到login页面
     *
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        if (username == null) {
            return "backend/login";
        } else {
            return "redirect:index";
        }
    }


    @RequestMapping("/index")
    public String index() {
        return "backend/index";
    }

    @RequestMapping("/main")
    public String main(Model model) {
        long userId = getUserId();
        BackendOperater user = backendOperaterService.findById(userId);
        model.addAttribute("user", user);
        model.addAttribute("count", redisSessionDAO.getActiveSessionsSize());
        return "backend/main";
    }



    /**
     * 跳转到无权限页面
     *
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("/unauthorized")
    public String unauthorized(HttpSession session, Model model) {
        return "unauthorized";
    }

    /**
     * 解除admin 用户的限制登录
     * 写死的 方便测试
     *
     * @return
     */
    @RequestMapping("/unlockAccount")
    public String unlockAccount(Model model) {
        model.addAttribute("msg", "用户解锁成功");
        retryLimitHashedCredentialsMatcher.unlockAccount("admin");
        return "backend/login";
    }


    /**
     * 登陆
     *
     * @param userName 用户名
     * @param passWord 密码
     */
    @PostMapping("/login")
    @ResponseBody
    public Result login(@RequestParam String userName, @RequestParam String passWord, @RequestParam(required = false) Integer rememberMe) {
        // 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        boolean rememberMeFlag = false;
        if (rememberMe != null) {
            rememberMeFlag = (rememberMe == 1);
        }
        try {
            //登录操作
            // 执行认证登陆
            UsernamePasswordToken token = new UsernamePasswordToken(userName, passWord, rememberMeFlag);
            subject.login(token);
        } catch (Exception e) {
            //登录失败从request中获取shiro处理的异常信息 shiroLoginFailure:就是shiro异常类的全类名
            String msg="";
            if (e instanceof UnknownAccountException) {
                msg="用户名或密码错误！";
            } else if (e instanceof IncorrectCredentialsException) {
                msg="用户名或密码错误！";
            } else if (e instanceof LockedAccountException) {
                msg="账号已被锁定,请联系管理员！";
            }
            return ResultGenerator.genFailResult(msg);
        }
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
