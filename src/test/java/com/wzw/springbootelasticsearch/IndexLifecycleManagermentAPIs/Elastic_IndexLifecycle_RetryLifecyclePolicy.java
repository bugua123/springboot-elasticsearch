package com.wzw.springbootelasticsearch.IndexLifecycleManagermentAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.AcknowledgedResponse;
import org.elasticsearch.client.indexlifecycle.RetryLifecyclePolicyRequest;
import org.elasticsearch.client.indexlifecycle.StopILMRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;

@SpringBootTest
@Slf4j
public class Elastic_IndexLifecycle_RetryLifecyclePolicy {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    //同步执行
    @Test
    public void retryLifecyclePolicy() throws IOException {

        RetryLifecyclePolicyRequest request=new RetryLifecyclePolicyRequest("index");
        AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indexLifecycle().retryLifecyclePolicy(request, RequestOptions.DEFAULT);


    }

    //异步执行
    @Test
@Async
    public void retryLifecyclePolicy_Async() throws IOException {



    }




}
