package com.yuntian.basecommon.util;

import com.yuntian.basecommon.Result;

public class ResultUtil {

    public static <T> Result<T> createSuccess(T data){
        Result<T> result=new Result<>();
        result.setCode(99);
        result.setMessage("成功");
        result.setData(data);
        return  result;
    }

    public static  <T> Result<T>  createSuccess(){
        return  createSuccess(null);
    }

    public  static <T> Result<T>  createFauile(int code,String message){
        Result<T> result=new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return  result;
    }


}
