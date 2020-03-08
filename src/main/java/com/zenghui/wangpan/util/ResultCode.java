package com.zenghui.wangpan.util;

/**
 * API 统一返回状态码
 * @author zeng
 */
public enum ResultCode {
    /* 成功状态码 */
    SUCCESS(0, "Request is successful"),
    FAIL(1, "Request is failed"),
    VERIFICATION_FAIL(40002,"字段验证失败"),
    TOKEN_INVALID(40001, "Token is null or invalid"),
    ACCESS_DENIED(40003, "Access denied"),
    FAIL4DELETE(50001, "Delete failed"),
    FAIL4UPDATE(50002, "Update failed");

    private Integer code;
    private String message;
    ResultCode(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public Integer code(){
        return this.code;
    }

    public String message(){
        return this.message;
    }
}

