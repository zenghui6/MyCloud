package com.zenghui.wangpan.controller;

import com.zenghui.wangpan.service.FileFolderService;
import com.zenghui.wangpan.service.FileStoreService;
import com.zenghui.wangpan.service.MyFileService;
import com.zenghui.wangpan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName BaseController
 * @Description: 所有控制器都继承该类,是为了注入方便
 * @Author zeng
 * @Date 2020/3/6
 **/
public class BaseController {
    @Autowired
    protected UserService userService;

    @Autowired
    protected FileStoreService fileStoreService;

    @Autowired
    protected FileFolderService fileFolderService;

    @Autowired
    protected MyFileService myFileService;


}
