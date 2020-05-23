package com.codelife.cloud.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Relation {
    public static final Integer FOLLOW_QUESTION = 0;
    public static final Integer FOLLOW_MEMBER = 1;
    public static final Integer LIKE_QUESTION = 2;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private Long questionId;
    private Long memberId;
    private Long commentId;
    private Long userId;
    private Integer type;
}
