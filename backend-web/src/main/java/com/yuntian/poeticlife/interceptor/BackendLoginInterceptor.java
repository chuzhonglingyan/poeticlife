package com.yuntian.poeticlife.interceptor;


import com.yuntian.poeticlife.cache.RedisUtil;
import com.yuntian.poeticlife.constants.CookieConstant;
import com.yuntian.poeticlife.constants.RedisKey;
import com.yuntian.poeticlife.model.entity.BackendOperater;
import com.yuntian.poeticlife.util.CookieUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import java.lang.reflect.Method;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: yuntian
 * @Date: 2018/8/19 21:02
 * @Description: 后台登录拦截器
 */
public class BackendLoginInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 登录session key
     */
    public final static String SESSION_KEY = "backend_login_user";

    //从Redis中查询
    @Resource
    private RedisUtil redisUtil;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ip = request.getRemoteAddr();
        long startTime = System.currentTimeMillis();
        request.setAttribute("requestStartTime", startTime);
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            logger.info("用户:" + ip + ",访问目标:" + method.getDeclaringClass().getName() + "." + method.getName());
            //查询cookie
            Cookie cookie = CookieUtils.get(request, CookieConstant.BACKEND_LOGIN_COOKIE);
            if (cookie == null || StringUtils.isEmpty(cookie.getValue())) {
                logger.warn("【登陆校验】Cookie中查不到登录cookie");
                // 跳转登录
                String url = "/user/login";
                response.sendRedirect(url);
                return false;
            }

            BackendOperater backedOperater = (BackendOperater) redisUtil.get(RedisKey.getBackendLoginkey(cookie.getValue()));
            if (backedOperater == null) {
                logger.warn("【登陆校验】Redis中查不到登录cookie");
                // 跳转登录
                String url = "/user/login";
                response.sendRedirect(url);
                return false;
            }
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            long startTime = (Long) request.getAttribute("requestStartTime");
            long endTime = System.currentTimeMillis();
            long executeTime = endTime - startTime;
            // 打印方法执行时间
            if (executeTime > 1000) {
                logger.info("[" + method.getDeclaringClass().getName() + "." + method.getName() + "] 执行耗时 : "
                        + executeTime + "ms");
            } else {
                logger.info("[" + method.getDeclaringClass().getSimpleName() + "." + method.getName() + "] 执行耗时 : "
                        + executeTime + "ms");
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
