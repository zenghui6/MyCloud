package com.zenghui.wangpan.service.impl;

import com.zenghui.wangpan.entity.MyFile;
import com.zenghui.wangpan.service.MyFileService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName MyFileServiceImpl
 * @Description: TODO
 * @Author zeng
 * @Date 2020/3/10
 **/
@Service
public class MyFileServiceImpl extends BaseService  implements MyFileService {
    /**
     * 上传文件
     *
     * @param myFile
     * @return
     */
    @Override
    public boolean add(MyFile myFile) {
        if (myFileMapper.insert(myFile) == 1) {
            return true;
        }
        return false;
    }

    /**
     * selectlive更新文件
     *   数据库连接url 设置了&useAffectedRows=true  #mybatis返回的是受影响的函行数
     * @param myFile
     * @return
     */
    @Override
    public boolean updateFile(MyFile myFile) {
        if (myFileMapper.updateByPrimaryKeySelective(myFile) != 0){
            return true;
        }
        return false;
    }

    /**
     * 下载文件,根据文件id获取文件信息
     *
     * @param fid
     * @return
     */
    @Override
    public MyFile getFileByFileId(Integer fid) {
        return myFileMapper.selectByPrimaryKey(fid);
    }

    /**
     * 查找文件夹下的所有文件
     * @param folderId
     * @return
     */
    @Override
    public List<MyFile> listFileByFolderId(Integer folderId) {
        return myFileMapper.listFileByFolderId(folderId);
    }

    /**
     * 查找仓库根目录下的所有文件,根据storeID 并且 parentId ==0
     * @param storeId
     * @return
     */
    @Override
    public List<MyFile> listRootFileByStoreId(Integer storeId) {
        return myFileMapper.listRootFileByStoreId(storeId);
    }

    /**
     * 查找对应仓库id 下的 类型为 type 的 文件
     *
     * @param storeId
     * @param type
     * @return
     */
    @Override
    public List<MyFile> listFilesByStoreIdAndType(Integer storeId, Integer type) {
        return myFileMapper.listFilesByStoreIdAndType(storeId,type);
    }
}
