package com.codelife.cloud.search.controller;

import com.codelife.cloud.dto.PageDTO;
import com.codelife.cloud.dto.QuestionDTO;
import com.codelife.cloud.entities.CommonResult;
import com.codelife.cloud.entities.Question;
import com.codelife.cloud.service.SearchService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Resource
    private SearchService searchService;

    @GetMapping("/list/{keyword}/{currentPage}/{size}")
    public CommonResult list(@PathVariable("keyword") String keyword, @PathVariable("currentPage") Integer currentPage, @PathVariable("size") Integer size){
        PageDTO list = searchService.list(keyword, currentPage, size);
        return new CommonResult(200,"success",list);
    }

    @GetMapping("/put")
    public String put(){
        return searchService.put();
    }

    @PostMapping("/putOne")
    public String putOne(@RequestBody Question question){
        return searchService.put(question);
    }
}
