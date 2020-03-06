package com.zenghui.wangpan.controller;

import com.zenghui.wangpan.entity.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UserController
 * @Description: 登录注册,关于用户的操作,Restful格式.
 * @Author zeng
 * @Date 2020/3/6
 **/
@RestController
public class UserController extends BaseController{
    /**
     * 用户登录,后面会改为shiro验证
     * 前端传入的formData会自动注入到bean中
     * @param user
     * @return
     */
    @PostMapping("/login")
    public Object login(@RequestBody User user){

        return null;
    }
}
