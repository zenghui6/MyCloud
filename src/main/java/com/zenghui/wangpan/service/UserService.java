package com.zenghui.wangpan.service;

import com.zenghui.wangpan.entity.User;

import java.util.List;

/**
 * @InterfaceName UserService
 * @Description: 用户层业务接口
 * @Author zeng
 * @Date 2020/3/6
 **/
public interface UserService {

    /**
     * 添加User
     * @param user
     * @return
     */
    boolean add(User user);

    /**
     * 删除User
     * @param userId
     * @return
     */
    boolean deleteById(Integer userId);

    /**
     * 通过id查询
     * @param userId
     * @return
     */
    User queryById(Integer userId);

    /**
     * 查询所有
     * @param user
     * @return
     */
    List<User> queryAll(User user);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    boolean update(User user);
}
