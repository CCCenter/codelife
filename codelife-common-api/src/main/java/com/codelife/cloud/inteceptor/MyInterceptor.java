package com.codelife.cloud.inteceptor;

import com.alibaba.fastjson.JSON;
import com.codelife.cloud.annotations.LoginRequired;
import com.codelife.cloud.dto.MemberDTO;
import com.codelife.cloud.entities.Member;
import com.codelife.cloud.util.HttpclientUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.TimeoutCallableProcessingInterceptor;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class MyInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){

        if(handler instanceof HandlerMethod) {
            HandlerMethod h = (HandlerMethod)handler;
            //判断是否需要拦截
            LoginRequired methodAnnotation = h.getMethodAnnotation(LoginRequired.class);
            if (methodAnnotation == null) {
                System.out.println(h.getMethod().getName() + "不拦截");
                return true;
            }
        }
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isBlank(authorization)) {
            //如果请求头 Authorization 为空 重定向到登陆操作
            System.out.println("没有身份信息");
            return true;
        }
        // 不为空 取出token 验证token真伪
        Header header = new BasicHeader("Authorization", authorization);
        String resultJson = HttpclientUtil.doPost("http://localhost:9000/passport/profile", null, header);
        Map map = JSON.parseObject(resultJson, Map.class);
        if(map == null){
            request.setAttribute("message","fail");
            return true;
        }
        if(map.get("data") == null){
            request.setAttribute("message","token失效");
            return true;
        }
        //取出id 传入接口
        String status = String.valueOf(map.get("code")).trim();
        if(!"700".equals(status)){
            Object data = map.get("data");

            MemberDTO memberDTO = objectMapper.convertValue(data, MemberDTO.class);
            if(Member.FORBIDDEN.equals(memberDTO.getRole())){
                request.setAttribute("message","用户被禁用");
                return true;
            }
            request.setAttribute("member",memberDTO);
        }
        return true;
    }
}