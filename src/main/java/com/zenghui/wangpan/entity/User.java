package com.zenghui.wangpan.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * user
 * @author 
 */
@Data
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

    /**
     * 密码
     * 在输出的Json数据中隐藏密码，只能输入不输出
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private static final long serialVersionUID = 1L;
}