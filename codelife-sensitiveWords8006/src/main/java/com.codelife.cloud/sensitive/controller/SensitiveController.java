package com.codelife.cloud.sensitive.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.codelife.cloud.annotations.LoginRequired;
import com.codelife.cloud.dto.MemberDTO;
import com.codelife.cloud.entities.CommonResult;
import com.codelife.cloud.entities.Member;
import com.codelife.cloud.entities.SensitiveWords;
import com.codelife.cloud.service.SensitiveService;
import com.codelife.cloud.util.sensitive.SensitiveFilter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/sensitive")
public class SensitiveController {

    @Resource
    private SensitiveService sensitiveService;

    private SensitiveFilter filter = SensitiveFilter.DEFAULT;

    @GetMapping("/list/{current}/{size}")
    public CommonResult list(@PathVariable("current") Long current, @PathVariable("size") Long size){
        IPage list = sensitiveService.list(current, size);
        return new CommonResult(200,"success",list);
    }

    @PostMapping("/create")
    @LoginRequired
    public CommonResult create(@RequestBody SensitiveWords sensitiveWords,HttpServletRequest request){
        MemberDTO member = (MemberDTO) request.getAttribute("member");
        if(member == null){
            return new CommonResult(700,"未登录");
        }else if (member.getRole().equals(Member.MANGER)){
            sensitiveWords.setMemberId(member.getId());
            int i = sensitiveService.create(sensitiveWords);
            filter.put(sensitiveWords.getSensitiveWord());
            return new CommonResult(200,"success");
        }else{
            return new CommonResult(405,"权限不足");
        }
    }

    @DeleteMapping("/delete/{id}")
    @LoginRequired
    public CommonResult delete(@PathVariable("id") Long id, HttpServletRequest request){
        MemberDTO member = (MemberDTO) request.getAttribute("member");
        if(member == null){
            return new CommonResult(700,"未登录");
        }else if (member.getRole().equals(Member.MANGER)){
            SensitiveWords sensitiveWord = sensitiveService.getById(id);
            filter.remove(sensitiveWord.getSensitiveWord());
            sensitiveService.deleteById(id);
            return new CommonResult(200,"success");
        }else{
            return new CommonResult(405,"权限不足");
        }
    }
}
