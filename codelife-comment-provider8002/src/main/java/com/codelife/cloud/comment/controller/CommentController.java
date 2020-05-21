package com.codelife.cloud.comment.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.codelife.cloud.annotations.LoginRequired;
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
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private CommentService commentService;


    @GetMapping("/listByQuestion/{id}/{currentPage}/{size}")
    public CommonResult listByQuestion(@PathVariable("id") Long id,@PathVariable("currentPage") Integer currentPage, @PathVariable("size") Integer size) {
        IPage<Comment> iPage = commentService.listByQuestion(new PageDTO(id,currentPage,size));
        return new CommonResult(200,"success",iPage);
    }


    @PostMapping("/create")
    @LoginRequired
    public CommonResult create(@RequestBody Comment comment,HttpServletRequest request){
        MemberDTO user = (MemberDTO)request.getAttribute("member");
        if(user == null){
            String message = (String) request.getAttribute("message");
            return new CommonResult(700,message);
        }
        comment.setCommentator(user.getId());
        int i = commentService.create(comment);
        if (i == 1){
            return new CommonResult(200,"success");
        }else{
            return new CommonResult(500,"服务器太热了稍后再试");
        }
    }

    @GetMapping("/listByComment/{id}/{currentPage}/{size}")
    public CommonResult listByComment(@PathVariable("id") Long id, @PathVariable("currentPage") Integer currentPage, @PathVariable("size") Integer size){
        IPage<Comment> iPage = commentService.listByComment(new PageDTO(id,currentPage,size));
        return new CommonResult(200,"success",iPage);
    }

    @DeleteMapping("/deleteById/{commentId}")
    @LoginRequired
    public CommonResult deleteById(@PathVariable("commentId") Long commentId,HttpServletRequest request){
        MemberDTO user = (MemberDTO)request.getAttribute("member");
        if(user == null){
            String message = (String) request.getAttribute("message");
            return new CommonResult(405,message);
        }
        CommentDTO comment = commentService.findById(commentId);
        if(user.getId().equals(comment.getCommentator())){
            commentService.deleteById(commentId);
            return new CommonResult(200,"success");
        }else if (Member.MANGER.equals(user.getRole())){
            commentService.deleteById(commentId);
            return new CommonResult(200,"管理员删除");
        }
        return new CommonResult(405,"forbidden");
    }

    @PutMapping("/incLikeCount")
    @LoginRequired
    public CommonResult incLikeCount(@RequestBody Comment comment, HttpServletRequest request) {
        MemberDTO user = (MemberDTO)request.getAttribute("member");
        if(user == null){
            String message = (String) request.getAttribute("message");
            return new CommonResult(405,message);
        }
        int i = commentService.incLikeCount(comment);
        return new CommonResult(200,"success");
    }

    @GetMapping("/list/{currentPage}/{size}")
    public CommonResult list(@PathVariable("currentPage") Integer currentPage, @PathVariable("size") Integer size){
        IPage<Comment> iPage = commentService.list(new PageDTO(currentPage,size));
        return new CommonResult(200,"success",iPage);
    }

    @GetMapping("/increased/{day}")
    public CommonResult increased(@PathVariable("day") Integer day){
        int increased = commentService.increased(day);
        return new CommonResult(200,"success",increased);
    }

}
