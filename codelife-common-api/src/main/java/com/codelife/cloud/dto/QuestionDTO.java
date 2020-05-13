package com.codelife.cloud.dto;

import com.codelife.cloud.entities.Question;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long memberId;
    private String memberNickName;
    private String memberAvatar;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String title;
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
    private String tags[];

    public QuestionDTO(MemberDTO memberDTO, Question question) {
        this.memberNickName = memberDTO.getNickName();
        this.memberAvatar = memberDTO.getAvatar();
        this.id = question.getId();
        this.title = question.getTitle();
        this.memberId = question.getMemberId();
        this.content = question.getContent();
        this.likeCount = question.getLikeCount();
        this.viewCount = question.getViewCount();
        this.commentCount = question.getCommentCount();
        this.gmtCreate = question.getGmtCreate();
        this.description = question.getDescription();
        this.gmtModified = question.getGmtModified();
        if (StringUtils.isNotBlank(question.getTags())) {
            this.tags = question.getTags().trim().split(",");
        }

    }
}
