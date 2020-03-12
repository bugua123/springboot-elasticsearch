package com.wzw.springbootelasticsearch.IndexLifecycleManagermentAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indexlifecycle.RemoveIndexLifecyclePolicyRequest;
import org.elasticsearch.client.indexlifecycle.RemoveIndexLifecyclePolicyResponse;
import org.elasticsearch.client.indexlifecycle.RetryLifecyclePolicyRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
public class Elastic_IndexLifecycle_RemoveLifecyclePolicy {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    //同步执行
    @Test
    public void removeLifecyclePolicy() throws IOException {

        List<String> indices=new ArrayList<>();
        indices.add("my_index");
        RemoveIndexLifecyclePolicyRequest request=new RemoveIndexLifecyclePolicyRequest(indices);

        RemoveIndexLifecyclePolicyResponse removeIndexLifecyclePolicyResponse = restHighLevelClient.indexLifecycle().removeIndexLifecyclePolicy(request, RequestOptions.DEFAULT);
        List<String> failedIndexes = removeIndexLifecyclePolicyResponse.getFailedIndexes();
        boolean b = removeIndexLifecyclePolicyResponse.hasFailures();


    }

    //异步执行
    @Test
@Async
    public void removeLifecyclePolicy_Async() throws IOException {



    }




}
