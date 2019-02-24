package com.yuntian.poeticlife.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuntian.poeticlife.core.Result;
import com.yuntian.poeticlife.core.ResultGenerator;
import com.yuntian.poeticlife.model.entity.BackendOperater;
import com.yuntian.poeticlife.model.vo.PageInfoVo;
import com.yuntian.poeticlife.service.BackendOperaterService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import javax.annotation.Resource;

/**
 * Created by CodeGenerator on 2019/02/23.
 */
@RestController
@RequestMapping("/operater")
public class BackendOperaterController extends BaseController {

    @Resource
    private BackendOperaterService backendOperaterService;

    @PostMapping("/add")
    public Result add(BackendOperater backendOperater) {
        backendOperaterService.save(backendOperater);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Long id) {
        backendOperaterService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(BackendOperater backendOperater) {
        backendOperaterService.update(backendOperater);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Long id) {
        BackendOperater backendOperater = backendOperaterService.findById(id);
        return ResultGenerator.genSuccessResult(backendOperater);
    }

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<BackendOperater> list = backendOperaterService.findAll();
        PageInfoVo<BackendOperater> pageInfo = new PageInfoVo<>(new PageInfo<>(list));
        return ResultGenerator.genSuccessResult(pageInfo);
    }


    /**
     * 登陆
     * @param userName 用户名
     * @param passWord 密码
     */
    @PostMapping("/login")
    public Result login(@RequestParam String userName, @RequestParam String passWord) {
        // 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        // 执行认证登陆
        UsernamePasswordToken token = new UsernamePasswordToken(userName, passWord);
        subject.login(token);
        return ResultGenerator.genSuccessResult("登录成功");
    }

}
