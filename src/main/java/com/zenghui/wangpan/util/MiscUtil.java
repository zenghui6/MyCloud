package com.zenghui.wangpan.util;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName MiscUtil
 * @Description: 封装的数据校验不合格的方法
 * @Author zeng
 * @Date 2020/3/7
 **/
public class MiscUtil {
    static public Result getValidateError(BindingResult bindingResult){

        if (bindingResult.hasErrors() == false) {
            return null;
        }

        Map<String,String> fieldErrors = new HashMap<>();
        
        for (FieldError error : bindingResult.getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getCode() + "|" + error.getDefaultMessage());
        }

        Result result = new Result(422,"输入错误");
        result.putData("fieldErrors",fieldErrors);

        return result;
    }
}
