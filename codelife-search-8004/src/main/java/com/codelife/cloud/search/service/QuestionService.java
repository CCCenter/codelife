package com.codelife.cloud.search.service;

import com.codelife.cloud.entities.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "question-service")
public interface QuestionService {
    @RequestMapping(value = "/question/findAll", method = RequestMethod.GET)
    CommonResult findAll();
    @RequestMapping(value = "/question/get/{id}", method = RequestMethod.GET )
    CommonResult findQuestionById(@PathVariable("id") Long id);
}
