package com.codelife.cloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.codelife.cloud.entities.SensitiveWords;

public interface SensitiveService {
    int create(SensitiveWords sensitiveWords);
    int deleteById(Long id);
    SensitiveWords getById(Long id);
    IPage list(Long current,Long size);
    void initSensitive();
}
