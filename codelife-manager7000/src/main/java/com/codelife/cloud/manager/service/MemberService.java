package com.codelife.cloud.manager.service;

import com.codelife.cloud.annotations.LoginRequired;
import com.codelife.cloud.entities.CommonResult;
import com.codelife.cloud.entities.Member;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

@FeignClient(value = "member-service")
public interface MemberService {

    @GetMapping("/member/profile/{id}")
    CommonResult findById(@PathVariable("id") Long id);

    @GetMapping("/member/list/{currentPage}/{size}")
    CommonResult list(@PathVariable("currentPage") Integer currentPage,@PathVariable("size") Integer size);

    @PostMapping("/member/verify")
    CommonResult verifyMember(@RequestBody Member member);

    @GetMapping("/member/disable/{id}")
    @LoginRequired
    CommonResult disable(@PathVariable("id") Long id, HttpServletRequest request);

    @GetMapping("/member/enable/{id}")
    @LoginRequired
    CommonResult enable(@PathVariable("id") Long id,HttpServletRequest request);

}
