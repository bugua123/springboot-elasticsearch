package com.wzw.springbootelasticsearch.IndexLifecycleManagermentAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.AcknowledgedResponse;
import org.elasticsearch.client.indexlifecycle.StartILMRequest;
import org.elasticsearch.client.indexlifecycle.StopILMRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;

@SpringBootTest
@Slf4j
public class Elastic_IndexLifecycle_stopIndexLifecyclePolicy {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    //同步执行
    @Test
    public void stopIndexLifecyclePolicy() throws IOException {

        StopILMRequest request=new StopILMRequest();
        AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indexLifecycle().stopILM(request, RequestOptions.DEFAULT);
        boolean acknowledged = acknowledgedResponse.isAcknowledged();

    }

    //异步执行
    @Test
@Async
    public void stopIndexLifecyclePolicy_Async() throws IOException {



    }




}
