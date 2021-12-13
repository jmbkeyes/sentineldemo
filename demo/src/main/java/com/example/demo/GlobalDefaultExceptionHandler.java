package com.example.demo;

import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

//@RestControllerAdvice
public class GlobalDefaultExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object blockException(Exception e){
        Map<String, Object> resp = new HashMap<>();
        resp.put("message", "限流");
        //被@Exceptionhandler处理的异常将不被统计到统计信息中，需要手动加入到统计信息了
        Tracer.trace(e);
        return resp;
    }
}
