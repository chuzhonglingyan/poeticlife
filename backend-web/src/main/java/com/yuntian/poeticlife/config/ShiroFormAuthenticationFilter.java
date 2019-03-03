package com.yuntian.poeticlife.config;

import com.yuntian.poeticlife.util.ShiroUtil;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import static com.yuntian.poeticlife.constants.BackedUserConstants.CURRENT_USERID;

/**
 * @Auther: yuntian
 * @Date: 2019/2/25 0025 22:00
 * @Description:
 */
@Slf4j
public class ShiroFormAuthenticationFilter extends FormAuthenticationFilter {


    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
                                     ServletResponse response) throws Exception {
        //清除用户信息
        WebUtils.getAndClearSavedRequest(request);
        //获取已登录的用户信息
        Long activeUser = ShiroUtil.getUserId();
        //获取session
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpSession session = httpServletRequest.getSession();
        //把用户信息保存到session
        session.setAttribute(CURRENT_USERID, activeUser);
        log.error("登录成功");
        return super.onLoginSuccess(token, subject, request, response);
    }

}
