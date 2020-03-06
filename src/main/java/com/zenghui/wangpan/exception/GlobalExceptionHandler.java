package com.zenghui.wangpan.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zeng
 * 异常处理，主要是在处理删除父类信息的时候
 * 因为外键约束的存在，而导致违反约束
 * @ControllerAdvice: 全局捕获异常类，只要作用在@RequestMapping上，所有异常都会被捕获
 */
@RestController
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public String defaultErrorHandler(HttpServletRequest req, Exception e) throws ClassNotFoundException {
        //定义外键约束异常
        Class constrainViolationException = Class.forName("org.hibernate.exception.ConstraintViolationException");
        if (e.getCause() != null && e.getCause().getClass() == constrainViolationException){
            return "违反了约束,多半是外键约束";
        }

        return e.getMessage();
    }
}
