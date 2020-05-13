package com.codelife.cloud.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.plaf.PanelUI;
import java.beans.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member implements Serializable {
    public static final Integer WEIBO = 1;
    public static final Integer GITHUB = 2;
    public static final Integer MEMBER = 0;
    public static final Integer MANGER = 1;
    public static final Integer FORBIDDEN= 2;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String username;
    private String password;
    private String nickName;
    private String phone;
    private Date createTime;
    private String city;
    private String gender;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long sourceUid;
    private Integer source;
    private Integer role;
    private String avatar;
}
