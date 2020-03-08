package com.zenghui.wangpan.mapper;

import com.zenghui.wangpan.entity.User;

import java.util.List;

/**
 * @author zeng
 */
public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    User selectByUserName(User user);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> queryAll();

    /**
     * 登录根据用户名和密码查找用户
     * @param userName
     * @param password
     * @return
     */
    User getByNameAndPassword(String userName, String password);

}