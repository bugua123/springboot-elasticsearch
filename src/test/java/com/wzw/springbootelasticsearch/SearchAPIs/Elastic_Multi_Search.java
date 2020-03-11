package com.wzw.springbootelasticsearch.SearchAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
@Slf4j
public class Elastic_Multi_Search {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    @Test
    public void multiSearchScroll() throws IOException {

        MultiSearchRequest request=new MultiSearchRequest();

        SearchRequest firstSearchRequest=new SearchRequest();//不指定索引
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("user", "kimchy"));
        firstSearchRequest.source(searchSourceBuilder);
        request.add(firstSearchRequest);

    //    SearchRequest secondSearchRequest=new SearchRequest("index");//指定索引
            SearchRequest secondSearchRequest=new SearchRequest();

        searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("user","lnca" ));
        secondSearchRequest.source(searchSourceBuilder);
        request.add(secondSearchRequest);
        MultiSearchResponse multiSearchResponse = restHighLevelClient.msearch(request, RequestOptions.DEFAULT);


        //返回信息
//         //可以循环取出数据
//        MultiSearchResponse.Item[] responses = multiSearchResponse.getResponses();

        //取出第一个查询返回信息
        MultiSearchResponse.Item firstResponse=multiSearchResponse.getResponses()[0];
        SearchResponse response1 = firstResponse.getResponse();
        System.out.println(response1);
        //取出第二个查询返回信息
        MultiSearchResponse.Item secondResponse = multiSearchResponse.getResponses()[1];
        SearchResponse response2 = secondResponse.getResponse();
        System.out.println(response2);

    }




}
