package com.codelife.cloud.dto;

import com.codelife.cloud.entities.Question;
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
public class QuestionDTO implements Serializable{
    @JsonSerialize(using = ToStringSerializer.class)
    private Long memberId;
    private String memberNickName;
    private String memberAvatar;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String title;
    private String valueContent;
    private String htmlContent;
    private String description;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long likeCount;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long viewCount;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long commentCount;
    private Date gmtCreate;
    private Date gmtModified;
    private Integer isLike;
    private String tags;
    private Long hotScore;

    public QuestionDTO(MemberDTO memberDTO, Question question) {
        this.memberNickName = memberDTO.getNickName();
        this.memberAvatar = memberDTO.getAvatar();
        this.id = question.getId();
        this.title = question.getTitle();
        this.memberId = question.getMemberId();
        this.htmlContent = question.getHtmlContent();
        this.valueContent = question.getValueContent();
        this.likeCount = question.getLikeCount();
        this.viewCount = question.getViewCount();
        this.commentCount = question.getCommentCount();
        this.gmtCreate = question.getGmtCreate();
        this.description = question.getDescription();
        this.gmtModified = question.getGmtModified();
        this.tags = question.getTags();
        if(question.getViewCount() != null){
            this.hotScore = question.getViewCount() + 5 * question.getCommentCount() +  4 * question.getLikeCount();
        }
    }
}
