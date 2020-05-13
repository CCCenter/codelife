package com.codelife.cloud.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codelife.cloud.dto.OauthCheckDTO;
import com.codelife.cloud.dto.PageDTO;
import com.codelife.cloud.dto.RegisterMemberDTO;
import com.codelife.cloud.entities.Member;
import com.codelife.cloud.member.mapper.MemberMapper;
import com.codelife.cloud.member.util.PhoneCode;
import com.codelife.cloud.service.MemberService;
import com.codelife.cloud.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class MemberServiceImpl implements MemberService {

    @Autowired
    private RedisUtil redisUtil;

    @Resource
    private MemberMapper memberMapper;

    @Override
    public Member create(RegisterMemberDTO registerMemberDTO) {
        String phone = registerMemberDTO.getPhone();
        String phoneCode = registerMemberDTO.getPhoneCode();
        String phoneCodeFromCache = String.valueOf(redisUtil.get(phone));
        Member member = new Member();
        if(phoneCode.equals(phoneCodeFromCache)){
            member.setUsername(registerMemberDTO.getUsername());
            member.setPassword(registerMemberDTO.getPassword());
            member.setPhone(registerMemberDTO.getPhone());
            member.setNickName("cl_" + (int)(Math.random() *10000000));
            int insert = memberMapper.insert(member);
        }
        return member;
    }

    @Override
    public Member findById(Long id) {
        Member member = memberMapper.selectById(id);
        return member;
    }

    @Override
    public IPage<Member> list(PageDTO pageDTO) {
        Integer count = memberMapper.selectCount(null);
        Page page = new Page(pageDTO.getCurrentPage(),pageDTO.getSize(),count);
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        return memberMapper.selectPage(page, queryWrapper);
    }


    @Override
    public Member findByUsername(String username) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        Member member = memberMapper.selectOne(queryWrapper);
        return member;
    }

    @Override
    public Member verify(Member member) {
        log.info("verify start");
        Member byUsername = findByUsername(member.getUsername());
        //验证成功
        System.out.println("member:" + byUsername);
        if(byUsername != null && byUsername.getPassword().equals(member.getPassword())){
            return byUsername;
        }else {
            return null;
        }
    }

    @Override
    public Member checkOauth(OauthCheckDTO oauthCheckDTO) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("source_uid",oauthCheckDTO.getSourceUid());
        queryWrapper.eq("source",oauthCheckDTO.getSource());
        Member member = memberMapper.selectOne(queryWrapper);
        return member;
    }

    @Override
    public boolean phoneCheck(String phoneNumber) {
        StringBuffer stringBuffer=new StringBuffer();
        for (int x=0;x<=5;x++) {
            int random = (int) (Math.random() * (10 - 1));
            stringBuffer.append(random);
        }
        String code = stringBuffer.toString();
        redisUtil.set(phoneNumber,code,300);
        boolean send = PhoneCode.send(phoneNumber, code);
        return send;
    }

    @Override
    public int deleteById(Long id) {
        return memberMapper.deleteById(id);
    }

    @Override
    public int updateById(Member member) {
        return memberMapper.updateById(member);
    }

}
