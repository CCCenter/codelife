package com.codelife.cloud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterMemberDTO {
    private String username;
    private String password;
    private String nickName;
    private String phone;
    private String phoneCode;
}
