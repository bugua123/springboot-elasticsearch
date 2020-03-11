package com.wzw.springbootelasticsearch.ClusterAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.cluster.settings.ClusterUpdateSettingsRequest;
import org.elasticsearch.action.admin.cluster.settings.ClusterUpdateSettingsResponse;
import org.elasticsearch.action.search.ShardSearchFailure;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.cluster.routing.allocation.decider.EnableAllocationDecider;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.indices.recovery.RecoverySettings;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@Slf4j
public class Elastic_Cluster_UpdateSettings {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void clusterUpdatesSettings() throws IOException {
        ClusterUpdateSettingsRequest request=new ClusterUpdateSettingsRequest();

        String transientSettingKey= RecoverySettings.INDICES_RECOVERY_MAX_BYTES_PER_SEC_SETTING.getKey();
        int transientSettingValue=10;
        Settings transientSettings=Settings.builder()
                .put(transientSettingKey,transientSettingValue, ByteSizeUnit.BYTES )
                .build();

        String persistentSettingKey =
                EnableAllocationDecider.CLUSTER_ROUTING_ALLOCATION_ENABLE_SETTING.getKey();
        String persistentSettingValue =
                EnableAllocationDecider.Allocation.NONE.name();
        Settings persistentSettings =
                Settings.builder()
                        .put(persistentSettingKey, persistentSettingValue)
                        .build();


        request.transientSettings(transientSettings);
        request.persistentSettings(persistentSettings);

//其他赋值方式
//        Settings.Builder transientSettingsBuilder =
//                Settings.builder()
//                        .put(transientSettingKey, transientSettingValue, ByteSizeUnit.BYTES);
//        request.transientSettings(transientSettingsBuilder);
//
//        request.transientSettings(
//                "{\"indices.recovery.max_bytes_per_sec\": \"10b\"}"
//                , XContentType.JSON);
//
//
//        Map<String, Object> map = new HashMap<>();
//        map.put(transientSettingKey
//                , transientSettingValue + ByteSizeUnit.BYTES.getSuffix());
//        request.transientSettings(map);

        ClusterUpdateSettingsResponse clusterUpdateSettingsResponse =
                restHighLevelClient.cluster().putSettings(request, RequestOptions.DEFAULT);

        //输出
        boolean acknowledged = clusterUpdateSettingsResponse.isAcknowledged();
        Settings transientSettingsResponse = clusterUpdateSettingsResponse.getTransientSettings();
        Settings persistentSettingsResponse = clusterUpdateSettingsResponse.getPersistentSettings();

        System.out.println(transientSettingsResponse);


    }

    //异步执行
    @Test
    public void clusterUpdatesSettings_Async() throws IOException {
        ClusterUpdateSettingsRequest request=new ClusterUpdateSettingsRequest();

        String transientSettingKey= RecoverySettings.INDICES_RECOVERY_MAX_BYTES_PER_SEC_SETTING.getKey();
        int transientSettingValue=10;
        Settings transientSettings=Settings.builder()
                .put(transientSettingKey,transientSettingValue, ByteSizeUnit.BYTES )
                .build();

        String persistentSettingKey =
                EnableAllocationDecider.CLUSTER_ROUTING_ALLOCATION_ENABLE_SETTING.getKey();
        String persistentSettingValue =
                EnableAllocationDecider.Allocation.NONE.name();

        Settings persistentSettings =
                Settings.builder()
                        .put(persistentSettingKey, persistentSettingValue)
                        .build();


        request.transientSettings(transientSettings);
        request.persistentSettings(persistentSettings);


        ActionListener<ClusterUpdateSettingsResponse> listener=new ActionListener<ClusterUpdateSettingsResponse>() {
            @Override
            public void onResponse(ClusterUpdateSettingsResponse clusterUpdateSettingsResponse) {
                boolean acknowledged = clusterUpdateSettingsResponse.isAcknowledged();
                Settings transientSettingsResponse = clusterUpdateSettingsResponse.getTransientSettings();
                Settings persistentSettingsResponse = clusterUpdateSettingsResponse.getPersistentSettings();
            }

            @Override
            public void onFailure(Exception e) {

            }
        };
        restHighLevelClient.cluster().putSettingsAsync(request, RequestOptions.DEFAULT, listener);




    }




}
