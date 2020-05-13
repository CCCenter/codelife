package com.codelife.cloud.question.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codelife.cloud.dto.MemberDTO;
import com.codelife.cloud.dto.PageDTO;
import com.codelife.cloud.dto.QuestionDTO;
import com.codelife.cloud.entities.CommonResult;
import com.codelife.cloud.entities.Member;
import com.codelife.cloud.entities.Question;
import com.codelife.cloud.question.mapper.QuestionMapper;
import com.codelife.cloud.question.service.MemberService;
import com.codelife.cloud.service.QuestionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private MemberService memberService;

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public int create(Question question) {
        return questionMapper.insert(question);
    }

    @Override
    public QuestionDTO findById(Long id) {
        Question question = questionMapper.selectById(id);
        if(question == null){
            return new QuestionDTO();
        }
        Long viewCount = question.getViewCount();
        question.setViewCount(1L);
        questionMapper.incViewCount(question);
        question.setViewCount(viewCount);

        CommonResult result = memberService.findById(question.getMemberId());
        Member member = objectMapper.convertValue(result.getData(), Member.class);
        MemberDTO memberDTO = new MemberDTO(member);

        QuestionDTO questionDTO = new QuestionDTO(memberDTO,question);

        return questionDTO;
    }

    @Override
    public int deleteById(Long id) {
        return questionMapper.deleteById(id);
    }

    @Override
    public List<QuestionDTO> findAll() {
        List<Question> questions = questionMapper.selectList(null);
        List<QuestionDTO> questionDTOS = new ArrayList<>();

        for (Question question : questions) {
            CommonResult result = memberService.findById(question.getMemberId());
            Member member = objectMapper.convertValue(result.getData(), Member.class);
            MemberDTO memberDTO = new MemberDTO(member);
            QuestionDTO questionDTO = new QuestionDTO(memberDTO,question);
            questionDTOS.add(questionDTO);
        }
        return questionDTOS;
    }

    @Override
    public IPage<Question> list(PageDTO pageDTO) {
        Integer count = questionMapper.selectCount(null);
        Page page = new Page(pageDTO.getCurrentPage(),pageDTO.getSize(),count);
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        Long memberId = pageDTO.getId();
        if(memberId != null){
            queryWrapper.eq("member_id",memberId);
        }
        queryWrapper.orderByDesc("gmt_create");
        IPage iPage = questionMapper.selectPage(page, queryWrapper);
        List records = iPage.getRecords();
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Object record : records) {

            CommonResult result = memberService.findById(((Question) record).getMemberId());
            Object obj = result.getData();
            Member member = objectMapper.convertValue(obj, Member.class);
            QuestionDTO questionDTO = new QuestionDTO(new MemberDTO(member),(Question) record);
            questionDTOS.add(questionDTO);
        }
        iPage.setRecords(questionDTOS);
        return iPage;
    }


    @Override
    public int incViewCount(Question question) {
        return questionMapper.incViewCount(question);
    }

    @Override
    public int incLikeCount(Question question) {
        return questionMapper.incLikeCount(question);
    }

    @Override
    public int incCommentCount(Question question) {
        return questionMapper.incCommentCount(question);
    }


}
