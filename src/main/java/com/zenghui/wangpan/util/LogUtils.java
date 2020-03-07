package com.zenghui.wangpan.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName LogUtils
 * @Description: 打印工具类,单例模式,调用getInstance方式时传入Class参数
 * @Author zeng
 * @Date 2020/3/7
 **/
public class LogUtils {
    private static Logger logger;
    public static Logger getInstance(Class c){
        return logger =  LoggerFactory.getLogger(c);
    }
    private LogUtils(){}
}