package com.codelife.cloud.passport.service;

import com.codelife.cloud.dto.OauthCheckDTO;
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

    @PostMapping("/member/create")
    CommonResult create(Member member);

    @PostMapping("/member/checkOauth")
    CommonResult checkOauth(OauthCheckDTO oauthCheckDTO);

}
