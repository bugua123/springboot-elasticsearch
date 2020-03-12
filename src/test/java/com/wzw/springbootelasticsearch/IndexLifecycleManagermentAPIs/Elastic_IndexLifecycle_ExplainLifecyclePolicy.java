package com.wzw.springbootelasticsearch.IndexLifecycleManagermentAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indexlifecycle.*;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;
import java.util.Map;

@SpringBootTest
@Slf4j
public class Elastic_IndexLifecycle_ExplainLifecyclePolicy {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    //同步执行
    @Test
    public void explainLifecyclePolicy() throws IOException {
        ExplainLifecycleRequest request=new ExplainLifecycleRequest("my_index-1","other_index");


        ExplainLifecycleResponse response = restHighLevelClient.indexLifecycle().explainLifecycle(request, RequestOptions.DEFAULT);

        Map<String, IndexLifecycleExplainResponse> indexResponses = response.getIndexResponses();
        IndexLifecycleExplainResponse indexLifecycleExplainResponse = indexResponses.get("my_index-1");
        String policyName = indexLifecycleExplainResponse.getPolicyName();
        boolean b = indexLifecycleExplainResponse.managedByILM();
        String phase = indexLifecycleExplainResponse.getPhase();
        long phaseTime = indexLifecycleExplainResponse.getPhaseTime();
        long actionTime = indexLifecycleExplainResponse.getActionTime();
        String action = indexLifecycleExplainResponse.getAction();
        String step = indexLifecycleExplainResponse.getStep();
        long stepTime = indexLifecycleExplainResponse.getStepTime();
        String failedStep = indexLifecycleExplainResponse.getFailedStep();


    }

    //异步执行
    @Test
@Async
    public void explainLifecyclePolicy_Async() throws IOException {



    }




}
