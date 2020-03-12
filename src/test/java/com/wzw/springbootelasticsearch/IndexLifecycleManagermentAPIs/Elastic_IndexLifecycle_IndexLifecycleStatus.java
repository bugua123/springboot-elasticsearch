package com.wzw.springbootelasticsearch.IndexLifecycleManagermentAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.AcknowledgedResponse;
import org.elasticsearch.client.indexlifecycle.LifecycleManagementStatusRequest;
import org.elasticsearch.client.indexlifecycle.LifecycleManagementStatusResponse;
import org.elasticsearch.client.indexlifecycle.OperationMode;
import org.elasticsearch.client.indexlifecycle.StopILMRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;

@SpringBootTest
@Slf4j
public class Elastic_IndexLifecycle_IndexLifecycleStatus {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    //同步执行
    @Test
    public void indexLifecycleStatus() throws IOException {

        LifecycleManagementStatusRequest request=new LifecycleManagementStatusRequest();
        LifecycleManagementStatusResponse response =
                restHighLevelClient.indexLifecycle()
                        .lifecycleManagementStatus(request, RequestOptions.DEFAULT);

        OperationMode operationMode = response.getOperationMode();
    }

    //异步执行
    @Test
@Async
    public void indexLifecycleStatus_Async() throws IOException {



    }




}
