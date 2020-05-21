package com.codelife.cloud.tag.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codelife.cloud.dto.TagTypeDTO;
import com.codelife.cloud.entities.TagType;
import com.codelife.cloud.service.TagService;
import com.codelife.cloud.service.TagTypeService;
import com.codelife.cloud.tag.mapper.TagMapper;
import com.codelife.cloud.tag.mapper.TagTypeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TagTypeServiceImpl implements TagTypeService {
    @Resource
    private TagTypeMapper tagTypeMapper;
    @Resource
    private TagService tagService;

    @Override
    public List list() {
        return tagTypeMapper.selectList(null);
    }

    @Override
    public IPage list(Long currentPage, Long size ){
        Page page = new Page(currentPage,size,tagTypeMapper.selectCount(null));
        IPage tagTypeIPage = tagTypeMapper.selectPage(page, null);
        List<TagType> records = tagTypeIPage.getRecords();
        List<TagTypeDTO> newRecords = new ArrayList<>();
        for (TagType record : records) {
            TagTypeDTO tagTypeDTO = new TagTypeDTO(record);
            tagTypeDTO.setTagCount(tagService.countByTagTypeId(record.getId()));
            newRecords.add(tagTypeDTO);
        }
        tagTypeIPage.setRecords(newRecords);
        return tagTypeIPage;
    }

    @Override
    public int create(TagType tagType) {
        return tagTypeMapper.insert(tagType);
    }

    @Override
    public int deleteById(Long id) {
        return tagTypeMapper.deleteById(id);
    }


    @Override
    public int updateById(TagType tagType) {
        return tagTypeMapper.updateById(tagType);
    }

    @Override
    public TagType getById(Long id) {
        return tagTypeMapper.selectById(id);
    }
}
