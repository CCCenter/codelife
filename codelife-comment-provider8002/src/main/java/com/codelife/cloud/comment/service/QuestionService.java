package com.codelife.cloud.comment.service;

import com.codelife.cloud.entities.CommonResult;
import com.codelife.cloud.entities.Question;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@FeignClient(value = "question-service")
public interface QuestionService {
    @RequestMapping(value = "/question/incCommentCount", method = RequestMethod.POST )
    CommonResult incCommentCount(@RequestBody Question question);
    @RequestMapping(value = "/question/get/{id}", method = RequestMethod.GET )
    CommonResult findQuestionById(@PathVariable("id") Long id);
}
