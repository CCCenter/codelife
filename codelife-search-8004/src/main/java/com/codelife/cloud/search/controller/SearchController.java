package com.codelife.cloud.search.controller;

import com.codelife.cloud.dto.QuestionDTO;
import com.codelife.cloud.service.SearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Resource
    private SearchService searchService;

    @GetMapping("/list")
    public List<QuestionDTO> list(String keyword){
        return searchService.list(keyword);
    }

    @GetMapping("/put")
    public String put(){
        return searchService.put();
    }
}
