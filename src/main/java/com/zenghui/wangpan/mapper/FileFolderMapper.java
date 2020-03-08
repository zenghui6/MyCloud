package com.zenghui.wangpan.mapper;

import com.zenghui.wangpan.entity.FileFolder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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

    /**
     * 根据仓库id获取仓库根目录下的文件夹,parent_folder_id == 0;
     * @param fileStoreId
     * @return
     */
    List<FileFolder> getRootFoldersByFileStoreId(Integer fileStoreId);

    /**
     *  获取父目录下的所有文件夹
     * @param parentFolderId
     * @return
     */
    List<FileFolder> getFileFolderByParentFolderId(Integer parentFolderId);
}