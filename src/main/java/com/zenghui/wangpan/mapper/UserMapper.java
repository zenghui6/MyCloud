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
}