package com.zenghui.wangpan.mapper;

import com.zenghui.wangpan.entity.FileStore;

public interface FileStoreMapper {
    int deleteByPrimaryKey(Integer fileStoreId);

    int insert(FileStore record);

    int insertSelective(FileStore record);

    FileStore selectByPrimaryKey(Integer fileStoreId);

    int updateByPrimaryKeySelective(FileStore record);

    int updateByPrimaryKey(FileStore record);
}