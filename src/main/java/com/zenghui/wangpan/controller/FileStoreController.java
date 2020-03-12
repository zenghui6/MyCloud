package com.zenghui.wangpan.controller;

import com.zenghui.wangpan.entity.FileFolder;
import com.zenghui.wangpan.entity.FileStore;
import com.zenghui.wangpan.entity.FileStoreStatistics;
import com.zenghui.wangpan.entity.User;
import com.zenghui.wangpan.util.LogUtils;
import com.zenghui.wangpan.util.MiscUtil;
import com.zenghui.wangpan.util.Result;
import org.slf4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

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

    /**
     * 根据传入的当前文件夹fid 找到当前目录下的文件夹
     * 如果foid = 0 ,就是仓库根目录
     * @return
     */
    @GetMapping("/folder")
    public Object listFolderByParentId(@RequestParam(value = "foid" ,defaultValue = "0") Integer foid ,HttpSession  session){
        User loginUser = (User)session.getAttribute("loginUser");
        int curStoreId = loginUser.getFileStoreId();

        List<FileFolder> folderList = null;
        if (foid == 0){
            folderList = fileFolderService.getRootFolderByFileStoreId(curStoreId);
        }
        else{
            folderList = fileFolderService.getFileFolderByParentFolderId(foid);
        }
        session.setAttribute("curFoid",foid);

        return Result.succuess(folderList);
    }

    /**
     *  根据当前文件夹id 生成面包屑导航栏 根目录 -> 音乐 -> 周杰伦音乐
     * @param foid
     * @param session
     * @return
     */
    @GetMapping("/folderParents")
    public Object getNavigationBarByFid(@RequestParam(value = "foid", defaultValue = "0")Integer foid, HttpSession session){
        //使用栈后进先出,更符合,因为是倒过来推
        Stack<FileFolder> foldersNavigationStack = new Stack<>();

        //非根目录
        if (foid != 0){
            FileFolder fileFolder = fileFolderService.getById(foid);

            while (fileFolder.getParentFolderId() != 0){
                foldersNavigationStack.add(fileFolder);
                int parentId = fileFolder.getParentFolderId();

                fileFolder = fileFolderService.getById(parentId);
            }

            foldersNavigationStack.add(fileFolder);
        }

        //倒过来存放
        List<FileFolder> ret = new LinkedList<>();
        while (!foldersNavigationStack.isEmpty()){
            ret.add(foldersNavigationStack.pop());
        }

        System.out.println(ret);

        return Result.succuess(ret);
    }

    /**
     * 获取当前用户仓库的状态,文件数,文件夹数...
     * 1:文本类型   2:图像类型  3:视频类型  4:音乐类型  5:其他类型
     * @param session
     * @return
     */
    @GetMapping("/fileStoreStatus")
    public Object getStatus(HttpSession session){
        User loginUser =(User) session.getAttribute("loginUser");

        FileStore curStore = fileStoreService.getByUserId(loginUser.getUserId());
        int storeId = curStore.getFileStoreId();
        //文件夹数
        int folderNum = fileFolderService.countFileFolderByStoreId(storeId);

        //文档
        int doc = myFileService.listFilesByStoreIdAndType(storeId,1).size();
        //图片数
        int image = myFileService.listFilesByStoreIdAndType(storeId,2).size();
        //视频数
        int video = myFileService.listFilesByStoreIdAndType(storeId,3).size();
        //音乐数
        int music = myFileService.listFilesByStoreIdAndType(storeId,4).size();
        //其他数
        int other = myFileService.listFilesByStoreIdAndType(storeId,5).size();
        //所有文件数
        int fileCount = doc+image+video+music+other;

        //采用FileStoreStatistics对象将信息封装
        FileStoreStatistics fileStoreStatistics =  FileStoreStatistics.builder()
                                                                        .doc(doc)
                                                                        .image(image)
                                                                        .fileCount(fileCount)
                                                                        .fileStore(curStore)
                                                                        .folderCount(fileCount)
                                                                        .music(music)
                                                                        .video(video)
                                                                        .other(other).build();

        return Result.succuess(fileStoreStatistics);
    }
}
