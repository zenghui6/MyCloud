package com.zenghui.wangpan.controller;

import com.zenghui.wangpan.util.LogUtils;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;

/**
 * @ClassName MyFileController
 * @Description: 文件控制器,掌管文件的上传,下载,分类
 * @Author zeng
 * @Date 2020/3/10
 **/
@RestController
public class MyFileController extends BaseController {
    private Logger logger = LogUtils.getInstance(FileStoreController.class);


}
