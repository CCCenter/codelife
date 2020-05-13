package com.codelife.cloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.codelife.cloud.dto.CommentDTO;
import com.codelife.cloud.dto.PageDTO;
import com.codelife.cloud.entities.Comment;

public interface CommentService {
    IPage<Comment> listByQuestion(PageDTO pageDTO);
    IPage<Comment> listByComment(PageDTO pageDTO);
    int create(Comment comment);
    int incLikeCount(Comment comment);
    int incCommentCount(Comment comment);
    int deleteById(Long id);
    CommentDTO findById(Long id);
}
