package com.yuntian.poeticlife.exception;//package com.company.project.exception;

import com.alibaba.fastjson.JSON;
import com.yuntian.basecommon.exception.BusinessException;
import com.yuntian.poeticlife.core.Result;
import com.yuntian.poeticlife.core.ResultGenerator;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;


/* @ Author     ：guangleilei.
 * @ Date       ：Created in 10:46 2018/11/13
 * @ Description：${description}
 */
@ControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    /**
     * 拦截捕捉自定义异常 BusinessException.class
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = BusinessException.class)
    public Result bussExceptionHandler(BusinessException e) {
        Result result = ResultGenerator.genFailResult(e.getCode(), e.getMessage());
        log.error(JSON.toJSONString(result));
        return result;
    }


}
