package com.zenghui.wangpan.mapper;

import com.zenghui.wangpan.entity.FileFolder;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zeng
 */
@Mapper
public interface FileFolderMapper {
    int deleteByPrimaryKey(Integer fileFolderId);

    int insert(FileFolder record);

    int insertSelective(FileFolder record);

    FileFolder selectByPrimaryKey(Integer fileFolderId);

    int updateByPrimaryKeySelective(FileFolder record);

    int updateByPrimaryKey(FileFolder record);
}