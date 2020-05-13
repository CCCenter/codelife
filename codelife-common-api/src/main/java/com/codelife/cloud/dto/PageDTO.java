package com.codelife.cloud.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private Integer currentPage;
    private Integer size;

    public PageDTO(Integer currentPage, Integer size) {
        this.currentPage = currentPage;
        this.size = size;
    }
}
