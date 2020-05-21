package com.codelife.cloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.codelife.cloud.entities.Tag;

import java.util.List;

public interface TagService {
    List list(Long id);
    IPage list(Long id,Long current, Long size);
    int deleteById(Long id);
    Integer countByTagTypeId(Long id);
    int create(Tag tag);
    int updateById(Tag tag);
}
