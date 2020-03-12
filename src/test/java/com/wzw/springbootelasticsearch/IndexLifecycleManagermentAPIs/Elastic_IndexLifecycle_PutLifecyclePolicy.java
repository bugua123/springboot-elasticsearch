package com.wzw.springbootelasticsearch.IndexLifecycleManagermentAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.cluster.settings.ClusterGetSettingsRequest;
import org.elasticsearch.action.admin.cluster.settings.ClusterGetSettingsResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.AcknowledgedResponse;
import org.elasticsearch.client.indexlifecycle.*;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@Slf4j
public class Elastic_IndexLifecycle_PutLifecyclePolicy {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void putLifecyclePolicy() throws IOException {
        Map<String, Phase> phases=new HashMap<>();
        Map<String, LifecycleAction> hotActions=new HashMap<>();
        hotActions.put(RolloverAction.NAME,new RolloverAction(new ByteSizeValue(50, ByteSizeUnit.GB) ,null,null ) );
        phases.put("hot",new Phase("hot",TimeValue.ZERO ,hotActions ) );

        Map<String,LifecycleAction> deleteActions= Collections.singletonMap(DeleteAction.NAME,new DeleteAction() );
        phases.put("delete",new Phase("delete",new TimeValue(90, TimeUnit.DAYS) ,deleteActions ) );

        LifecyclePolicy policy=new LifecyclePolicy("my_policy",phases );
        PutLifecyclePolicyRequest request=new PutLifecyclePolicyRequest(policy);

        AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indexLifecycle().putLifecyclePolicy(request, RequestOptions.DEFAULT);
        boolean acknowledged = acknowledgedResponse.isAcknowledged();
        System.out.println(acknowledged);

    }

    //异步执行
    @Test
@Async
    public void putLifecyclePolicy_Async() throws IOException {



    }




}
