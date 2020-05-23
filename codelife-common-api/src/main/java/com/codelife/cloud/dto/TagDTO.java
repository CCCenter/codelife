package com.codelife.cloud.dto;

import com.codelife.cloud.entities.Tag;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String tagType;
    private String tagName;
    private Integer questionCount;

    public TagDTO(Tag tag) {
        this.id = tag.getId();
        this.tagType = tag.getTagType();
        this.tagName = tag.getTagName();
    }
}
