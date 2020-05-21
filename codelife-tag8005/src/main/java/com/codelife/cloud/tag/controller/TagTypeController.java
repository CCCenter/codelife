package com.codelife.cloud.tag.controller;

import com.codelife.cloud.annotations.LoginRequired;
import com.codelife.cloud.entities.CommonResult;
import com.codelife.cloud.entities.Member;
import com.codelife.cloud.entities.TagType;
import com.codelife.cloud.service.TagTypeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/tagType")
public class TagTypeController {
    @Resource
    private TagTypeService tagTypeService;

    @GetMapping("/list")
    public CommonResult list() {
        return new CommonResult(200, "success", tagTypeService.list());
    }

    @GetMapping("/list/{current}/{size}")
    public CommonResult list(@PathVariable("current") Long current, @PathVariable("size") Long size) {
        return new CommonResult(200, "success", tagTypeService.list(current,size));
    }

    @GetMapping("/getById/{id}")
    public CommonResult getById(@PathVariable("id") Long id) {
        return new CommonResult(200, "success", tagTypeService.getById(id));
    }


    @PostMapping("/create")
    @LoginRequired
    CommonResult create(@RequestBody TagType tagType, HttpServletRequest request) {
        Member member = (Member) request.getAttribute("member");
        if (member != null && member.getRole().equals(Member.MANGER)) {
            tagTypeService.create(tagType);
            return new CommonResult(200, "success");
        } else {
            return new CommonResult(405, "权限不足");
        }
    }

    @DeleteMapping("/delete/{id}")
    @LoginRequired
    CommonResult deleteById(@PathVariable("id") Long id, HttpServletRequest request) {
        Member member = (Member) request.getAttribute("member");
        if (member != null && member.getRole().equals(Member.MANGER)) {
            tagTypeService.deleteById(id);
            return new CommonResult(200, "success");
        } else {
            return new CommonResult(405, "权限不足");
        }
    }

    @PostMapping("/updateById")
    @LoginRequired
    CommonResult updateById(@RequestBody TagType tagType, HttpServletRequest request) {
        Member member = (Member) request.getAttribute("member");
        if (member != null && member.getRole().equals(Member.MANGER)) {
            tagTypeService.updateById(tagType);
            return new CommonResult(200, "success");
        } else {
            return new CommonResult(405, "权限不足");
        }
    }

}
