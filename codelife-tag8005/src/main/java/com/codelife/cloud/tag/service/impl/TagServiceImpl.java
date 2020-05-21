package com.codelife.cloud.tag.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codelife.cloud.dto.TagDTO;
import com.codelife.cloud.entities.CommonResult;
import com.codelife.cloud.entities.Tag;
import com.codelife.cloud.service.TagService;
import com.codelife.cloud.tag.mapper.TagMapper;
import com.codelife.cloud.tag.service.QuestionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Resource
    private TagMapper tagMapper;

    @Resource
    private QuestionService questionService;

    @Override
    public List list(Long id) {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tag_type", id);
        return tagMapper.selectList(queryWrapper);
    }

    @Override
    public IPage list(Long tagType, Long current, Long size) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("tag_type",tagType);
        Page page = new Page(current, size, tagMapper.selectCount(queryWrapper));
        IPage tagIPage = tagMapper.selectPage(page, queryWrapper);
        List<Tag> records = tagIPage.getRecords();
        List<TagDTO> newRecords = new ArrayList<>();
        for (Tag record : records) {
            TagDTO tagDTO = new TagDTO(record);
            CommonResult result = questionService.countByTag(tagDTO.getTagName());
            Integer count = Integer.valueOf(String.valueOf(result.getData()));
            tagDTO.setQuestionCount(count);
            newRecords.add(tagDTO);
        }
        tagIPage.setRecords(newRecords);
        return tagIPage;
    }

    @Override
    public Integer countByTagTypeId(Long id) {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tag_type", id);
        tagMapper.selectCount(queryWrapper);
        return tagMapper.selectCount(queryWrapper);
    }

    @Override
    public int deleteById(Long id) {
        return tagMapper.deleteById(id);
    }

    @Override
    public int create(Tag tag) {
        return tagMapper.insert(tag);
    }

    @Override
    public int updateById(Tag tag) {
        return tagMapper.updateById(tag);
    }
}
