package com.codelife.cloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.codelife.cloud.dto.PageDTO;
import com.codelife.cloud.dto.QuestionDTO;
import com.codelife.cloud.entities.Question;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface QuestionService {
    int create(Question question);
    int update(Question question);
    QuestionDTO findById(Long id);
    Integer countByTag(String tag);
    int deleteById(Long id);
    IPage list(PageDTO pageDTO);
    IPage listByTag(String tag,PageDTO pageDTO);
    int incViewCount(Question question);
    int incLikeCount(Question question);
    int incCommentCount(Question question);
    int increased(Integer day);
    List findAll();
    List hotList();
}
