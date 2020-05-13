package com.codelife.cloud.manager.controller;

import com.codelife.cloud.annotations.LoginRequired;
import com.codelife.cloud.dto.MemberDTO;
import com.codelife.cloud.dto.PageDTO;
import com.codelife.cloud.entities.CommonResult;
import com.codelife.cloud.entities.Member;
import com.codelife.cloud.manager.service.CommentService;
import com.codelife.cloud.manager.service.MemberService;
import com.codelife.cloud.manager.service.QuestionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/manager")
public class ManagerController {
    @Resource
    private MemberService memberService;

    @Resource
    private QuestionService questionService;

    @Resource
    private CommentService commentService;

    @Resource
    private ObjectMapper objectMapper;

    /*******************  manager api  ******************/
    /**
     * 管理员登录
     * @param member
     * @return
     */
    @PostMapping("/login")
    public CommonResult login(@RequestBody Member member){
        CommonResult result = memberService.verifyMember(member);
        Object data = result.getData();
        if(data == null){
            return new CommonResult(404,"用户名密码不存在");
        }
        Member member1 = objectMapper.convertValue(data, Member.class);
        if(Member.MANGER.equals(member1.getRole())){
            return new CommonResult(200,"管理员用户登录",member1);
        }
        return new CommonResult(405,"权限不足");
    }


    /*******************  member api  ******************/


    /**
     * member list
     * @param currentPage
     * @param size
     * @return
     */
    @GetMapping("/memberList/{currentPage}/{size}")
    public CommonResult memberList(@PathVariable("currentPage") Integer currentPage, @PathVariable("size") Integer size) {
        return memberService.list(currentPage,size);
    }

    @GetMapping("/profile/{id}")
    public CommonResult findById(@PathVariable("id") Long id) {
        return memberService.findById(id);
    }

    @GetMapping("/disableMember/{id}")
    @LoginRequired
    public CommonResult disableMember(@PathVariable("id") Long id, HttpServletRequest request){
        MemberDTO member = (MemberDTO) request.getAttribute("member");
        if(member == null){
            String message = (String) request.getAttribute("message");
            return new CommonResult(405,message);
        }else if(Member.MEMBER.equals(member.getRole())){
            return memberService.disable(id,request);
        }else{
            return new CommonResult(405,"forbidden");
        }

    }

    @GetMapping("/enableMember/{id}")
    @LoginRequired
    public CommonResult enable(@PathVariable("id") Long id,HttpServletRequest request){
        MemberDTO member = (MemberDTO) request.getAttribute("member");
        if(member == null){
            String message = (String) request.getAttribute("message");
            return new CommonResult(405,message);
        }else if(Member.MEMBER.equals(member.getRole())){
            return  memberService.enable(id,request);
        }else{
            return new CommonResult(405,"forbidden");
        }
    }

    /*******************  question api  ******************/


    @RequestMapping(value = "/question/list/{currentPage}/{size}", method = RequestMethod.GET)
    public CommonResult questionList(@PathVariable("currentPage") Integer currentPage, @PathVariable("size") Integer size){
        return questionService.list(currentPage, size);
    }

    @RequestMapping(value = "/question/listByMember/{id}/{currentPage}/{size}", method = RequestMethod.GET)
    public CommonResult questionListById(@PathVariable("id") Long id, @PathVariable("currentPage") Integer currentPage, @PathVariable("size") Integer size){
        return questionService.list(id, currentPage, size);
    }

    @DeleteMapping("/question/deleteById/{questionId}")
    @LoginRequired
    public CommonResult deleteQuestionById(@PathVariable("questionId") Long questionId, HttpServletRequest request){
        MemberDTO member = (MemberDTO) request.getAttribute("member");
        if(member == null){
            String message = (String) request.getAttribute("message");
            return new CommonResult(405,message);
        }else if(Member.MEMBER.equals(member.getRole())){
            return questionService.deleteById(questionId, request);
        }else{
            return new CommonResult(405,"forbidden");
        }
    }

    @RequestMapping(value = "/question/get/{id}", method = RequestMethod.GET )
    public CommonResult findQuestionById(@PathVariable("id") Long id){
        return questionService.findQuestionById(id);
    }

    /*******************  comment api  ******************/

    @GetMapping("comment/listByQuestion/{id}/{currentPage}/{size}")
    public CommonResult listByQuestion(@PathVariable("id") Long id,@PathVariable("currentPage") Integer currentPage, @PathVariable("size") Integer size){
        return commentService.listByQuestion(id, currentPage, size);
    }

    @GetMapping("comment/listByComment/{id}/{currentPage}/{size}")
    public CommonResult listByComment(@PathVariable("id") Long id, @PathVariable("currentPage") Integer currentPage, @PathVariable("size") Integer size){
        return commentService.listByComment(id, currentPage, size);
    }

    @DeleteMapping("comment/deleteById/{commentId}")
    @LoginRequired
    public CommonResult deleteCommentById(@PathVariable("commentId") Long commentId, HttpServletRequest request){
        MemberDTO member = (MemberDTO) request.getAttribute("member");
        if(member == null){
            String message = (String) request.getAttribute("message");
            return new CommonResult(405,message);
        }else if(Member.MEMBER.equals(member.getRole())){
            return commentService.deleteById(commentId, request);
        }else{
            return new CommonResult(405,"forbidden");
        }
    }
}
