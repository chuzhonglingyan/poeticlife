package com.yuntian.poeticlife.quartz;

import com.yuntian.poeticlife.exception.BusinessException;
import com.yuntian.poeticlife.util.SpringContextUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * @Auther: yuntian
 * @Date: 2019/3/17 0017 19:35
 * @Description:
 */
public class ScheduleCallTask implements Callable<Integer> {

    private Object target;
    private Method method;
    private String params;

    public ScheduleCallTask(String beanName, String methodName, String params)  {
       check(beanName,methodName,params);
    }


    private void  check(String beanName, String methodName, String params){
        try {
            this.target = SpringContextUtils.getBean(beanName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.params = params;
        if(this.target==null){
            BusinessException.throwMessage("没有该任务类");
        }
        if(StringUtils.isNotBlank(params)){
            try {
                this.method = target.getClass().getDeclaredMethod(methodName, String.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                BusinessException.throwMessage("没有该方法");
            }
        }else{
            try {
                this.method = target.getClass().getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                BusinessException.throwMessage("没有该方法");
            }
        }
    }

    @Override
    public Integer call()  {
        try {
            ReflectionUtils.makeAccessible(method);
            if(StringUtils.isNotBlank(params)){
                method.invoke(target, params);
            }else{
                method.invoke(target);
            }
            return 1;
        }catch (Exception e) {
            BusinessException.throwMessage("执行方法失败");
        }
        return 0;
    }
}
