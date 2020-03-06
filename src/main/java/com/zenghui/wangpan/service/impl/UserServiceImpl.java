package com.zenghui.wangpan.service.impl;

import com.zenghui.wangpan.entity.User;
import com.zenghui.wangpan.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName UserService
 * @Description: TODO
 * @Author zeng
 * @Date 2020/3/6
 **/
@Service
public class UserServiceImpl extends BaseService implements UserService {

    /**
     * 添加User
     *
     * @param user
     * @return
     */
    @Override
    public boolean add(User user) {
        if (userMapper.insert(user) == 1){
            return true;
        }
        return false;
    }

    /**
     * 删除User
     *
     * @param userId
     * @return
     */
    @Override
    public boolean deleteById(Integer userId) {
        if (userMapper.deleteByPrimaryKey(userId) == 1){
            return true;
        }

        return false;
    }

    /**
     * 通过id查询
     *
     * @param userId
     * @return
     */
    @Override
    public User queryById(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    /**
     * 查询所有
     *
     * @param user
     * @return
     */
    @Override
    public List<User> queryAll(User user) {
        return userMapper.queryAll();
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    @Override
    public boolean update(User user) {
        if (userMapper.updateByPrimaryKeySelective(user) == 1){
            return true;
        }
        return false;
    }
}
