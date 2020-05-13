package com.codelife.cloud.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OauthCheckDTO implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long sourceUid;
    private Integer source;
}
