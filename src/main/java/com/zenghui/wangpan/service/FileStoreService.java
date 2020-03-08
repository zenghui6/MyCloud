package com.zenghui.wangpan.service;

import com.zenghui.wangpan.entity.FileStore;

/**
 * @InterfaceName FileStoreService
 * @Description: 仓库业务接口
 * @Author zeng
 * @Date 2020/3/8
 **/
public interface FileStoreService {
    /**
     * 为用户添加仓库
     * @param fileStore
     * @return
     */
    boolean add(FileStore fileStore);

    /**
     * 通过用户id查找对应的仓库
     * @param userId
     * @return
     */
    FileStore getByUserId(Integer userId);
}
