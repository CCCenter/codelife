package com.codelife.cloud.dto;

import com.codelife.cloud.entities.TagType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagTypeDTO implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String tagType;
    private Integer tagCount;

    public TagTypeDTO(TagType tagType) {
        this.id = tagType.getId();
        this.tagType = tagType.getTagType();
    }
}
