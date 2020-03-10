package com.zenghui.wangpan.service;

import com.zenghui.wangpan.entity.MyFile;

import java.util.List;

/**
 * @InterfaceName MyFileService
 * @Description: TODO
 * @Author zeng
 * @Date 2020/3/10
 **/
public interface MyFileService {
    /**
     * 上传文件
     * @param myFile
     * @return
     */
    boolean add(MyFile myFile);

    /**
     * 查找文件夹下的所有文件
     * @param folderId
     * @return
     */
    List<MyFile> listFileByFolderId(Integer folderId);

    /**
     * 查找仓库根目录下的所有文件,根据storeID 并且 parentId ==0
     * @param storeId
     * @return
     */
    List<MyFile> listRootFileByStoreId(Integer storeId);
}
