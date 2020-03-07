package com.zenghui.wangpan.config;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zeng
 */
public class WebMvcConfigurati implements WebMvcConfigurer {
    /**
     * 默认页
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        /**
         * 转跳首页
         */
        registry.addViewController("/").setViewName("u-admin/index");

        /**
         * 重定向到首页
         */
        registry.addRedirectViewController("/test","/");
    }
}
