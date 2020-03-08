package com.zenghui.wangpan.controller;

import com.zenghui.wangpan.entity.User;
import com.zenghui.wangpan.util.LogUtils;
import com.zenghui.wangpan.util.MiscUtil;
import com.zenghui.wangpan.util.Result;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Date;


/**
 * @ClassName UserController
 * @Description: 登录注册,关于用户的操作,Restful格式.
 * @Author zeng
 * @Date 2020/3/6
 **/
@RestController
public class UserController extends BaseController{
    private Logger logger = LogUtils.getInstance(UserController.class);
    /**
     * 用户登录,后面会改为shiro验证
     * 前端传入的formData会自动注入到bean中
     * @param user
     * @return
     */
    @PostMapping("/login")
    public Object login(@RequestBody User user){

        return null;
    }

    /**
     * 注册@Valid 表明在绑定输入时作校验,
     * BindingResult bindingResult 这个参数存放校验结果
     *
     * 使用MultipartFile后不要使用 @RequestBody,因为它也不知道要注入到哪,去掉就行
     * @param user
     * @param bindingResult
     * @return
     */
    @PostMapping("/register")
    public Object register(@Valid User user, BindingResult bindingResult,
                                           @RequestParam("image") MultipartFile image, HttpServletRequest request) throws IOException {
        if (bindingResult.hasErrors()){
            //返回输入错误的result,使用工具类处理
            Result errResult = MiscUtil.getValidateError(bindingResult);

            //返回ResponseEntity
            return errResult;
        }
        //判断用户是否已存在
        if (userService.findByUserName(user)){
            //用户名存在
            return Result.fail("该用户名已存在");
        }
        //输入数据校验成功,开始注册
        user.setRegisterTime(new Date());
        user.setImagePath(saveOrUpdateImageFile(user,image,request));

        userService.add(user);
        logger.info("创建用户成功"+user);

        return Result.succuess();
    }

    /**
     * 对上传图片处理的工具方法
     * @param image
     * @param request
     * @return
     * @throws IOException
     */
    public String saveOrUpdateImageFile(User user,MultipartFile image,HttpServletRequest request) throws IOException {
        //得到路径,根据路径创建文件夹,默认会在项目下找public || static文件夹,找不到就去tomcat数据库下
        File imageFolder = new File(request.getServletContext().getRealPath("img/user"));
        //文件名
        String filename = image.getOriginalFilename();
        //获取文件后缀名 .jpg
        String fileType = filename.substring(filename.lastIndexOf("."),filename.length());
        //在文件夹下创建文件
        File file = new File(imageFolder,user.getUserName()+fileType);

        //user文件不存在就创建user文件夹
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        //生成图片并保存
        image.transferTo(file);
        logger.info("头像上传成功:"+file.getCanonicalPath());

        return file.getPath();
    }

}
