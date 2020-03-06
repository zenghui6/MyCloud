package com.zenghui.wangpan.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * file_folder
 * @author 
 */
@Data
@AllArgsConstructor
public class FileFolder implements Serializable {
    /**
     * 文件夹ID
     */
    private Integer fileFolderId;

    /**
     * 文件夹名称
     */
    private String fileFolderName;

    /**
     * 创建时间
     */
    private Date time;

    /**
     * 父文件夹ID
     */
    private Integer parentFolderId;

    /**
     * 所属仓库ID
     */
    private Integer fileStoreId;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        FileFolder other = (FileFolder) that;
        return (this.getFileFolderId() == null ? other.getFileFolderId() == null : this.getFileFolderId().equals(other.getFileFolderId()))
            && (this.getFileFolderName() == null ? other.getFileFolderName() == null : this.getFileFolderName().equals(other.getFileFolderName()))
            && (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()))
            && (this.getParentFolderId() == null ? other.getParentFolderId() == null : this.getParentFolderId().equals(other.getParentFolderId()))
            && (this.getFileStoreId() == null ? other.getFileStoreId() == null : this.getFileStoreId().equals(other.getFileStoreId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getFileFolderId() == null) ? 0 : getFileFolderId().hashCode());
        result = prime * result + ((getFileFolderName() == null) ? 0 : getFileFolderName().hashCode());
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        result = prime * result + ((getParentFolderId() == null) ? 0 : getParentFolderId().hashCode());
        result = prime * result + ((getFileStoreId() == null) ? 0 : getFileStoreId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", fileFolderId=").append(fileFolderId);
        sb.append(", fileFolderName=").append(fileFolderName);
        sb.append(", time=").append(time);
        sb.append(", parentFolderId=").append(parentFolderId);
        sb.append(", fileStoreId=").append(fileStoreId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}