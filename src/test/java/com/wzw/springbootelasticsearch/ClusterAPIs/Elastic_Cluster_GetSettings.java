package com.wzw.springbootelasticsearch.ClusterAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.cluster.settings.ClusterGetSettingsRequest;
import org.elasticsearch.action.admin.cluster.settings.ClusterGetSettingsResponse;
import org.elasticsearch.action.admin.cluster.settings.ClusterUpdateSettingsRequest;
import org.elasticsearch.action.admin.cluster.settings.ClusterUpdateSettingsResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.routing.allocation.decider.EnableAllocationDecider;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.indices.recovery.RecoverySettings;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;

@SpringBootTest
@Slf4j
public class Elastic_Cluster_GetSettings {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void clusterGetSettings() throws IOException {
        ClusterGetSettingsRequest request=new ClusterGetSettingsRequest();

        request.includeDefaults(true);
        request.local(true);
        request.masterNodeTimeout(TimeValue.timeValueMillis(1));
        request.masterNodeTimeout("1m");

        ClusterGetSettingsResponse response = restHighLevelClient.cluster().getSettings(request, RequestOptions.DEFAULT);

        //返回信息
        Settings persistentSettings = response.getPersistentSettings();
        Settings transientSettings = response.getTransientSettings();
        Settings defaultSettings = response.getDefaultSettings();
        String setting = response.getSetting("cluster.routing.allocation.enable");
        System.out.println(defaultSettings);


    }

    //异步执行
    @Test
@Async
    public void clusterGetSettings_Async() throws IOException {

        ClusterGetSettingsRequest request=new ClusterGetSettingsRequest();

        request.includeDefaults(true);
        request.local(true);
        request.masterNodeTimeout(TimeValue.timeValueMillis(1));
        request.masterNodeTimeout("1m");


        ActionListener<ClusterGetSettingsResponse> listener=new ActionListener<ClusterGetSettingsResponse>() {
            @Override
            public void onResponse(ClusterGetSettingsResponse clusterGetSettingsResponse) {

                //返回信息

            }

            @Override
            public void onFailure(Exception e) {

            }
        };
        restHighLevelClient.cluster().getSettingsAsync(request,RequestOptions.DEFAULT , listener);

    }




}
