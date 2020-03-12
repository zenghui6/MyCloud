package com.zenghui.wangpan.mapper;

import com.zenghui.wangpan.entity.MyFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.relational.core.sql.In;

import java.util.List;

@Mapper
public interface MyFileMapper {
    int deleteByPrimaryKey(Integer myFileId);

    int insert(MyFile record);

    int insertSelective(MyFile record);

    MyFile selectByPrimaryKey(Integer myFileId);

    int updateByPrimaryKeySelective(MyFile record);

    int updateByPrimaryKey(MyFile record);

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

    /**
     * 查找对应仓库id 下的 类型为 type 的 文件
     * @param storeId
     * @param type
     * @return
     */
    List<MyFile> listFilesByStoreIdAndType(@Param("storeId") Integer storeId,@Param("type") Integer type);
}