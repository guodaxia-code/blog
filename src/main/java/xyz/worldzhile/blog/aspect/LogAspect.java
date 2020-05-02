package xyz.worldzhile.blog.aspect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class LogAspect {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* xyz.worldzhile.blog.controller.*.*(..))")
    private void log(){}

    @Before("log()")
    private void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod=joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog=new RequestLog(url,ip,classMethod,args);
        logger.info("doBefore : {}",requestLog);
    }

    @After("log()")
    private void doAfter(){
//        logger.info("--------------doAfter---------------");
    }

    //成功的返回结果
    @AfterReturning(returning = "result",pointcut = "log()")
    private void doAfterReturning(Object result){
        logger.info("Result : {}",result);
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

    }

}

