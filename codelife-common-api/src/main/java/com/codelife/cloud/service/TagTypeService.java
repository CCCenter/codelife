package com.codelife.cloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.codelife.cloud.entities.TagType;

import java.util.List;

public interface TagTypeService {
    List list();
    IPage list(Long currentPage, Long size);
    int create(TagType tagType);
    int deleteById(Long id);
    int updateById(TagType tagType);
    TagType getById(Long id);
}
