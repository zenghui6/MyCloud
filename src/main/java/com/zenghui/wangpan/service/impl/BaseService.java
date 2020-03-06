package com.zenghui.wangpan.service.impl;

import com.zenghui.wangpan.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName BaseService
 * @Description: 单纯的基本依赖注入
 * @Author zeng
 * @Date 2020/3/6
 **/
public class BaseService {

    @Autowired
    protected UserMapper userMapper;
}
