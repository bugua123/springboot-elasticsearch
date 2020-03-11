package com.wzw.springbootelasticsearch.ClusterAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.cluster.settings.ClusterGetSettingsRequest;
import org.elasticsearch.action.admin.cluster.settings.ClusterGetSettingsResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.cluster.health.ClusterIndexHealth;
import org.elasticsearch.cluster.health.ClusterShardHealth;
import org.elasticsearch.common.Priority;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.rest.RestStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Map;

@SpringBootTest
@Slf4j
public class Elastic_Cluster_Health {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void clusterHealth() throws IOException {
        ClusterHealthRequest request=new ClusterHealthRequest();

//        ClusterHealthRequest request1=new ClusterHealthRequest("index1","index2");
//
//        ClusterHealthRequest request2=new ClusterHealthRequest();
//        request2.indices("index1","index2");

        request.timeout(TimeValue.timeValueSeconds(50));
        request.timeout("50s");
        request.masterNodeTimeout(TimeValue.timeValueSeconds(20));
        request.masterNodeTimeout("20s");

       request.waitForStatus(ClusterHealthStatus.YELLOW);
       request.waitForYellowStatus();

       request.waitForEvents(Priority.NORMAL);
       request.level(ClusterHealthRequest.Level.SHARDS);

       request.waitForNoRelocatingShards(true);
       request.waitForNoInitializingShards(true);

       request.waitForNodes("2");
       request.waitForNodes(">=2");
       request.waitForNodes("le(2)");

       request.local(true);

        ClusterHealthResponse response = restHighLevelClient.cluster().health(request, RequestOptions.DEFAULT);

        //返回信息

        String clusterName=response.getClusterName();
        ClusterHealthStatus status=response.getStatus();

        boolean timedOut=response.isTimedOut();
        RestStatus restStatus=response.status();

        int numberOfNodes=response.getNumberOfNodes();
        int numberOfDataNodes=response.getNumberOfDataNodes();

        int activeShards=response.getActiveShards();
        int activePrimarShards=response.getActivePrimaryShards();

        int relocatingShards=response.getRelocatingShards();
        int initializingShards=response.getInitializingShards();
        int unassignedShards=response.getUnassignedShards();
        int delayedUnassignedShards=response.getDelayedUnassignedShards();
        double activeShardsPercent = response.getActiveShardsPercent();

        TimeValue taskMaxWaitingTime = response.getTaskMaxWaitingTime();
        int numberOfPendingTasks = response.getNumberOfPendingTasks();
        int numberOfInFlightFetch = response.getNumberOfInFlightFetch();

        Map<String, ClusterIndexHealth> indices = response.getIndices();

        ClusterIndexHealth index = indices.get("goods");
        ClusterHealthStatus indexStatus = index.getStatus();
        int numberOfShards = index.getNumberOfShards();
        int numberOfReplicas = index.getNumberOfReplicas();
        int activeShards_ = index.getActiveShards();
        int activePrimaryShards = index.getActivePrimaryShards();
        int initializingShards_ = index.getInitializingShards();
        int relocatingShards_ = index.getRelocatingShards();
        int unassignedShards_ = index.getUnassignedShards();


        Map<Integer, ClusterShardHealth> shards = index.getShards();
        ClusterShardHealth clusterShardHealth = shards.get(0);
        int shardId = clusterShardHealth.getShardId();
        ClusterHealthStatus shardStatus = clusterShardHealth.getStatus();
        int active = clusterShardHealth.getActiveShards();
        int initializing = clusterShardHealth.getInitializingShards();
        int unassigned = clusterShardHealth.getUnassignedShards();
        int relocating = clusterShardHealth.getRelocatingShards();
        boolean primaryActive = clusterShardHealth.isPrimaryActive();

        System.out.println("++++++++++++++++同步调用+++++++++++++"+status);
    }

    //异步执行
    @Test
    public void clusterHealth_Async() throws IOException, InterruptedException {

        ClusterHealthRequest request=new ClusterHealthRequest("goods");

//        request.waitForStatus(ClusterHealthStatus.YELLOW);

        ActionListener<ClusterHealthResponse> listener=new ActionListener<ClusterHealthResponse>() {
            @Override
            public void onResponse(ClusterHealthResponse clusterHealthResponse) {
                //返回信息
                String clusterName=clusterHealthResponse.getClusterName();
                ClusterHealthStatus status=clusterHealthResponse.getStatus();
                System.out.println("+++++++++++++++++异步调用++++++++++++"+status);
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println("信息："+e);
            }
        };
        restHighLevelClient.cluster().healthAsync(request,RequestOptions.DEFAULT,listener );

        //异步操作，延迟2000 毫秒后，返回数据 其他操作进行更改
//        Thread.sleep(2000);
//        restHighLevelClient.close();
    }




}
