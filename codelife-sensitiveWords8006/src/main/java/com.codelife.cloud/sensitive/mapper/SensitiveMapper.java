package com.codelife.cloud.sensitive.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codelife.cloud.entities.SensitiveWords;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SensitiveMapper extends BaseMapper<SensitiveWords> {
}
