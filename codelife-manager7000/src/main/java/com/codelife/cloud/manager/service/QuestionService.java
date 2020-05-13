package com.codelife.cloud.manager.service;

import com.codelife.cloud.annotations.LoginRequired;
import com.codelife.cloud.entities.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@FeignClient(value = "question-service")
public interface QuestionService {
    @RequestMapping(value = "/question/list/{currentPage}/{size}", method = RequestMethod.GET)
    CommonResult list(@PathVariable("currentPage") Integer currentPage, @PathVariable("size") Integer size);

    @RequestMapping(value = "/question/listByMember/{id}/{currentPage}/{size}", method = RequestMethod.GET)
    CommonResult list(@PathVariable("id") Long id, @PathVariable("currentPage") Integer currentPage, @PathVariable("size") Integer size);

    @DeleteMapping("/question/deleteById/{questionId}")
    @LoginRequired
    CommonResult deleteById(@PathVariable("questionId") Long questionId, HttpServletRequest request);

    @RequestMapping(value = "/question/get/{id}", method = RequestMethod.GET )
    CommonResult findQuestionById(@PathVariable("id") Long id);

}
