package com.codelife.cloud.service;

import com.codelife.cloud.dto.PageDTO;
import com.codelife.cloud.dto.QuestionDTO;
import com.codelife.cloud.entities.Question;

import java.util.List;

public interface SearchService {
    PageDTO list(String keyword, Integer currentPage, Integer size);
    String put();
    String put(Question question);
}
