package com.zenghui.wangpan.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * user
 * @author 
 */
@Data
/**
 * @Data 注解的类编译后会自动给我们加上:
 *         所有属性的get和set方法toString hashCode方法 equals方法
 */
@AllArgsConstructor
public class User implements Serializable {
    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 注册时间
     */
    private Date registerTime;

    /**
     * 头像地址
     */
    private String imagePath;

    private static final long serialVersionUID = 1L;
}