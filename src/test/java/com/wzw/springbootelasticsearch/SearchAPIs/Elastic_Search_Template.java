package com.wzw.springbootelasticsearch.SearchAPIs;


import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.ShardSearchFailure;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.SuggestionBuilder;
import org.elasticsearch.search.suggest.term.TermSuggestion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@Slf4j
public class Elastic_Search_Template {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    @Test
    public void searchtemplate1() throws IOException {
        SearchTemplateRequest request=new SearchTemplateRequest();
        request.setRequest( new SearchRequest("goods"));

        request.setScriptType(ScriptType.INLINE);

        request.setScript("{" +
                "  \"query\": { \"match\" : { \"{{field}}\" : \"{{value}}\" } }," +
                "  \"size\" : \"{{size}}\"" +
                "}");


        Map<String, Object> scriptParams = new HashMap<>();
        scriptParams.put("field", "title");
        scriptParams.put("value","elasticsearch" );
        scriptParams.put("size","5" );
        request.setScriptParams(scriptParams);

        SearchTemplateResponse response = restHighLevelClient.searchTemplate(request, RequestOptions.DEFAULT);
        SearchResponse searchResponse = response.getResponse();
        System.out.println(searchResponse);
    }

    @Test
    public void searchtemplate2() throws IOException {


        SearchTemplateRequest request = new SearchTemplateRequest();
        request.setRequest(new SearchRequest("goods"));

        request.setScriptType(ScriptType.STORED);
        request.setScript("title_search");//此脚本需要在elasticsearch 中建立

        Map<String, Object> params = new HashMap<>();
        params.put("field", "title");
        params.put("value", "elasticsearch");
        params.put("size", 5);
        request.setScriptParams(params);
        request.setSimulate(true);
        request.setExplain(true);
        request.setProfile(true);

        SearchTemplateResponse response = restHighLevelClient.searchTemplate(request, RequestOptions.DEFAULT);
        SearchResponse searchResponse = response.getResponse();
    }

}


