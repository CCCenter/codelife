package com.codelife.cloud.comment.service;

import com.codelife.cloud.entities.CommonResult;
import com.codelife.cloud.entities.Question;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "question-service")
public interface QuestionService {
    @RequestMapping(value = "/question/incCommentCount", method = RequestMethod.POST )
    CommonResult incCommentCount(Question question);
}
