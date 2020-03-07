package com.zenghui.wangpan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zeng
 */
@SpringBootApplication
//配置dao接口扫描包
@MapperScan("com.zenghui.wangpan.mapper")
public class WangpanApplication {

    public static void main(String[] args) {
        SpringApplication.run(WangpanApplication.class, args);
    }

}
