package com.codelife.cloud.question.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.codelife.cloud.annotations.LoginRequired;
import com.codelife.cloud.dto.PageDTO;
import com.codelife.cloud.dto.QuestionDTO;
import com.codelife.cloud.entities.CommonResult;
import com.codelife.cloud.entities.Question;
import com.codelife.cloud.service.QuestionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin
public class QuestionController {

    @Resource
    private QuestionService questionService;

    @RequestMapping(value = "/question/create", method = RequestMethod.POST)
    @LoginRequired
    public CommonResult create(@RequestBody Question question, HttpServletRequest request){
        Long memberId = (Long)request.getAttribute("memberId");
        if( memberId == null){
            return new CommonResult(700,"用户未登录");
        }
        question.setMemberId(memberId);
        int i = questionService.create(question);
        if (i == 0){
            return new CommonResult(500,"服务器冒烟了，稍后再试试");
        }else {
            return new CommonResult(200,"创建成功");
        }
    }

    @RequestMapping(value = "/question/listByMember/{id}/{currentPage}/{size}", method = RequestMethod.GET)
    public CommonResult list(@PathVariable("id") Long id, @PathVariable("currentPage") Integer currentPage, @PathVariable("size") Integer size){
        IPage list = questionService.list(new PageDTO(id,currentPage,size));
        return new CommonResult(200,"success",list);
    }

    @RequestMapping(value = "/question/list/{currentPage}/{size}", method = RequestMethod.GET)
    public CommonResult list(@PathVariable("currentPage") Integer currentPage, @PathVariable("size") Integer size){
        IPage list = questionService.list(new PageDTO(currentPage,size));
        return new CommonResult(200,"success",list);
    }

    @RequestMapping(value = "/question/findAll", method = RequestMethod.GET)
    public CommonResult findAll(){
        List<QuestionDTO> list = questionService.findAll();
        return new CommonResult(200,"success",list);
    }

    @RequestMapping(value = "/question/get/{id}", method = RequestMethod.GET )
    public CommonResult findQuestionById(@PathVariable("id") Long id) {
        QuestionDTO questionDTO = questionService.findById(id);
        if(questionDTO != null){
            return new CommonResult(200,"查询成功",questionDTO);
        }
        return new CommonResult(404,"页面走失了");
    }

    @RequestMapping(value = "/question/incLikeCount", method = RequestMethod.POST )
    @LoginRequired
    public CommonResult incLikeCount(Question question,HttpServletRequest request){
        Long id = (Long)request.getAttribute("id");
        if( id == null){
            return new CommonResult(700,"用户未登录");
        }
        question.setMemberId(id);
        question.setLikeCount(1L);
        int i = questionService.incLikeCount(question);
        if(i == 0){
            return new CommonResult(500,"服务器冒烟稍后再试哦");
        }
        return new CommonResult(200,"success");
    }

    @DeleteMapping("/question/deleteById/{questionId}")
    @LoginRequired
    public CommonResult deleteById(@PathVariable("questionId") Long questionId,HttpServletRequest request){
        Long id = (Long)request.getAttribute("id");
        if( id == null){
            return new CommonResult(700,"用户未登录");
        }
        QuestionDTO questionDTO = questionService.findById(questionId);
        Long memberId = questionDTO.getMemberId();
        if(id.equals(memberId)){
            int i = questionService.deleteById(id);
            return new CommonResult(200,"success");
        }else if(id.equals(10000L)){
            int i = questionService.deleteById(id);
            return new CommonResult(200,"管理员操作");
        }else {
            return new CommonResult(403,"权限不足");
        }
    }

    @RequestMapping(value = "/question/incCommentCount", method = RequestMethod.POST )
    @LoginRequired
    public CommonResult incCommentCount(Question question,HttpServletRequest request){
        Long id = (Long)request.getAttribute("id");
        if( id == null){
            return new CommonResult(700,"用户未登录");
        }
        question.setMemberId(id);
        question.setLikeCount(1L);
        int i = questionService.incCommentCount(question);
        if(i == 0){
            return new CommonResult(500,"服务器冒烟稍后再试哦");
        }
        return new CommonResult(200,"success");
    }
}
