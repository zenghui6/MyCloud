package com.zenghui.wangpan.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * file_store
 * @author 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileStore implements Serializable {
    /**
     * 文件仓库ID
     */
    private Integer fileStoreId;

    /**
     * 最大容量(单位KB)
     */
    private Integer maxSize;

    /**
     * 当前容量(单位KB)
     */
    private Integer currentSize;

    /**
     * 主人ID
     */
    private Integer userId;

    private static final long serialVersionUID = 1L;
}