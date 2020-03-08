package com.zenghui.wangpan.controller;

import com.zenghui.wangpan.entity.FileFolder;
import com.zenghui.wangpan.entity.User;
import com.zenghui.wangpan.util.LogUtils;
import com.zenghui.wangpan.util.MiscUtil;
import com.zenghui.wangpan.util.Result;
import org.slf4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * @ClassName FileStoreController
 * @Description: 仓库中文件夹和文件的管理
 * @Author zeng
 * @Date 2020/3/8
 **/
@RestController
public class FileStoreController extends BaseController {
    private Logger logger = LogUtils.getInstance(FileStoreController.class);

    /**
     * 在仓库中新增文件夹
     * @param folder
     * @return
     */
    @PostMapping("/addFolder")
    public Object addFolder(@Valid @RequestBody FileFolder folder, BindingResult bindingResult, HttpSession  session){
        System.out.println(folder);
        if (bindingResult.hasErrors()){
            Result errResult = MiscUtil.getValidateError(bindingResult);
            return errResult;
        }
        //获取当前登录对象
        User loginUser = (User)session.getAttribute("loginUser");

        folder.setFileStoreId(loginUser.getFileStoreId());
        folder.setTime(new Date());

        //获取当前目录下的所有文件夹,检查当前文件夹是否已经存在
        List<FileFolder> fileFolders = null;
        //父文件夹id是通过前端传过来的
        if (folder.getParentFolderId() == 0){
            //没有父文件夹,就在根目录咯,在仓库根目录下所有文件夹
            fileFolders = fileFolderService.getRootFolderByFileStoreId(loginUser.getFileStoreId());
        }else {
            //有父文件夹,在父文件夹下添加文件夹,先获取其下所有文件夹,判断是否存在了
            fileFolders =fileFolderService.getFileFolderByParentFolderId(folder.getParentFolderId());
        }

        //遍历查询出来的文件夹,判断是否已经存在
        for (FileFolder fileFolder : fileFolders) {
            if (fileFolder.getFileFolderName().equals(folder.getFileFolderName())){

                logger.info("添加文件夹失败! 文件夹已经存在");
                return Result.fail("添加文件夹失败! 文件夹已经存在");
            }
        }

        //没有重复,添加文件夹,向数据库写入数据
        fileFolderService.addFileFolder(folder);
        logger.info("添加文件夹成功! "+folder);

        return Result.succuess();
    }
}
