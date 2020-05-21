package com.codelife.cloud.dto;

import com.codelife.cloud.entities.SensitiveWords;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensitiveWordsDTO implements Serializable {
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
