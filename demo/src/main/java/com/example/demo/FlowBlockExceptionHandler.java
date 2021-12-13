package com.example.demo;

import com.alibaba.csp.sentinel.slots.block.BlockException;

public class FlowBlockExceptionHandler{
    public static String handle(String name, BlockException ex){
        return "{\"message\":\"限流\"}";
    }
}
