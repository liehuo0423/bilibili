package com.bilibili.handler;

import com.bilibili.domain.Response;
import com.bilibili.domain.exception.ConditionException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CommonGlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Response<String> commonGlobalExceptionHandler(HttpServletRequest request,Exception exception){
        String message = exception.getMessage();
        if(exception instanceof ConditionException){
            Integer code = ((ConditionException) exception).getCode();
            return new Response<>(code,message);
        }else {
            return new Response<>(500,message);
        }
    }
}
