package com.zenghui.wangpan.service.impl;

import com.zenghui.wangpan.entity.FileFolder;
import com.zenghui.wangpan.service.FileFolderService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName FileFolderServiceImpl
 * @Description: TODO
 * @Author zeng
 * @Date 2020/3/8
 **/
@Service
public class FileFolderServiceImpl extends BaseService implements FileFolderService {
    /**
     * 查找一个
     *
     * @param id
     * @return
     */
    @Override
    public FileFolder getById(Integer id) {
        return fileFolderMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取仓库根目录下的所有文件夹
     *
     * @param fileStoreId
     * @return
     */
    @Override
    public List<FileFolder> getRootFolderByFileStoreId(Integer fileStoreId) {
        return fileFolderMapper.getRootFoldersByFileStoreId(fileStoreId);
    }

    /**
     * 获取父目录下的所有文件夹
     *
     * @param parentFolderId
     * @return
     */
    @Override
    public List<FileFolder> getFileFolderByParentFolderId(Integer parentFolderId) {
        return fileFolderMapper.getFileFolderByParentFolderId(parentFolderId);
    }

    /**
     * 添加文件夹
     *
     * @param fileFolder
     * @return
     */
    @Override
    public boolean addFileFolder(FileFolder fileFolder) {
        if (fileFolderMapper.insert(fileFolder) == 1){
            return true;
        }
        return false;
    }

    /**
     * 计算对应仓库下的所有文件夹数
     *
     * @param storeId
     * @return
     */
    @Override
    public int countFileFolderByStoreId(Integer storeId) {
        return fileFolderMapper.countFileFolderByStoreId(storeId);
    }
}
