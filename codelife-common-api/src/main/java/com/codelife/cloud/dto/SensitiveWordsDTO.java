package com.codelife.cloud.dto;

import com.codelife.cloud.entities.SensitiveWords;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensitiveWordsDTO implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String sensitiveWord;
    private Long memberId;
    private String memberName;

    public SensitiveWordsDTO(SensitiveWords sensitiveWords) {
        this.id = sensitiveWords.getId();
        this.sensitiveWord = sensitiveWords.getSensitiveWord();
        this.memberId = sensitiveWords.getMemberId();
    }
}
