package com.codelife.cloud.tag.service;

import com.codelife.cloud.entities.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "question-service")
public interface QuestionService {
    @RequestMapping(value = "/question/countByTag/{tag}", method = RequestMethod.GET )
    CommonResult countByTag(@PathVariable("tag") String tag);
}
