package com.wzw.springbootelasticsearch.IndexLifecycleManagermentAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.AcknowledgedResponse;
import org.elasticsearch.client.indexlifecycle.*;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;

@SpringBootTest
@Slf4j
public class Elastic_IndexLifecycle_GetLifecyclePolicy {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    //同步执行
    @Test
    public void getLifecyclePolicy() throws IOException {
        GetLifecyclePolicyRequest allrequest=new GetLifecyclePolicyRequest();

        GetLifecyclePolicyRequest request=new GetLifecyclePolicyRequest("my_policy","other_policy");

        GetLifecyclePolicyResponse response = restHighLevelClient.indexLifecycle().getLifecyclePolicy(request, RequestOptions.DEFAULT);

        ImmutableOpenMap<String, LifecyclePolicyMetadata> policies = response.getPolicies();
        LifecyclePolicyMetadata my_policy = policies.get("my_policy");
        String name = my_policy.getName();
        long version = my_policy.getVersion();
        String modifiedDateString = my_policy.getModifiedDateString();
        long modifiedDate = my_policy.getModifiedDate();
        LifecyclePolicy policy = my_policy.getPolicy();

    }

    //异步执行
    @Test
@Async
    public void getLifecyclePolicy_Async() throws IOException {



    }




}
