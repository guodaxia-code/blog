package xyz.worldzhile.blog.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class MyGlobalExceptionHandler {
// springmvc 注解@ControllerAdvice 三大作用
//    全局异常处理
//   全局数据绑定
//    全局数据预处理

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ModelAndView ArithmeticException(HttpServletRequest request,Exception e) throws Exception {
        logger.error("Request URL : {}, Expection ： {}",request.getRequestURL(),e.getMessage());
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class)!=null){
                throw  e;
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("code",505);
        modelAndView.addObject("url",request.getRequestURL());
        modelAndView.addObject("exception",e);
        modelAndView.setViewName("error/error");
        return modelAndView;

    }

}
