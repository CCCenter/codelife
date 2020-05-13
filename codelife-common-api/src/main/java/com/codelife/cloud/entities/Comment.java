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
public class Comment implements Serializable {
    public static final Integer COMMENT_QUESTION = 0;
    public static final Integer COMMENT_COMMENT = 1;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;
    private Integer type;
    private Date gmtCreate;
    private Date gmtModified;
    private Long likeCount;
    private String content;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long commentCount;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long commentator;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long commentator2;
}
