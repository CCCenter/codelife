package com.codelife.cloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.codelife.cloud.entities.Relation;

public interface RelationService {
    int create(Relation entity);
    Relation findByEntity(Relation entity);
    IPage list(Relation entity, Long current, Long size);
}
