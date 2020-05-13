package com.codelife.cloud.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codelife.cloud.entities.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper extends BaseMapper<Member> {
}
