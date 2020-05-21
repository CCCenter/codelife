package com.codelife.cloud.question.service;

import com.codelife.cloud.entities.Question;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "search-service")
public interface SearchService {
    @PostMapping("/search/putOne")
    String putOne(@RequestBody Question question);
}
