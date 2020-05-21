package com.codelife.cloud.sensitive.service;

import com.codelife.cloud.entities.CommonResult;
import com.codelife.cloud.entities.Member;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "member-service")
public interface MemberService {

    @PostMapping("/member/verify")
    CommonResult verifyMember(Member member);

    @GetMapping("/member/profile/{id}")
    CommonResult findById(@PathVariable("id") Long id);


}
