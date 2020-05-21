package com.codelife.cloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensitiveWords {
    private Long id;
    private String sensitiveWord;
    private Long memberId;
}
