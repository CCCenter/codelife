package com.codelife.cloud.comment.service;

import com.codelife.cloud.entities.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "member-service")
public interface MemberService {
    @GetMapping("/member/profile/{id}")
    CommonResult findById(@PathVariable("id") Long id);
}
