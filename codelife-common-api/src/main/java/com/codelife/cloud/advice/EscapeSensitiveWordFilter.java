package com.codelife.cloud.advice;

import com.codelife.cloud.util.SensitiveWordUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author Ryan
 * @date 2019/4/25 18:41
 */
@ControllerAdvice(basePackages = "com.codelife.cloud")
public class EscapeSensitiveWordFilter implements RequestBodyAdvice {


    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }


    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        if(o != null){
            SensitiveWordUtils.apply(o);
        }
        System.out.println(o);
        return o;
    }

        @Override
        public Object handleEmptyBody (Object o, HttpInputMessage httpInputMessage, MethodParameter
        methodParameter, Type type, Class < ? extends HttpMessageConverter<?>>aClass){

            return o;
        }
    }
