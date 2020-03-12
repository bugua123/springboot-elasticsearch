package com.wzw.springbootelasticsearch.SnapshotAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.cluster.snapshots.create.CreateSnapshotRequest;
import org.elasticsearch.action.admin.cluster.snapshots.create.CreateSnapshotResponse;
import org.elasticsearch.action.admin.cluster.snapshots.get.GetSnapshotsRequest;
import org.elasticsearch.action.admin.cluster.snapshots.get.GetSnapshotsResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
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
public class Elastic_Snapshot_Get_Snapshot {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void getSnapshot() throws IOException {
        GetSnapshotsRequest request=new GetSnapshotsRequest();
        String repositoryName="111";
        request.repository(repositoryName);

        String snapshotName="222";
        String[] snapshots = { snapshotName };
        request.snapshots(snapshots);
        request.masterNodeTimeout(TimeValue.timeValueMinutes(1));
        request.verbose(true);
        request.ignoreUnavailable(false);

        GetSnapshotsResponse response = restHighLevelClient.snapshot().get(request, RequestOptions.DEFAULT);
        List<SnapshotInfo> snapshotsInfos = response.getSnapshots();
        SnapshotInfo snapshotInfo = snapshotsInfos.get(0);
        RestStatus restStatus = snapshotInfo.status();
        SnapshotId snapshotId = snapshotInfo.snapshotId();
        SnapshotState snapshotState = snapshotInfo.state();
        List<SnapshotShardFailure> snapshotShardFailures = snapshotInfo.shardFailures();
        long startTime = snapshotInfo.startTime();
        long endTime = snapshotInfo.endTime();
    }

    //异步执行
    @Test
    public void getSnapshot_Async() throws IOException, InterruptedException {

        GetSnapshotsRequest request=new GetSnapshotsRequest();
        String repositoryName="111";
        request.repository(repositoryName);

        String snapshotName="222";
        String[] snapshots = { snapshotName };
        request.snapshots(snapshots);
        request.masterNodeTimeout(TimeValue.timeValueMinutes(1));
        request.verbose(true);
        request.ignoreUnavailable(false);

        ActionListener<GetSnapshotsResponse> listener=new ActionListener<GetSnapshotsResponse>() {
            @Override
            public void onResponse(GetSnapshotsResponse getSnapshotsResponse) {

            }

            @Override
            public void onFailure(Exception e) {

            }
        };
        restHighLevelClient.snapshot().getAsync(request, RequestOptions.DEFAULT,listener);
    }




}
