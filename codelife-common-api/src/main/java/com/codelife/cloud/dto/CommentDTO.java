package com.codelife.cloud.dto;

import com.codelife.cloud.entities.Comment;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;
    private Integer type;
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date gmtCreate;
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date gmtModified;
    private Long likeCount;
    private String content;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long commentCount;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long commentator;
    private String commentatorNickName;
    private String commentatorAvatar;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long commentator2;
    private String commentator2NickName;
    private String commentator2Avatar;
    private List<CommentDTO> comment2;

    public CommentDTO(Comment comment){
        this.id = comment.getId();
        this.parentId = comment.getParentId();
        this.type = comment.getType();
        this.gmtCreate = comment.getGmtCreate();
        this.gmtModified = comment.getGmtModified();
        this.likeCount = comment.getLikeCount();
        this.content = comment.getContent();
        this.commentCount = comment.getCommentCount();
        this.commentator = comment.getCommentator();
        this.commentator2 = comment.getCommentator2();
    }

    public CommentDTO(Comment comment, MemberDTO memberDTO) {
        this.id = comment.getId();
        this.parentId = comment.getParentId();
        this.type = comment.getType();
        this.gmtCreate = comment.getGmtCreate();
        this.gmtModified = comment.getGmtModified();
        this.likeCount = comment.getLikeCount();
        this.content = comment.getContent();
        this.commentCount = comment.getCommentCount();
        this.commentator = comment.getCommentator();
        this.commentator2 = comment.getCommentator2();
        this.commentatorNickName = memberDTO.getNickName();
        this.commentatorAvatar = memberDTO.getAvatar();
    }

    public CommentDTO(Comment comment, MemberDTO memberDTO,MemberDTO memberDTO2) {
        this.id = comment.getId();
        this.parentId = comment.getParentId();
        this.type = comment.getType();
        this.gmtCreate = comment.getGmtCreate();
        this.gmtModified = comment.getGmtModified();
        this.likeCount = comment.getLikeCount();
        this.content = comment.getContent();
        this.commentCount = comment.getCommentCount();
        this.commentator = comment.getCommentator();
        this.commentator2 = comment.getCommentator2();
        this.commentatorNickName = memberDTO.getNickName();
        this.commentatorAvatar = memberDTO.getAvatar();
        this.commentator2NickName = memberDTO2.getNickName();
        this.commentator2Avatar = memberDTO2.getAvatar();
    }
}
