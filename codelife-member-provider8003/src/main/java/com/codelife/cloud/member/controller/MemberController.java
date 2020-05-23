package com.codelife.cloud.member.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.codelife.cloud.annotations.LoginRequired;
import com.codelife.cloud.dto.*;
import com.codelife.cloud.entities.CommonResult;
import com.codelife.cloud.entities.Member;
import com.codelife.cloud.member.util.PhoneCode;
import com.codelife.cloud.service.MemberService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/member")
@CrossOrigin
public class MemberController {

    @Resource
    private MemberService memberService;

    @DeleteMapping("/deleteById/{memberId}")
    @LoginRequired
    public CommonResult deleteById(@PathVariable("memberId") Long memberId, HttpServletRequest request){
        Long id = (Long)request.getAttribute("id");
        if( id == null){
            return new CommonResult(700,"用户未登录");
        }
       if(id.equals(10000L)){
            int i = memberService.deleteById(id);
            return new CommonResult(200,"管理员操作");
        }else {
            return new CommonResult(403,"权限不足");
        }
    }

    @PostMapping("/create")
    public CommonResult create(@RequestBody RegisterMemberDTO registerMemberDTO) {
        if(registerMemberDTO.getPhoneCode()==null || registerMemberDTO.getPhoneCode() == ""){
            return new CommonResult(400,"请输入验证码");
        }
        Member member1 = memberService.create(registerMemberDTO);
        return new CommonResult(200,"success",member1);
    }

    @PostMapping("/phoneCheck")
    public CommonResult phoneCheck (@RequestBody RegisterMemberDTO registerMemberDTO) {
        String phone = registerMemberDTO.getPhone();
        if(PhoneCode.isChinaPhoneLegal(phone)){
            if(memberService.phoneCheck(phone)){
                return new CommonResult(200,"验证码发送成功");
            }else{
                return new CommonResult(500,"验证码发送失败");
            }
        }
        return new CommonResult(444,"号码不存在");
    }

    @GetMapping("/profile/{id}")
    public CommonResult findById(@PathVariable("id") Long id) {
        Member member = memberService.findById(id);
        if(member == null){
            return new CommonResult(404,"NotFound");
        }
        return new CommonResult(200,"success",new MemberDTO(member));
    }

    @GetMapping("/list/{currentPage}/{size}")
    @ResponseBody
    public CommonResult list(@PathVariable("currentPage") Integer currentPage,@PathVariable("size") Integer size) {
        IPage<Member> iPage = memberService.list(new PageDTO(currentPage,size));
        return new CommonResult(200,"success",iPage);
    }

    /**
     *  禁用用户
     * @param id
     * @param request
     * @return
     */
    @PostMapping("/disable/{id}")
    @LoginRequired
    public CommonResult disable(@PathVariable("id") Long id,HttpServletRequest request) {
        MemberDTO user = (MemberDTO)request.getAttribute("member");
        if((Member.MANGER).equals(user.getRole())) {
            Member member = new Member();
            member.setId(id);
            member.setRole(Member.FORBIDDEN);
            memberService.updateById(member);
            return new CommonResult(200, "success");
        }
        return new CommonResult(200,"forbidden");
    }

    /**
     *  启用用户
     * @param id
     * @param request
     * @return
     */
    @PostMapping("/enable/{id}")
    @LoginRequired
    public CommonResult enable(@PathVariable("id") Long id,HttpServletRequest request) {
        MemberDTO user = (MemberDTO)request.getAttribute("member");
        if((Member.MANGER).equals(user.getRole())){
            Member member = new Member();
            member.setId(id);
            member.setRole(Member.MEMBER);
            memberService.updateById(member);
            return new CommonResult(200,"success");
        }
        return new CommonResult(200,"forbidden");
    }

    @PostMapping("/update")
    @LoginRequired
    public CommonResult updateAvatar(@RequestBody Member member,HttpServletRequest request) {
        MemberDTO user = (MemberDTO)request.getAttribute("member");
        if(user.getId().equals(member.getId())){
            memberService.updateById(member);
            return new CommonResult(200,"success");
        }
        return new CommonResult(405,"forbidden");
    }

    @GetMapping("/test")
    @LoginRequired
    public CommonResult test(HttpServletRequest request) {
        MemberDTO user = (MemberDTO)request.getAttribute("member");
        return new CommonResult(405,"forbidden");
    }

    /**
     * 验证用户名密码
     */
    @PostMapping("/verify")
    public CommonResult verifyMember(@RequestBody Member member){
        Member verify = memberService.verify(member);
        return new CommonResult(200,"success",verify);
    }

    @PostMapping("/checkOauth")
    public CommonResult checkOauth(@RequestBody OauthCheckDTO oauthCheckDTO){
        Member member = memberService.checkOauth(oauthCheckDTO);
        if(member == null){
            return new CommonResult(404,"not fount", null);
        }
        return new CommonResult(200,"success",member);
    }

    @GetMapping("/findByUsername")
    public CommonResult findByUsername(String username){
        Member byUsername = memberService.findByUsername(username);
        return new CommonResult(200,"success",byUsername);
    }

    @GetMapping("/increased/{day}")
    public CommonResult increased(@PathVariable("day") Integer day){
        int increased = memberService.increased(day);
        return new CommonResult(200,"success",increased);
    }

}
