package com.codelife.cloud.question.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codelife.cloud.dto.QuestionDTO;
import com.codelife.cloud.entities.Relation;
import com.codelife.cloud.question.mapper.RelationMapper;
import com.codelife.cloud.service.QuestionService;
import com.codelife.cloud.service.RelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class RelationServiceImpl implements RelationService {

    @Resource
    private RelationMapper relationMapper;

    @Resource
    private QuestionService questionService;

    @Override
    public int create(Relation entity) {
        Relation relation = findByEntity(entity);
        if(relation != null){
            relationMapper.deleteById(relation.getId());
            return -1;
        }
        return relationMapper.insert(entity);
    }

    @Override
    public Relation findByEntity(Relation entity){
        QueryWrapper queryWrapper = new QueryWrapper();
        if(entity.getMemberId() != null){
            queryWrapper.eq("member_id",entity.getMemberId());
        }
        if(entity.getCommentId() != null){
            queryWrapper.eq("comment_id",entity.getCommentId());
        }
        if(entity.getUserId() != null){
            queryWrapper.eq("user_id",entity.getUserId());
        }
        if(entity.getQuestionId() != null){
            queryWrapper.eq("question_id",entity.getQuestionId());
        }
        if (entity.getType() != null){
            queryWrapper.eq("type",entity.getType());
        }

        Relation relation = relationMapper.selectOne(queryWrapper);
        return relation;
    }

    @Override
    public IPage list(Relation entity,Long current, Long size){

        QueryWrapper queryWrapper = new QueryWrapper();
        if(entity.getMemberId() != null){
            queryWrapper.eq("member_id",entity.getMemberId());
        }
        if(entity.getCommentId() != null){
            queryWrapper.eq("comment_id",entity.getCommentId());
        }
        if(entity.getUserId() != null){
            queryWrapper.eq("user_id",entity.getUserId());
        }
        if(entity.getQuestionId() != null){
            queryWrapper.eq("question_id",entity.getQuestionId());
        }
        if (entity.getType() != null){
            queryWrapper.eq("type",entity.getType());
        }
        Page page = new Page(current,size,relationMapper.selectCount(queryWrapper));
        IPage iPage = relationMapper.selectPage(page, queryWrapper);
        List<Relation> records = iPage.getRecords();
        List data = new ArrayList();

        if(entity.getType() == Relation.FOLLOW_QUESTION || entity.getType() == Relation.LIKE_QUESTION){
            for (Relation record : records) {
                QuestionDTO questionDTO = questionService.findById(record.getQuestionId());
                data.add(questionDTO);
            }
        }else if(entity.getType() == Relation.FOLLOW_MEMBER){

        }
        iPage.setRecords(data);
        return iPage;
    }
}
