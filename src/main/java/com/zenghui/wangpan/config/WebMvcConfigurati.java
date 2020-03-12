package com.zenghui.wangpan.config;

import com.zenghui.wangpan.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zeng
 */

@Configuration
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

    /**
     * 注册登录拦截器
     * addPathPatterns() -> 拦截的请求
     * excludePathPatterns -> 不拦截的请求
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new LoginInterceptor())
                    .addPathPatterns("/**")
                    .excludePathPatterns("/user","/login","/register","/static/**");
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        //上传的图片在，访问路径如：http://localhost:8080/d3cf0281-bb7f-40e0-ab77-406db95ccf2c.jpg
//        //其中pic表示访问的前缀。"file:E:\\Projects\\img_example\\"是文件真实的存储路径
//        registry.addResourceHandler("/**").addResourceLocations("/home/zeng/Desktop/Java学习/wangpan/public/img/user/");
//    }
}
