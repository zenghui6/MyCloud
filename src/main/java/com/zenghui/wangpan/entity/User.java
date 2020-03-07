package com.zenghui.wangpan.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * user
 * @author zeng
 * 1.@NotNull：不能为null，但可以为空， (""," “,” “)都是可以的
 * 2.@NotEmpty：不能为null，而且长度必须大于0，(” “,” ")是可以的，需要标记在String类型的字段上
 * 3.@NotBlank：不能为null，而且调用trim()后，长度必须大于0，(“test”)是可以的，只能作用在String类型的字段上。
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
    @NotBlank(message = "用户名必须填")
    @Size(min = 2,max = 16 , message = "用户名2~20个字")
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
    @NotBlank(message = "密码必须填")
    @Size(min = 6,max = 16 , message = "密码6~16位")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private static final long serialVersionUID = 1L;
}