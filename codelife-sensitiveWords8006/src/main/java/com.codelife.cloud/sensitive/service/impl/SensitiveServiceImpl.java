package com.codelife.cloud.sensitive.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codelife.cloud.dto.SensitiveWordsDTO;
import com.codelife.cloud.entities.CommonResult;
import com.codelife.cloud.entities.Member;
import com.codelife.cloud.entities.SensitiveWords;
import com.codelife.cloud.sensitive.mapper.SensitiveMapper;
import com.codelife.cloud.sensitive.service.MemberService;
import com.codelife.cloud.service.SensitiveService;
import com.codelife.cloud.util.sensitive.SensitiveFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SensitiveServiceImpl implements SensitiveService {
    private SensitiveFilter filter = SensitiveFilter.DEFAULT;
    @Resource
    private SensitiveMapper sensitiveMapper;
    @Resource
    private MemberService memberService;
    @Resource
    private ObjectMapper objectMapper;
    @Override
    public int create(SensitiveWords sensitiveWords) {
        int insert = sensitiveMapper.insert(sensitiveWords);
        return insert;
    }

    @Override
    public int deleteById(Long id) {
        int i = sensitiveMapper.deleteById(id);
        return i;
    }

    @Override
    public SensitiveWords getById(Long id) {
        return sensitiveMapper.selectById(id);
    }

    @Override
    public IPage list(Long current,Long size) {
        Page page = new Page(current,size,sensitiveMapper.selectCount(null));
        IPage iPage = sensitiveMapper.selectPage(page, null);
        List<SensitiveWords> records = iPage.getRecords();
        List<SensitiveWordsDTO> sensitiveWordsDTOS = new ArrayList<>();
        for (SensitiveWords record : records) {
            SensitiveWordsDTO sensitiveWordsDTO = new SensitiveWordsDTO(record);
            CommonResult result = memberService.findById(record.getMemberId());
            Member member = objectMapper.convertValue(result.getData(), Member.class);
            sensitiveWordsDTO.setMemberName(member.getNickName());
            sensitiveWordsDTOS.add(sensitiveWordsDTO);
        }
        iPage.setRecords(sensitiveWordsDTOS);
        return iPage;
    }

    @Override
    public void initSensitive() {
        System.out.println("init");
        List<SensitiveWords> sensitiveWords = sensitiveMapper.selectList(null);
        for (SensitiveWords sensitiveWord : sensitiveWords) {
            filter.put(sensitiveWord.getSensitiveWord());
        }
    }
}
