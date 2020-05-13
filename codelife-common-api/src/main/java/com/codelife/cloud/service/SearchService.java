package com.codelife.cloud.service;

import com.codelife.cloud.dto.QuestionDTO;

import java.util.List;

public interface SearchService {
    List<QuestionDTO> list(String keyword);
    String put();
}
