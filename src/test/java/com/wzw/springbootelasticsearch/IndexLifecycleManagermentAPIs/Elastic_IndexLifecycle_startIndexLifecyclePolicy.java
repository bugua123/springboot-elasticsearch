package com.wzw.springbootelasticsearch.IndexLifecycleManagermentAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.AcknowledgedResponse;
import org.elasticsearch.client.indexlifecycle.ExplainLifecycleRequest;
import org.elasticsearch.client.indexlifecycle.ExplainLifecycleResponse;
import org.elasticsearch.client.indexlifecycle.IndexLifecycleExplainResponse;
import org.elasticsearch.client.indexlifecycle.StartILMRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;
import java.util.Map;

@SpringBootTest
@Slf4j
public class Elastic_IndexLifecycle_startIndexLifecyclePolicy {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    //同步执行
    @Test
    public void startIndexLifecyclePolicy() throws IOException {

        StartILMRequest request=new StartILMRequest();
        AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indexLifecycle().startILM(request, RequestOptions.DEFAULT);
        boolean acknowledged = acknowledgedResponse.isAcknowledged();


    }

    //异步执行
    @Test
@Async
    public void startIndexLifecyclePolicy_Async() throws IOException {



    }




}
