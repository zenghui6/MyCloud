package com.zenghui.wangpan;

import com.zenghui.wangpan.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
//启动Spring
@SpringBootTest
public class UserMapperTest {

    @Autowired
    protected UserMapper userMapper;

    @Test
    public void getUser(){
        userMapper.selectByPrimaryKey(1);
    }

    public void hello(){
        System.out.println("hello");
    }
}
