package com.codelife.cloud.comment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codelife.cloud.entities.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    int incLikeCount(Comment comment);
    int incCommentCount(Comment comment);
}
