package com.zenghui.wangpan.util;

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
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }

        Result result =  Result.verifiFail("输入错误");
        Map<String,Object> map = new HashMap<>();
        map.put("fieldErrors",fieldErrors);
        result.setData(map);
        return result;
    }
}
