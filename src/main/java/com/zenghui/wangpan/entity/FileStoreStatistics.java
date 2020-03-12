package com.zenghui.wangpan.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName FileStoreStatistics
 * @Description: 文件仓库数据统计工具类,方便存储计算的数据
 * @Author zeng
 * @Date 2020/3/8
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileStoreStatistics implements Serializable {
    /**
     * 该用户的文件仓库
     */
    private FileStore fileStore;

    /**
     * 文档数
     */
    private int doc;
    /**
     * 音乐数
     */
    private int music;

    /**
     * 视频数
     */
    private int video;
    /**
     * 图片数
     */
    private int image;
    /**
     * 其他数
     */
    private int other;
    /**
     * 文件数
     */
    private int fileCount;
    /**
     * 文件夹数
     */
    private int folderCount;
}
