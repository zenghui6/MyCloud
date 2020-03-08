package com.zenghui.wangpan.service.impl;

import com.zenghui.wangpan.controller.BaseController;
import com.zenghui.wangpan.entity.FileStore;
import com.zenghui.wangpan.service.FileStoreService;
import org.springframework.stereotype.Service;

/**
 * @ClassName FileStoreServiceImpl
 * @Description: TODO
 * @Author zeng
 * @Date 2020/3/8
 **/
@Service
public class FileStoreServiceImpl  extends BaseService implements FileStoreService {
    /**
     * 为用户添加仓库
     *
     * @param fileStore
     * @return
     */
    @Override
    public boolean add(FileStore fileStore) {
        if (fileStoreMapper.insert(fileStore) == 1){
            return true;
        }

        return false;
    }

    /**
     * 通过用户id查找对应的仓库
     *
     * @param userId
     * @return
     */
    @Override
    public FileStore getByUserId(Integer userId) {
        return fileStoreMapper.selectByUserId(userId);
    }


}
