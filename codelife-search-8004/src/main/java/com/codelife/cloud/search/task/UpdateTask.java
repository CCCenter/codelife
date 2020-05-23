package com.codelife.cloud.search.task;

import com.codelife.cloud.service.SearchService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class UpdateTask {
    @Resource
    private SearchService searchService;
    //每天凌晨一点更新
    @Scheduled(cron = "0 0 1 * * ?")
    public void Update(){
        searchService.put();
    }
}
