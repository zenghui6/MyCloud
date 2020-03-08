package com.zenghui.wangpan.service;

import com.zenghui.wangpan.entity.FileFolder;

import java.util.List;

/**
 * @ClassName FileFolderService
 * @Description: 文件夹接口
 * @Author zeng
 * @Date 2020/3/8
 **/
public interface FileFolderService {
    /**
     * 获取仓库根目录下的所有文件夹
     * @param fileStoreId
     * @return
     */
    List<FileFolder> getRootFolderByFileStoreId(Integer fileStoreId);

    /**
     *  获取父目录下的所有文件夹
     * @param parentFolderId
     * @return
     */
    List<FileFolder> getFileFolderByParentFolderId(Integer parentFolderId);

    /**
     * 添加文件夹
     * @param fileFolder
     * @return
     */
    boolean addFileFolder(FileFolder fileFolder);
}
