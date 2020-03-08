package com.zenghui.wangpan.util;

import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 用了返回给前端信息的类
 * @author zeng
 */
public class Result implements Serializable {

    private int code;

    private String message;

    private Object data;

    public Result(){

    }

    public static Result succuess(){
        Result Result = new Result();
        Result.setResultCode(ResultCode.SUCCESS);

        return Result;
    }

    public static Result succuess(Object data){
        Result Result = new Result();
        Result.setResultCode(ResultCode.SUCCESS);
        Result.setData(data);

        return Result;
    }

    public static Result fail() {
        Result Result = new Result();
        Result.setResultCode(ResultCode.FAIL);

        return Result;
    }


    public static Result fail(ResultCode resultCode) {
        Result Result = new Result();
        Result.setResultCode(resultCode);

        return Result;
    }

    public static Result fail(String message) {
        Result Result = new Result();
        Result.setCode(ResultCode.FAIL.code());
        Result.setMessage(message);

        return Result;
    }

    public static Result fail(Integer code, String message) {
        Result Result = new Result();
        Result.setCode(code);
        Result.setMessage(message);

        return Result;
    }

    public static Result fail(ResultCode resultCode, Object data) {
        Result Result = new Result();
        Result.setResultCode(resultCode);
        Result.setData(data);

        return Result;
    }

    public static Result verifiFail(String message){
        Result Result = new Result();
        Result.setCode(ResultCode.VERIFICATION_FAIL.code());
        Result.setMessage(message);

        return Result;
    }

    private void setResultCode(ResultCode resultCode){
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    public void setData(Object data){
        this.data = data;
    }

    public Object getData(){
        return data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}