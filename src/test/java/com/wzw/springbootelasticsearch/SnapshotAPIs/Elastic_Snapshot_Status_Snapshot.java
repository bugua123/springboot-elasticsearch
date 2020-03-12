package com.wzw.springbootelasticsearch.SnapshotAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.cluster.snapshots.get.GetSnapshotsRequest;
import org.elasticsearch.action.admin.cluster.snapshots.get.GetSnapshotsResponse;
import org.elasticsearch.action.admin.cluster.snapshots.status.SnapshotStats;
import org.elasticsearch.action.admin.cluster.snapshots.status.SnapshotStatus;
import org.elasticsearch.action.admin.cluster.snapshots.status.SnapshotsStatusRequest;
import org.elasticsearch.action.admin.cluster.snapshots.status.SnapshotsStatusResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.SnapshotsInProgress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.snapshots.SnapshotId;
import org.elasticsearch.snapshots.SnapshotInfo;
import org.elasticsearch.snapshots.SnapshotShardFailure;
import org.elasticsearch.snapshots.SnapshotState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
@Slf4j
public class Elastic_Snapshot_Status_Snapshot {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void statusSnapshot() throws IOException {
        SnapshotsStatusRequest request=new SnapshotsStatusRequest();
        String repositoryName="111";
        request.repository(repositoryName);


        String snapshotName="222";
        String [] snapshots = new String[] {snapshotName};
        request.snapshots(snapshots);

        request.ignoreUnavailable(true);
        request.masterNodeTimeout(TimeValue.timeValueMinutes(1));
        request.masterNodeTimeout("1m");

        SnapshotsStatusResponse response = restHighLevelClient.snapshot().status(request, RequestOptions.DEFAULT);

        List<SnapshotStatus> snapshotStatusesResponse = response.getSnapshots();
        SnapshotStatus snapshotStatus = snapshotStatusesResponse.get(0);
        SnapshotsInProgress.State snapshotState = snapshotStatus.getState();
        String indexName="index_name";
        SnapshotStats shardStats = snapshotStatus.getIndices().get(indexName).getShards().get(0).getStats();


    }

    //异步执行
    @Test
    public void statusSnapshot_Async() throws IOException, InterruptedException {

        SnapshotsStatusRequest request=new SnapshotsStatusRequest();
        String repositoryName="111";
        request.repository(repositoryName);


        String snapshotName="222";
        String [] snapshots = new String[] {snapshotName};
        request.snapshots(snapshots);

        request.ignoreUnavailable(true);
        request.masterNodeTimeout(TimeValue.timeValueMinutes(1));
        request.masterNodeTimeout("1m");

        ActionListener<SnapshotsStatusResponse> listener=new ActionListener<SnapshotsStatusResponse>() {
            @Override
            public void onResponse(SnapshotsStatusResponse snapshotsStatusResponse) {


            }

            @Override
            public void onFailure(Exception e) {

            }
        };
        restHighLevelClient.snapshot().statusAsync(request, RequestOptions.DEFAULT,listener);
    }




}
