package com.codelife.cloud.tag.controller;

import com.codelife.cloud.annotations.LoginRequired;
import com.codelife.cloud.dto.MemberDTO;
import com.codelife.cloud.entities.CommonResult;
import com.codelife.cloud.entities.Member;
import com.codelife.cloud.entities.Tag;
import com.codelife.cloud.service.TagService;
import com.codelife.cloud.util.sensitive.SensitiveFilter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/tag")
public class TagController {


    @Resource
    private TagService tagService;

    @GetMapping("/list/{id}")
    public CommonResult list(@PathVariable("id") Long id) {
        List list = tagService.list(id);
        return new CommonResult(200, "success", list);
    }

    @GetMapping("/list/{id}/{current}/{size}")
    public CommonResult list(@PathVariable("id") Long id, @PathVariable("current") Long current, @PathVariable("size") Long size) {
        return new CommonResult(200, "success", tagService.list(id, current, size));
    }

    @PostMapping("/create")
    @LoginRequired
    public CommonResult create(@RequestBody Tag tag, HttpServletRequest request) {
        MemberDTO member = (MemberDTO) request.getAttribute("member");
        if (member != null && member.getRole().equals(Member.MANGER)) {
            int i = tagService.create(tag);
            return new CommonResult(200, "success");
        } else {
            return new CommonResult(405, "权限不足");
        }
    }

    @PostMapping("/update")
    @LoginRequired
    public CommonResult updateById(@RequestBody Tag tag, HttpServletRequest request) {
        MemberDTO member = (MemberDTO) request.getAttribute("member");
        if (member != null && member.getRole().equals(Member.MANGER)) {
            tagService.updateById(tag);
            return new CommonResult(200, "success");
        } else {
            return new CommonResult(405, "权限不足");
        }
    }

    @DeleteMapping("/deleteById/{id}")
    @LoginRequired
    public CommonResult deleteById(@PathVariable("id") Long id, HttpServletRequest request) {
        MemberDTO member = (MemberDTO) request.getAttribute("member");
        if (member != null && member.getRole().equals(Member.MANGER)) {
            tagService.deleteById(id);
            return new CommonResult(200, "success");
        } else {
            return new CommonResult(405, "权限不足");
        }
    }
}
