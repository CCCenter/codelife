package com.codelife.cloud.question.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.codelife.cloud.annotations.LoginRequired;
import com.codelife.cloud.dto.MemberDTO;
import com.codelife.cloud.entities.CommonResult;
import com.codelife.cloud.entities.Relation;
import com.codelife.cloud.question.service.impl.RelationServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/relation")
public class RelationController {

    @Resource
    private RelationServiceImpl relationService;

    @PostMapping("/followQuestion")
    @LoginRequired
    public CommonResult followQuestion(@RequestBody Relation relation, HttpServletRequest request) {
        MemberDTO user = (MemberDTO) request.getAttribute("member");
        if (user == null) {
            String message = (String) request.getAttribute("message");
            return new CommonResult(700, message);
        }
        Long questionId = relation.getQuestionId();
        Relation entity = new Relation();
        entity.setUserId(user.getId());
        entity.setQuestionId(questionId);
        entity.setType(Relation.LIKE_QUESTION);
        int i = relationService.create(entity);
        if (i == -1) {
            return new CommonResult(200, "取消关注");
        } else {
            return new CommonResult(200, "关注成功");
        }
    }

    @PostMapping("/findRelation")
//    @LoginRequired
    public CommonResult findRelation(@RequestBody Relation entity) {
        Relation relation = relationService.findByEntity(entity);
        if (relation == null) {
            return new CommonResult(404, "未找到");
        } else {
            return new CommonResult(200, "success");
        }
    }

    @PostMapping("/list/{current}/{size}")
    public CommonResult list(@RequestBody Relation entity, @PathVariable("current") Long current, @PathVariable("size") Long size) {
        IPage list = relationService.list(entity, current, size);
        return new CommonResult(200, "success", list);
    }
}
