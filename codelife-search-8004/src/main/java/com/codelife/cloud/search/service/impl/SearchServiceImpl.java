package com.codelife.cloud.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.codelife.cloud.dto.MemberDTO;
import com.codelife.cloud.dto.PageDTO;
import com.codelife.cloud.dto.QuestionDTO;
import com.codelife.cloud.entities.CommonResult;
import com.codelife.cloud.entities.Member;
import com.codelife.cloud.entities.Question;
import com.codelife.cloud.search.service.MemberService;
import com.codelife.cloud.search.service.QuestionService;
import com.codelife.cloud.service.SearchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private Gson gson;

    @Resource
    private JestClient jestClient;

    @Resource
    private QuestionService questionService;

    @Resource
    private MemberService memberService;

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public String put(Question question) {
        CommonResult result = memberService.findById(question.getMemberId());
        Member member = objectMapper.convertValue(result.getData(), Member.class);
        QuestionDTO questionDTO = new QuestionDTO(new MemberDTO(member), question);
        try {
                Index put = new Index.Builder(gson.toJson(questionDTO)).index("codelife").type("question").id(questionDTO.getId().toString()).build();
                jestClient.execute(put);
                int i = 1;
            } catch (IOException e) {
                e.printStackTrace();
                return "fail";
            }
        return "success";
    }

    @Override
    public String put() {
        CommonResult commonResult = questionService.findAll();
        Object data = commonResult.getData();
        String string = JSON.toJSONString(data);
        List<QuestionDTO> questionDTOS = JSON.parseArray(string, QuestionDTO.class);
        for (QuestionDTO questionDTO : questionDTOS) {
            Index put = new Index.Builder(gson.toJson(questionDTO)).index("codelife").type("question").id(questionDTO.getId().toString()).build();
            try {
                jestClient.execute(put);
                int i = 1;
            } catch (IOException e) {
                e.printStackTrace();
                return "fail";
            }
        }
        return "success";
    }

    @Override
    public PageDTO list(String keyword,Integer currentPage, Integer size) {
        PageDTO pageDTO = null;
        String dsl = getSearchDsl(keyword,currentPage,size);
        Search build = new Search.Builder(dsl).addIndex("codelife").addType("question").build();
        ArrayList<QuestionDTO> questionDTOS = new ArrayList<>();
        SearchResult searchResult = null;
        try {
            searchResult = jestClient.execute(build);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (searchResult != null) {
            List<SearchResult.Hit<QuestionDTO, Void>> hits = searchResult.getHits(QuestionDTO.class);
            Long total = searchResult.getTotal();
            for (SearchResult.Hit<QuestionDTO, Void> hit : hits) {
                QuestionDTO source = hit.source;
                Map<String, List<String>> highlight = hit.highlight;
                if (highlight != null) {
                    String title = highlight.get("title").get(0);
                    source.setTitle(title);
                }
                questionDTOS.add(source);
            }
            pageDTO = new PageDTO(currentPage, size, total, questionDTOS);
        }

        return pageDTO;
    }

    private String getSearchDsl(String keyword,Integer currentPage, Integer size) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        //匹配标题
        if (StringUtils.isNotBlank(keyword)) {
            boolQueryBuilder.must(new MatchQueryBuilder("title", keyword));
        }
        searchSourceBuilder.query(boolQueryBuilder);

        //highlight
        HighlightBuilder highlightBuilder = new HighlightBuilder();

        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.field("title");
        highlightBuilder.postTags("</span>");

        searchSourceBuilder.highlighter(highlightBuilder);
        //sort
        searchSourceBuilder.sort("gmtCreate", SortOrder.DESC);

        //from
        searchSourceBuilder.from((currentPage - 1 ) * size + 1);
        //  1 - 5
        //  6 - 10
        // 11 - 15
        //size
        searchSourceBuilder.size(size);
        return searchSourceBuilder.toString();
    }
}
