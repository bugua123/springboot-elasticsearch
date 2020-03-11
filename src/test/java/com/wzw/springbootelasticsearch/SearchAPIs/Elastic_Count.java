package com.wzw.springbootelasticsearch.SearchAPIs;


import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.Explanation;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.explain.ExplainRequest;
import org.elasticsearch.action.explain.ExplainResponse;
import org.elasticsearch.action.search.ShardSearchFailure;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.index.get.GetResult;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Map;

@SpringBootTest
@Slf4j
public class Elastic_Count {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void count() throws IOException {
        CountRequest request=new CountRequest();
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        request.source(searchSourceBuilder);
//
//        CountRequest countRequest=new CountRequest("blog")
//                .routing("routing")
//                .indicesOptions(IndicesOptions.lenientExpandOpen())
//                .preference("_local");
//
//
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        sourceBuilder.query(QueryBuilders.termQuery("user", "kimchy"));
//
//        CountRequest countRequest2 = new CountRequest();
//        countRequest2.indices("blog", "author");
//        countRequest2.source(sourceBuilder);

        CountResponse countResponse = restHighLevelClient.count(request, RequestOptions.DEFAULT);

        //输出
        long count=countResponse.getCount();
        RestStatus status=countResponse.status();

        Boolean terminatedEarly = countResponse.isTerminatedEarly();

        int totalShards=countResponse.getTotalShards();
        int skippedShards=countResponse.getSkippedShards();
        int successfulShards=countResponse.getSuccessfulShards();
        int failedShards = countResponse.getFailedShards();
        for (ShardSearchFailure failure:countResponse.getShardFailures()) {
            failure.toString();
        }


    }

    //异步执行
    @Test
    public void count_Async() throws IOException {

        CountRequest request=new CountRequest();
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        request.source(searchSourceBuilder);

        ActionListener<CountResponse> listener=new ActionListener<CountResponse>() {
            @Override
            public void onResponse(CountResponse countResponse) {
                //输出
                long count=countResponse.getCount();
                RestStatus status=countResponse.status();
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println("失败");
            }
        };
        restHighLevelClient.countAsync(request, RequestOptions.DEFAULT, listener);

    }




}
