package com.codelife.cloud.manager.service;

import com.codelife.cloud.annotations.LoginRequired;
import com.codelife.cloud.entities.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@FeignClient(value = "comment-service")
public interface CommentService {

    @GetMapping("/listByQuestion/{id}/{currentPage}/{size}")
    CommonResult listByQuestion(@PathVariable("id") Long id,@PathVariable("currentPage") Integer currentPage, @PathVariable("size") Integer size);

    @GetMapping("/listByComment/{id}/{currentPage}/{size}")
    CommonResult listByComment(@PathVariable("id") Long id, @PathVariable("currentPage") Integer currentPage, @PathVariable("size") Integer size);

    @DeleteMapping("/deleteById/{commentId}")
    @LoginRequired
    CommonResult deleteById(@PathVariable("commentId") Long commentId, HttpServletRequest request);

}
