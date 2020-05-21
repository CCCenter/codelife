package com.codelife.cloud.passport.controller;

import com.codelife.cloud.dto.MemberDTO;
import com.codelife.cloud.dto.OauthCheckDTO;
import com.codelife.cloud.entities.CommonResult;
import com.codelife.cloud.entities.Member;
import com.codelife.cloud.passport.provider.GithubProvider;
import com.codelife.cloud.passport.provider.WeiboProvider;
import com.codelife.cloud.passport.service.MemberService;
import com.codelife.cloud.util.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/passport")
@CrossOrigin
public class PassportController {
    @Resource
    private MemberService memberService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private WeiboProvider weiboProvider;

    @Autowired
    private GithubProvider githubProvider;

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello";
    }

    @PostMapping("/login")
    @ResponseBody
    public CommonResult login(@RequestBody Member member) {

        String token;
        CommonResult commonResult = memberService.verifyMember(member);
        if (commonResult.getData() == null) {
            return commonResult;
        } else {
            Member data = objectMapper.convertValue(commonResult.getData(), Member.class);
            token = jwtUtils.createJwt(String.valueOf(data.getId()), data.getNickName(), new HashMap<>());
        }
        Map map = new HashMap();
        map.put("token", token);
        return new CommonResult(200, "success", map);
    }

    @PostMapping("/profile")
    @ResponseBody
    public CommonResult profile(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isEmpty(authorization)) {
            return new CommonResult(444, "未登陆");
        }
        String t = authorization.replace("Bearer ", "");

        //解析token
        try {
            Claims claims = jwtUtils.parseJwt(t);
            String memberId = claims.getId();
            //根据id查询数据
            Long id = Long.valueOf(memberId);
            CommonResult commonResult = memberService.findById(id);
            Member member = objectMapper.convertValue(commonResult.getData(), Member.class);
            return new CommonResult(200, "success", new MemberDTO(member));
        }catch (Exception e){
            return new CommonResult(700, "success");
        }

    }


    @RequestMapping("/vlogin")
    public String vlogin(String code) {
        //通过code 获取accessToken
        Map<String, Object> accessMap = weiboProvider.getAccessToken(code);
        String accessToken = (String) accessMap.get("access_token");
        String strUid = String.valueOf(accessMap.get("uid")).trim();
        System.out.println("code: "+code);
        System.out.println("uid: "+strUid);
        Long uid = Long.valueOf(strUid);
        //通过accessToken 获取用户信息
        Map<String, Object> infoMap = weiboProvider.getUserInfo(accessToken, uid);
        OauthCheckDTO oauthCheckDTO = new OauthCheckDTO(uid, Member.WEIBO);
        CommonResult result = memberService.checkOauth(oauthCheckDTO);
        Member oauthMember = new Member();
        if (result.getData() == null) {
            oauthMember.setSource(Member.WEIBO);
            oauthMember.setSourceUid(uid);
            oauthMember.setNickName((String) infoMap.get("name"));
            oauthMember.setAvatar((String) infoMap.get("avatar_hd"));
            oauthMember.setCity((String) infoMap.get("location"));
            oauthMember.setGender((String) infoMap.get("gender"));
            CommonResult result1 = memberService.create(oauthMember);
            oauthMember = objectMapper.convertValue(result1.getData(), Member.class);
        } else {
            oauthMember = objectMapper.convertValue(result.getData(), Member.class);
        }
        String token = jwtUtils.createJwt(String.valueOf(oauthMember.getId()), oauthMember.getNickName(), null);

        return "redirect:http://localhost:8080/#/loading?token=" + token;
    }

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code){//spring 自动把上下文的request 放入
        Map<String, Object> accessMap = githubProvider.getAccessToken(code);
        Map<String, Object> userInfo = githubProvider.getUserInfo((String) accessMap.get("access_token"));
        if(userInfo != null){
            Long uid = Long.valueOf(String.valueOf(userInfo.get("id")));
            OauthCheckDTO oauthCheckDTO = new OauthCheckDTO(uid, Member.GITHUB);
            CommonResult result = memberService.checkOauth(oauthCheckDTO);
            Member oauthMember = new Member();
            if (result.getData() == null) {
                oauthMember.setSource(Member.GITHUB);
                oauthMember.setSourceUid(uid);
                oauthMember.setNickName((String) userInfo.get("name"));
                oauthMember.setAvatar((String) userInfo.get("avatar_url"));
                oauthMember.setCity((String) userInfo.get("location"));
                CommonResult result1 = memberService.create(oauthMember);
                oauthMember = objectMapper.convertValue(result1.getData(), Member.class);
            } else {
                oauthMember = objectMapper.convertValue(result.getData(), Member.class);
            }
            String token = jwtUtils.createJwt(String.valueOf(oauthMember.getId()), oauthMember.getNickName(), null);

            return "redirect:http://localhost:8080/#/loading?token=" + token;
        }else{
            return "redirect:http://localhost:8080/#/login?errorCode=" + 500;
        }
    }
}
