package com.codelife.cloud.sensitive.config;

import com.codelife.cloud.service.SensitiveService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import javax.annotation.Resource;
import java.util.stream.Stream;

@Configuration
public class SensitiveConfig {
    @Resource
    private SensitiveService sensitiveService;

    @Bean
    public ApplicationRunner initData2(){
        return (args) -> {
            System.out.println("sensitive init.");
            Stream.of(args.getSourceArgs()).forEach(System.out::println);
            sensitiveService.initSensitive();
        };
    }
}
