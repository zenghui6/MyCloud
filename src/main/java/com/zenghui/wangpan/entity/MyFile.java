package com.zenghui.wangpan.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * my_file
 * @author 
 */
@Data
public class MyFile implements Serializable {
    /**
     * 文件ID
     */
    private Integer myFileId;

    /**
     * 文件名
     */
    private String myFileName;

    /**
     * 下载次数
     */
    private Integer downloadTime;

    /**
     * 上传时间
     */
    private Date uploadTime;

    /**
     * 文件大小
     */
    private Integer size;

    /**
     * 文件类型
     */
    private Integer type;

    /**
     * 文件后缀
     */
    private String postfix;

    /**
     * 所属文件夹
     */
    private Integer fileFolderId;

    private String myFilePath;

    /**
     * 文件所属仓库
     */
    private Integer fileStoreId;

    private static final long serialVersionUID = 1L;
}