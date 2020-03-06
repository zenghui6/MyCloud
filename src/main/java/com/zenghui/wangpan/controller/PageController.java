package com.zenghui.wangpan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName PageController
 * @Description: 用作页面转跳
 * @Author zeng
 * @Date 2020/3/6
 **/
@Controller
public class PageController {

    @GetMapping("/login")
    public String login(){
        return "index";
    }

    @GetMapping("/")
    public String index(){
        return "u-admin/index";
    }

}
