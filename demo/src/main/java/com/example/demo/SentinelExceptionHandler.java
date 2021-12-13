package com.example.demo;

import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 *  仅对 Sentinel 自带的 Web 埋点生效（URL 维度）
 *  **注解方式埋点，参考注解埋点文档，配置 blockHandler**
 */
@Component
public class SentinelExceptionHandler implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
        Map<String, Object> resp = new HashMap<>();
        // 不同的异常返回不同的提示语
        String message = null;
        if (e instanceof FlowException) {
            message = "被限流了";
        } else if (e instanceof DegradeException) {
            message = "服务降级了";
            Tracer.trace(e);
        } else if (e instanceof ParamFlowException) {
            message = "被限流了";
        } else if (e instanceof SystemBlockException) {
            message = "被系统保护了";
        } else if (e instanceof AuthorityException) {
            message = "被授权了";
        }

        response.setCharacterEncoding("utf8");
        response.setHeader("content-Type", "application/json");
        response.getWriter().print("{\"status\":\"1\", \"message\":\"" + message +"\"}");
    }
}
