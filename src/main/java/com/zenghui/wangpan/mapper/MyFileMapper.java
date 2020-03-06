package com.zenghui.wangpan.mapper;

import com.zenghui.wangpan.entity.MyFile;

public interface MyFileMapper {
    int deleteByPrimaryKey(Integer myFileId);

    int insert(MyFile record);

    int insertSelective(MyFile record);

    MyFile selectByPrimaryKey(Integer myFileId);

    int updateByPrimaryKeySelective(MyFile record);

    int updateByPrimaryKey(MyFile record);
}