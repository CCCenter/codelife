package com.codelife.cloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.codelife.cloud.dto.OauthCheckDTO;
import com.codelife.cloud.dto.PageDTO;
import com.codelife.cloud.dto.RegisterMemberDTO;
import com.codelife.cloud.entities.Member;

public interface MemberService {
    Member create(RegisterMemberDTO registerMemberDTO);
    Member findById(Long id);
    IPage<Member> list(PageDTO pageDTO);
    Member findByUsername(String username);
    Member verify(Member member);
    Member checkOauth(OauthCheckDTO oauthCheckDTO);
    boolean phoneCheck(String phoneNumber);
    int deleteById(Long id);
    int updateById(Member member);

}
