package com.codelife.cloud.question.service.impl;

import com.alibaba.fastjson.JSON;
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
import com.codelife.cloud.question.service.SearchService;
import com.codelife.cloud.service.QuestionService;
import com.codelife.cloud.util.RedisUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private MemberService memberService;

    @Resource
    private SearchService searchService;

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public int create(Question question) {
        question.setGmtCreate(new Date());
        question.setLikeCount(0L);
        question.setCommentCount(0L);
        question.setViewCount(0L);
        int insert = questionMapper.insert(question);
        searchService.putOne(question);
        return insert;
    }
    @Override
    public int update(Question question){
        return questionMapper.updateById(question);
    }

    @Override
    public Integer countByTag(String tag){
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.last("where tags regexp" + "'"+ tag +"'");
        return questionMapper.selectCount(queryWrapper);
    }
    @Override
    public IPage<QuestionDTO> listByTag(String tag,PageDTO pageDTO){
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.last("where tags regexp" + "'"+ tag +"'");
        Page<Question> page = new Page(pageDTO.getCurrentPage(),pageDTO.getSize());
        IPage iPage = null;
        try{
            iPage = questionMapper.selectPage(page, queryWrapper);
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
        }catch (Exception e){
            e.printStackTrace();
        }
        return iPage;
    }

    @Override
    public QuestionDTO findById(Long id) {
        Question question = questionMapper.selectById(id);
        if(question == null){
            return null;
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
    public List<QuestionDTO> hotList() {
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        if(redisUtil.get("hotList") == null){
            System.out.println("from db");
            QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
            queryWrapper.last("where DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(gmt_create)");
            List<Question> questions = questionMapper.selectList(queryWrapper);
            for (Question question : questions) {
                CommonResult result = memberService.findById(question.getMemberId());
                Member member = objectMapper.convertValue(result.getData(), Member.class);
                QuestionDTO questionDTO = new QuestionDTO(new MemberDTO(member), question);
                questionDTOS.add(questionDTO);
            }
            Collections.sort(questionDTOS, (o1, o2) -> Integer.valueOf(String.valueOf(o2.getHotScore()-o1.getHotScore())));
            if(questionDTOS.size() > 10){
                questionDTOS = questionDTOS.subList(0,11);
            }
            redisUtil.set("hotList",JSON.toJSONString(questionDTOS),24 * 60 * 60);
        }else{
            System.out.println("from redis");
            String hotList = String.valueOf(redisUtil.get("hotList"));
            questionDTOS = JSON.parseArray(hotList, QuestionDTO.class);
        }
        return questionDTOS;
    }

    @Override
    public IPage<QuestionDTO> list(PageDTO pageDTO) {
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
        question.setViewCount(1L);
        return questionMapper.incViewCount(question);
    }

    @Override
    public int incLikeCount(Question question) {
        question.setLikeCount(1L);
        return questionMapper.incLikeCount(question);
    }

    @Override
    public int incCommentCount(Question question) {
        question.setCommentCount(1L);
        return questionMapper.incCommentCount(question);
    }

    @Override
    public int increased(Integer day) {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.last("where DATE_SUB(CURDATE(), INTERVAL "+ day + " DAY) <= date(gmt_create)");
        Integer integer = questionMapper.selectCount(queryWrapper);
        return integer;
    }
}
