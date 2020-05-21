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
    private Long total;
    private Object records;

    public PageDTO(Long id, Integer currentPage, Integer size) {
        this.id = id;
        this.currentPage = currentPage;
        this.size = size;
    }

    public PageDTO(Integer currentPage, Integer size) {
        this.currentPage = currentPage;
        this.size = size;
    }

    public PageDTO(Integer currentPage, Integer size, Long total, Object records) {
        this.currentPage = currentPage;
        this.size = size;
        this.total = total;
        this.records = records;
    }
}
