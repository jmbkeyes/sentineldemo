package com.example.demo.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping(value = "/api/test", produces = {"application/json"})
public class TestController {
    @GetMapping(value = "/hello/{name}")
    @SentinelResource(value = "sayHello")
    public String apiHello(@PathVariable String name) {
        return "hello, " + name;
    }

    @GetMapping(value = "/info")
    public Object info(@RequestParam(value = "type")Integer type) throws Exception {
        int number = new Random(1).nextInt(100);
        if(type == 1) {//慢调用比例
            //if(number %2 == 1){
            Thread.sleep(1000);
            // }
            return "info";
        }else if(type == 2){//异常比例
            if(number %3 == 1){
                throw new Exception("异常比例");
            }
        }else if(type == 3){
            if(number %2 == 1){
                throw new Exception("异常数");
            }
        }
        return "info";
    }
}
