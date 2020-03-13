package com.zenghui.wangpan.controller;

import com.zenghui.wangpan.entity.FileFolder;
import com.zenghui.wangpan.entity.FileStore;
import com.zenghui.wangpan.entity.MyFile;
import com.zenghui.wangpan.entity.User;
import com.zenghui.wangpan.util.FtpUtils;
import com.zenghui.wangpan.util.LogUtils;
import com.zenghui.wangpan.util.Result;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName MyFileController
 * @Description: 文件控制器,掌管文件的上传,下载,分类
 * @Author zeng
 * @Date 2020/3/10
 **/
@RestController
public class MyFileController extends BaseController {
    private Logger logger = LogUtils.getInstance(FileStoreController.class);

    /**
     * 网盘文件上传
     * @param files
     * @return
     */
    @PostMapping("/uploadFile")
    public Object uploadFile(@RequestParam("file")MultipartFile files , HttpSession session, HttpServletRequest request){

        /**
         * 那个前端插件传参总是错误,只能使用这个方法了
         */
        Integer foid = (Integer) session.getAttribute("curFoid");

        //获取当前登录用户的信息,和仓库状态
        User loginUser = (User) session.getAttribute("loginUser");
        FileStore store = fileStoreService.getByUserId(loginUser.getUserId());

        //删除文件命名中的空格
        String name = files.getOriginalFilename().replaceAll(" ","");

        //获取当前目录下的所有文件,判断是否已经存在
        List<MyFile> myFiles = null;
        if (foid == 0){
            //当前目录为仓库根目录
            myFiles = myFileService.listRootFileByStoreId(loginUser.getFileStoreId());
        }else {
            //非根目录
            myFiles = myFileService.listFileByFolderId(foid);
        }
        System.out.println(myFiles);

        //遍历判断是否存在同名的
        for (MyFile curFile : myFiles) {
            if (curFile!=null && (curFile.getMyFileName() + curFile.getPostfix()).equals(name)) {
                logger.error("当前文件夹已经存在!上传失败...");
                return Result.fail(501, "文件同名");
            }
        }

        //判断名称是否符合规范
        if (checkTarget(name)){
            logger.error("上传文件失败!文件名不符合规范....");
            return Result.fail(502,"上传失败!文件名不符合规范");
        }

        //文件存储路径以每天用户Id和上传日期 命名文件夹
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dataStr = format.format(new Date());

        String path = loginUser.getUserId()+"/"+ dataStr + "/" + foid;


        //上传文件,计算文件大小,计算库剩余容量,将长整形转为 int,宁愿浪费一点空间,不让他上传
        Integer sizeInt = Math.max((int) files.getSize() / 1024, 1);
        logger.info("文件大小: " +sizeInt);

        //是否仓库放不下该文件
        if (store.getCurrentSize() + sizeInt > store.getMaxSize()){
            logger.error("上传失败!仓库已满");
            return Result.fail(503,"上传失败!仓库已满");
        }

        //处理文件信息
        //处理文件大小
        int index = name.lastIndexOf(".");
        String tempName = name.substring(index);
        name = name.substring(0,index);

        //获得文件类型 1:文本类型   2:图像类型  3:视频类型  4:音乐类型  5:其他类型
        String postfix = tempName.toLowerCase();
        int type = getType(postfix);

        //提交到FTP服务器
        try {
            // 文件存储路径,存放的文件名,文件输入流
            FtpUtils ftpUtil = new FtpUtils();
            boolean result = ftpUtil.fileUpload("/"+path,name+postfix,files.getInputStream());
            System.out.println("上传文件:" +result);
            if (result){
                logger.info("文件上传成功: "+files.getOriginalFilename());

                //文件上传成功,向数据库中写入信息
                MyFile newFile = new MyFile();
                newFile.setMyFileName(name);
                newFile.setDownloadTime(0);
                newFile.setFileFolderId(foid);
                newFile.setFileStoreId(store.getFileStoreId());
                newFile.setUploadTime(new Date());
                newFile.setSize(sizeInt);
                newFile.setPostfix(postfix);
                newFile.setMyFilePath(path);
                newFile.setType(type);

                if (myFileService.add(newFile)){
                    //上传成功了,给仓库使用容量改变
                    int curSize = store.getCurrentSize();
                    logger.info("更新仓库用量: "+ curSize);
                    store.setCurrentSize(curSize + sizeInt);
                    logger.info("更新后仓库用量: " +store.getCurrentSize());
                    //将仓库信息更新到数据库
                    if (!fileStoreService.update(store)){
                        logger.info("更新仓库信息失败");
                        return Result.fail("更新仓库信息失败");
                    }
                    return Result.succuess(200,"上传文件成功");
                }
                else {
                    logger.error("文件夹上传失败: "+files.getOriginalFilename());
                    return Result.fail(504,"文件夹上传失败");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.fail();
    }

    /**
     * 根据传入的当前文件夹foid 找到当前目录下的所有文件
     * 如果foid = 0 ,就是仓库根目录
     * @return
     */
    @GetMapping("/myfiles")
    public Object listFolderByParentId(@RequestParam(value = "foid" ,defaultValue = "0") Integer foid ,HttpSession  session){
        User loginUser = (User)session.getAttribute("loginUser");
        int curStoreId = loginUser.getFileStoreId();

        List<MyFile> fileList = null;
        if (foid == 0){
            fileList = myFileService.listRootFileByStoreId(curStoreId);
        }
        else{
            fileList = myFileService.listFileByFolderId(foid);
        }

        System.out.println(fileList);

        return Result.succuess(fileList);
    }

    /**
     * 下载文件,下载的文件通过HTTPServletResponse,传给浏览器
     * @param fid
     * @param response
     * @return
     */
    @GetMapping("/downloadFile")
    public void downloadFile(@RequestParam("fid") Integer fid, HttpServletResponse response){
        //获取要下载的文件信息
        MyFile myFile = myFileService.getFileByFileId(fid);
        //ftp上的文件地址
        String remotePath = myFile.getMyFilePath();
        String fileName = myFile.getMyFileName()+myFile.getPostfix();

        try{
            //输出缓冲流
            OutputStream os = new BufferedOutputStream(response.getOutputStream());
            //设置返回类型和编码
            response.setCharacterEncoding("utf-8");
            //响应内容类型
            response.setContentType("multipart/form-data");
            //文件名转码,不然呢会出现中文乱码  attachment意味着消息体应该被下载到本地
            response.setHeader("Content-Disposition","attachment;fileName="+ URLEncoder.encode(fileName,"UTF-8"));

            FtpUtils ftpUtils = new FtpUtils();
            boolean flag = ftpUtils.downloadFile("/"+remotePath,fileName,os);

            if (flag){
                //下载文件成功了,开始更新数据库数据
                MyFile newFile = new MyFile();
                newFile.setMyFileId(myFile.getMyFileId());
                newFile.setDownloadTime(myFile.getDownloadTime() + 1);
                myFileService.updateFile(newFile);

                os.flush();
                os.close();
                logger.info("文件下载成功: "+myFile.getMyFileName()+myFile.getPostfix());
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("文件下载失败: "+myFile.getMyFileName()+myFile.getPostfix());
    }

    /**
     *修改文件名
     * @param file
     * @param session
     * @return
     */
    @PostMapping("/updateFileName")
    public Object updateFileName(@RequestBody MyFile file,HttpSession session){
        User loginUser = (User) session.getAttribute("loginUser");

        MyFile myFile = myFileService.getFileByFileId(file.getMyFileId());
        if (myFile != null){
            String oldName = myFile.getMyFileName();
            String newName = file.getMyFileName();

            //检查文件名的合法性
            if (checkTarget(newName)){
                logger.error("文件名更新失败!文件名不符合规范....");
                return Result.fail(502,"文件名更新失败!文件名不符合规范....");
            }
            int folderId = (Integer) session.getAttribute("foid");
            //获取当前目录下的所有文件，用来判断是否已经存在
            List<MyFile> myFiles = null;
            if (folderId == 0){
                //当前目录为根目录
                myFiles = myFileService.listRootFileByStoreId(loginUser.getFileStoreId());
            }else {
                //当前目录为其他目录
                myFiles = myFileService.listFileByFolderId(folderId);
            }
            for (int i = 0; i < myFiles.size(); i++) {
                if ((myFiles.get(i).getMyFileName()+myFiles.get(i).getPostfix()).equals(file.getMyFileName()+file.getPostfix())){
                    logger.error("当前文件名更新失败...");
                    return Result.fail("文件名已存在!");
                }
            }
            if (!oldName.equals(newName)){
                FtpUtils ftpUtils = new FtpUtils();
                boolean b = ftpUtils.reNameFile(myFile.getMyFilePath() + "/" +oldName+myFile.getPostfix(),myFile.getMyFilePath() + "/" + newName+myFile.getPostfix());

                if (b){
                    myFile.setMyFileName(newName);
                    boolean ret = myFileService.updateFile(myFile);
                    if (ret){
                        logger.info("修改文件名成功!原文件名: "+oldName+"新文件名: "+newName);
                    }else {
                        logger.error("修改文件名失败!原文件名: "+oldName+"新文件名: "+newName);
                    }

                }
            }
        }
        return Result.fail("修改文件名失败");
    }

    /**
     * 根据文件种类返回所有该文件
     * @param type
     * @param session
     * @return
     */
    @GetMapping("/getFilesByType")
    public Object getFilesByType(@RequestParam("type") int type,HttpSession session){
        User loginUser  = (User) session.getAttribute("loginUser");

        List<MyFile> files = myFileService.listFilesByStoreIdAndType(loginUser.getFileStoreId(),type);

        return Result.succuess(files);
    }

    /**
     * 根据文件后缀名获取相应的类型
     * @param type
     * @return int 1:文本类型   2:图像类型  3:视频类型  4:音乐类型  5:其他类型
     */
    public int getType(String type){
        if (".txt".equals(type) || ".doc".equals(type) || ".docx".equals(type)|| ".wps".equals(type)
                || ".word".equals(type) || ".html".equals(type) || ".pdf".equals(type)){
            return 1;
        }else if(".bmp".equals(type)||".gif".equals(type)||".jpg".equals(type)
            ||".pic".equals(type)||".png".equals(type)||".jepg".equals(type)||".webp".equals(type)
            ||".svg".equals(type)){
            return 2;
        } else if (".avi".equals(type)||".mov".equals(type)||".qt".equals(type)
            ||".asf".equals(type)||".rm".equals(type)||".navi".equals(type)||".wav".equals(type)
            ||".mp4".equals(type)){
            return 3;
        } else if (".mp3".equals(type)||".wma".equals(type)){
            return 4;
        } else {
            return 5;
        }
    }

    /**
     * 正则表达式验证文件名是否合法 [汉字,字符,数字,下划线,英文句号,横线]
     * @param target
     * @return
     */
    public boolean checkTarget(String target){
        final String format = "[^\\u4E00-\\u9FA5\\uF900-\\uFA2D\\W-_.]";

        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(target);

        return !matcher.find();
    }
}
