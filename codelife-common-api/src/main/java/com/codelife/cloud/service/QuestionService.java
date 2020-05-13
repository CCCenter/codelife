package com.codelife.cloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.codelife.cloud.dto.PageDTO;
import com.codelife.cloud.dto.QuestionDTO;
import com.codelife.cloud.entities.Question;

import java.util.List;

public interface QuestionService {
    int create(Question question);
    QuestionDTO findById(Long id);
    int deleteById(Long id);
    IPage list(PageDTO pageDTO);
    int incViewCount(Question question);
    int incLikeCount(Question question);
    int incCommentCount(Question question);
    List<QuestionDTO> findAll();
}
