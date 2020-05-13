package com.codelife.cloud.question.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codelife.cloud.entities.Question;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper extends BaseMapper<Question> {

    int incViewCount(Question question);

    int incLikeCount(Question question);

    int incCommentCount(Question question);
}
