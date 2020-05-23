package com.codelife.cloud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchQuestionDTO {
    private Long id;
    private String title;

    public SearchQuestionDTO(QuestionDTO questionDTO) {
        this.id = questionDTO.getId();
        this.title = questionDTO.getTitle();
    }
}
