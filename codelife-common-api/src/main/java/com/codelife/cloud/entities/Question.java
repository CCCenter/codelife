package com.codelife.cloud.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String title;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long memberId;
    private String content;
    private String description;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long likeCount;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long viewCount;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long commentCount;
    private Date gmtCreate;
    private Date gmtModified;
    private String tags;
}
