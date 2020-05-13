package com.codelife.cloud.search;

import com.codelife.cloud.dto.QuestionDTO;
import com.codelife.cloud.entities.CommonResult;
import com.codelife.cloud.search.service.QuestionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class BuildInfo {

    @Resource
    private QuestionService questionService;
    @Resource
    private JestClient jestClient;
    @Resource
    private ObjectMapper objectMapper;

    private void generate() throws IOException {
        CommonResult commonResult = questionService.findAll();
        Object data = commonResult.getData();
        ArrayList<QuestionDTO> arrayList = objectMapper.convertValue(data, ArrayList.class);
        for (QuestionDTO questionDTO : arrayList) {
            Index put = new Index.Builder(questionDTO).index("codelife").type("question").id(questionDTO.getId().toString()).build();
            jestClient.execute(put);
        }
    }

    public static void main(String[] args) throws IOException {
        new BuildInfo().generate();
    }
}
