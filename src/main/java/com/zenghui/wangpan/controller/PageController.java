package com.zenghui.wangpan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

/**
 * @ClassName PageController
 * @Description: 页面跳转
 * @Author zeng
 * @Date 2020/3/7
 **/
@Controller
public class PageController {

    @GetMapping("/user")
    public String toLogin(HttpSession session){
        if (session.getAttribute("loginUser") != null){
            return "redirect:/";
        }

        return "index";
    }

    /**
     * 转到上传文件页面
     * @return
     */
    @GetMapping("/upload")
    public String toUpload(){
        return "u-admin/upload";
    }
    /**
     * 转跳到所有文件
     */
    @GetMapping("/files")
    public String toFiles(){
        return "u-admin/files";
    }


    /**
     * 登出
     * @param session
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
