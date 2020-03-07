package com.zenghui.wangpan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName PageController
 * @Description: TODO
 * @Author zeng
 * @Date 2020/3/7
 **/
@Controller
public class PageController {

    @GetMapping("/login")
    public String toLogin(){
        return "index";
    }
}
