package com.zenghui.wangpan.controller;

import com.zenghui.wangpan.entity.FileStore;
import com.zenghui.wangpan.entity.MyFile;
import com.zenghui.wangpan.entity.User;
import com.zenghui.wangpan.util.FtpUtils;
import com.zenghui.wangpan.util.LogUtils;
import com.zenghui.wangpan.util.Result;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
        System.out.println(checkTarget(name));
        if (checkTarget(name)){
            logger.error("上传文件失败!文件名不符合规范....");
            return Result.fail(502,"上传失败!文件名不符合规范");
        }

        //文件存储路径以每天用户Id和上传日期 命名文件夹
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dataStr = format.format(new Date());

        String path = loginUser.getUserId()+"/"+ dataStr + "/" + foid;


        //上传文件,计算文件大小,计算库剩余容量,将长整形转为 int,宁愿浪费一点空间,不让他上传
        Integer sizeInt = Math.toIntExact(files.getSize() / 1024);
        //是否仓库放不下该文件
        if (store.getCurrentSize() + sizeInt > store.getMaxSize()){
            logger.error("上传失败!仓库已满");
            return Result.fail(503,"上传失败!仓库已满");
        }

        //处理文件信息
        //处理文件大小
        String size = String.valueOf(files.getSize()/1024.0);
        int indexDot = size.lastIndexOf(".");
        size = size.substring(0,indexDot);
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
                newFile.setSize(Integer.valueOf(size));
                newFile.setPostfix(postfix);
                newFile.setMyFilePath(path);
                newFile.setType(type);

                if (myFileService.add(newFile)){
                    //上传成功了,给仓库使用容量改变
                    store.setCurrentSize(store.getCurrentSize()+sizeInt);
                    //将仓库信息更新到数据库
                    if (!fileStoreService.update(store)){
                        logger.info("更新仓库信息失败");
                        return Result.fail("更新仓库信息失败");
                    }
                    return Result.succuess("上传文件成功");
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
