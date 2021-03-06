package com.codelife.cloud.dto;

import com.codelife.cloud.entities.Member;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String nickName;
    private String username;
    private String phone;
    private String avatar;
    private Integer role;
    private Date createTime;

    public MemberDTO(Member member) {
        this.username = member.getUsername();
        this.id = member.getId();
        this.nickName = member.getNickName();
        this.phone = member.getPhone();
        this.avatar = member.getAvatar();
        this.role = member.getRole();
        this.createTime = member.getCreateTime();
    }
}
