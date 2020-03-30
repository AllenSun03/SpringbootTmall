package com.how2java.tmall.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/** 异常处理的类
 * 主要是在处理删除父类信息的时候么因为外键越是的存在，而导致违反约束
 * @ClassName: GloabalExceptionHandler
 * @Author: AllenSun
 * @Date: 2020/2/4 13:30
 */
@RestController
@ControllerAdvice
public class GloabalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public String defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception{
        e.printStackTrace();
        Class constraintViolationException = Class.forName("org.hibernate.exception.ConstraintViolationException");
        if(null!=e.getCause() && constraintViolationException==e.getCause().getClass()){
            return "违反了约束，多半是外键约束";
        }
        return e.getMessage();
    }
}
