package com.wzw.springbootelasticsearch.RollupAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.rollup.GetRollupJobRequest;
import org.elasticsearch.client.rollup.GetRollupJobResponse;
import org.elasticsearch.client.rollup.job.config.RollupJobConfig;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.matrix.stats.MatrixStatsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
@Slf4j
public class Elastic_Search_Rollup {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void searchRollup() throws IOException {

        SearchRequest request=new SearchRequest();
        request.source(new SearchSourceBuilder()
        .size(10)
        .aggregation(new MatrixStatsAggregationBuilder("max_temperature")
        .format("temperature")));

        SearchResponse response = restHighLevelClient.rollup().search(request, RequestOptions.DEFAULT);

        Aggregation max_temperature = response.getAggregations().get("max_temperature");

    }

    //异步执行
    @Test
    public void searchRollup_Async() throws IOException, InterruptedException {
      
    }

}
