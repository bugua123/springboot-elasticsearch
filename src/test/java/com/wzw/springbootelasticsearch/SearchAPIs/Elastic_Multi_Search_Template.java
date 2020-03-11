package com.wzw.springbootelasticsearch.SearchAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.MultiSearchTemplateRequest;
import org.elasticsearch.script.mustache.MultiSearchTemplateResponse;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@Slf4j
public class Elastic_Multi_Search_Template {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    @Test
    public void multiSearchTemplate1() throws IOException {
        String [] searchTerms = {"elasticsearch", "logstash", "kibana"};
        MultiSearchTemplateRequest multiSearchTemplateRequest= new MultiSearchTemplateRequest();
        for (String searchTerm:searchTerms) {
            SearchTemplateRequest request=new SearchTemplateRequest();
            request.setRequest(new SearchRequest("goods"));

            request.setScriptType(ScriptType.INLINE);
            request.setScript(
                    "{" +
                            "  \"query\": { \"match\" : { \"{{field}}\" : \"{{value}}\" } }," +
                            "  \"size\" : \"{{size}}\"" +
                            "}"
            );

            Map<String,Object> scriptParams=new HashMap<>();
            scriptParams.put("field","title" );
            scriptParams.put("value",searchTerm );
            scriptParams.put("size",5 );
            request.setScriptParams(scriptParams);
            multiSearchTemplateRequest.add(request);
        }

        MultiSearchTemplateResponse multiSearchTemplateResponse = restHighLevelClient.msearchTemplate(multiSearchTemplateRequest, RequestOptions.DEFAULT);

        for (MultiSearchTemplateResponse.Item item : multiSearchTemplateResponse.getResponses()) {
            if (item.isFailure()) {
                String error = item.getFailureMessage();
            } else {
                SearchTemplateResponse searchTemplateResponse = item.getResponse();
                SearchResponse searchResponse = searchTemplateResponse.getResponse();
                searchResponse.getHits();
            }
        }
    }



}


