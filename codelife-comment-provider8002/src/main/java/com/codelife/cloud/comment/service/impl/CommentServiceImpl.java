package com.codelife.cloud.comment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codelife.cloud.comment.mapper.CommentMapper;
import com.codelife.cloud.comment.service.MemberService;
import com.codelife.cloud.comment.service.QuestionService;
import com.codelife.cloud.dto.CommentDTO;
import com.codelife.cloud.dto.MemberDTO;
import com.codelife.cloud.dto.PageDTO;
import com.codelife.cloud.dto.QuestionDTO;
import com.codelife.cloud.entities.Comment;
import com.codelife.cloud.entities.CommonResult;
import com.codelife.cloud.entities.Member;
import com.codelife.cloud.entities.Question;
import com.codelife.cloud.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private MemberService memberService;
    @Resource
    private QuestionService questionService;
    @Resource
    private ObjectMapper objectMapper;

    @Override
    public IPage<Comment> list(PageDTO pageDTO) {
        Page page = new Page(pageDTO.getCurrentPage(), pageDTO.getSize());
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");
        IPage iPage = commentMapper.selectPage(page, queryWrapper);
        List records = iPage.getRecords();
        //封装 member 到 records
        List<CommentDTO> comments = new ArrayList<>();
        for (Object record : records) {
            Comment comment = (Comment) record;
            CommonResult questionById = questionService.findQuestionById(comment.getQuestionId());
            CommonResult result = memberService.findById(comment.getCommentator());
            CommonResult result2 = memberService.findById(comment.getCommentator2());
            QuestionDTO questionDTO = objectMapper.convertValue(questionById.getData(), QuestionDTO.class);
            Member member = objectMapper.convertValue(result.getData(), Member.class);
            Member member2 = objectMapper.convertValue(result2.getData(), Member.class);
            CommentDTO commentDTO = new CommentDTO(comment, new MemberDTO(member), new MemberDTO(member2));
            if (questionDTO != null) {
                commentDTO.setQuestionTitle(questionDTO.getTitle());
            }
            comments.add(commentDTO);
        }
        iPage.setRecords(comments);
        return iPage;
    }

    @Override
    public IPage<Comment> listByQuestion(PageDTO pageDTO) {
        Long id = pageDTO.getId();
        Integer currentPage = pageDTO.getCurrentPage();
        Integer size = pageDTO.getSize();
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", Comment.COMMENT_QUESTION);
        queryWrapper.eq("parent_id", id);
        queryWrapper.orderByDesc("gmt_create");
        Page page = new Page(currentPage, size);
        IPage iPage = commentMapper.selectPage(page, queryWrapper);
        List records = iPage.getRecords();
        //封装 member 到 records
        List<CommentDTO> comments = new ArrayList<>();
        for (Object record : records) {
            Comment comment = (Comment) record;
            CommonResult result = memberService.findById(comment.getCommentator());
            CommonResult result2 = memberService.findById(comment.getCommentator2());
            Member member = objectMapper.convertValue(result.getData(), Member.class);
            Member member2 = objectMapper.convertValue(result2.getData(), Member.class);
            CommentDTO commentDTO = new CommentDTO(comment, new MemberDTO(member), new MemberDTO(member2));
            comments.add(commentDTO);
        }
        iPage.setRecords(comments);
        return iPage;
    }

    @Override
    public IPage<Comment> listByComment(PageDTO pageDTO) {
        Long id = pageDTO.getId();
        Integer currentPage = pageDTO.getCurrentPage();
        Integer size = pageDTO.getSize();
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", Comment.COMMENT_COMMENT);
        queryWrapper.eq("parent_id", id);
        queryWrapper.orderByDesc("gmt_create");
        Page page = new Page(currentPage, size);
        IPage iPage = commentMapper.selectPage(page, queryWrapper);
        List records = iPage.getRecords();
        //封装 member 到 records
        List<CommentDTO> comments = new ArrayList<>();
        for (Object record : records) {
            Comment comment = (Comment) record;
            CommonResult result = memberService.findById(comment.getCommentator());
            CommonResult result2 = memberService.findById(comment.getCommentator2());
            Member member = objectMapper.convertValue(result.getData(), Member.class);
            Member member2 = objectMapper.convertValue(result2.getData(), Member.class);
            CommentDTO commentDTO = new CommentDTO(comment, new MemberDTO(member), new MemberDTO(member2));
            comments.add(commentDTO);
        }
        iPage.setRecords(comments);
        return iPage;
    }

    @Override
    public int create(Comment comment) {
        int insert = commentMapper.insert(comment);
        Question question = new Question();
        question.setId(comment.getQuestionId());
        questionService.incCommentCount(question);
        if (comment.getType() == Comment.COMMENT_COMMENT) {
            Comment comm = new Comment();
            comm.setId(comment.getParentId());
            incCommentCount(comm);
        }
        return insert;
    }

    @Override
    public int incLikeCount(Comment comment) {
        comment.setLikeCount(1L);
        return commentMapper.incLikeCount(comment);
    }

    @Override
    public int incCommentCount(Comment comment) {
        comment.setCommentCount(1L);
        return commentMapper.incCommentCount(comment);
    }

    public CommentDTO findById(Long id) {
        Comment comment = commentMapper.selectById(id);
        return new CommentDTO(comment);
    }

    @Override
    public int deleteById(Long id) {
        return commentMapper.deleteById(id);
    }

    @Override
    public int increased(Integer day) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.last("where DATE_SUB(CURDATE(), INTERVAL "+ day + " DAY) <= date(gmt_create)");
        Integer integer = commentMapper.selectCount(queryWrapper);
        return integer;
    }

}
