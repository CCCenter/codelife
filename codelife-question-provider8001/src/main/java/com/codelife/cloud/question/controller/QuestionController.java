package com.codelife.cloud.question.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.codelife.cloud.annotations.LoginRequired;
import com.codelife.cloud.dto.MemberDTO;
import com.codelife.cloud.dto.PageDTO;
import com.codelife.cloud.dto.QuestionDTO;
import com.codelife.cloud.entities.CommonResult;
import com.codelife.cloud.entities.Member;
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

    @RequestMapping(value = "/question/createOrUpdate", method = RequestMethod.POST)
    @LoginRequired
    public CommonResult createOrUpdate(@RequestBody Question question, HttpServletRequest request){
        MemberDTO user = (MemberDTO)request.getAttribute("member");
        if(user == null){
            String message = (String) request.getAttribute("message");
            return new CommonResult(700,message);
        }
        if(question.getId() != null){
            if(question.getMemberId().equals(user.getId())){
                questionService.update(question);
                return new CommonResult(200,"success");
            }else{
                return new CommonResult(405,"forbidden");
            }
        }
        question.setMemberId(user.getId());
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

    @RequestMapping(value = "/question/list/{tag}/{currentPage}/{size}", method = RequestMethod.GET)
    public CommonResult listByTag(@PathVariable("tag") String tag,@PathVariable("currentPage") Integer currentPage, @PathVariable("size") Integer size){
        IPage list = questionService.listByTag(tag,new PageDTO(currentPage,size));
        return new CommonResult(200,"success",list);
    }
    @RequestMapping(value = "/question/hotList", method = RequestMethod.GET)
    public CommonResult hotList(){
        List list = questionService.hotList();
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

    @RequestMapping(value = "/question/countByTag/{tag}", method = RequestMethod.GET )
    public CommonResult countByTag(@PathVariable("tag") String tag) {
        return new CommonResult(200,"success",questionService.countByTag(tag));
    }

    @RequestMapping(value = "/question/incLikeCount/{id}", method = RequestMethod.POST)
    @LoginRequired
    public CommonResult incLikeCount(@PathVariable("id") Long id ,HttpServletRequest request){
        MemberDTO user = (MemberDTO)request.getAttribute("member");
        if(user == null){
            String message = (String) request.getAttribute("message");
            return new CommonResult(700,message);
        }
        Question question = new Question();
        question.setId(id);
        question.setMemberId(user.getId());
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
        MemberDTO user = (MemberDTO)request.getAttribute("member");
        if(user == null){
            String message = (String) request.getAttribute("message");
            return new CommonResult(700,message);
        }
        QuestionDTO questionDTO = questionService.findById(questionId);
        if(Member.MANGER.equals(user.getRole())){
            questionService.deleteById(questionId);
            return new CommonResult(200,"success");
        }else if(questionDTO.getMemberId().equals(user.getId())){
            int i = questionService.deleteById(questionId);
            return new CommonResult(200,"success");
        }else{
            return new CommonResult(405,"forbidden");
        }
    }

    @RequestMapping(value = "/question/incCommentCount", method = RequestMethod.POST )
    public CommonResult incCommentCount(@RequestBody Question question){
//        MemberDTO user = (MemberDTO)request.getAttribute("member");
//        if(user == null){
//            String message = (String) request.getAttribute("message");
//            return new CommonResult(700,message);
//        }
        int i = questionService.incCommentCount(question);
        return new CommonResult(200,"success");
    }

    @GetMapping("/question/increased/{day}")
    public CommonResult increased(@PathVariable("day") Integer day){
        int increased = questionService.increased(day);
        return new CommonResult(200,"success",increased);
    }
}
