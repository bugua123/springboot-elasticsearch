package com.wzw.springbootelasticsearch.SnapshotAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.cluster.snapshots.delete.DeleteSnapshotRequest;
import org.elasticsearch.action.admin.cluster.snapshots.status.SnapshotStats;
import org.elasticsearch.action.admin.cluster.snapshots.status.SnapshotStatus;
import org.elasticsearch.action.admin.cluster.snapshots.status.SnapshotsStatusRequest;
import org.elasticsearch.action.admin.cluster.snapshots.status.SnapshotsStatusResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.SnapshotsInProgress;
import org.elasticsearch.common.unit.TimeValue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
@Slf4j
public class Elastic_Snapshot_Delete_Snapshot {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void deleteSnapshot() throws IOException {
        DeleteSnapshotRequest request=new DeleteSnapshotRequest();
        String snapshotName="111";
        request.snapshot(snapshotName);
        request.masterNodeTimeout(TimeValue.timeValueMinutes(1));
        AcknowledgedResponse response = restHighLevelClient.snapshot().delete(request, RequestOptions.DEFAULT);
        boolean acknowledged = response.isAcknowledged();
    }

    //异步执行
    @Test
    public void deleteSnapshot_Async() throws IOException, InterruptedException {

        DeleteSnapshotRequest request=new DeleteSnapshotRequest();
        String snapshotName="111";
        request.snapshot(snapshotName);
        request.masterNodeTimeout(TimeValue.timeValueMinutes(1));
        ActionListener<AcknowledgedResponse> listener=new ActionListener<AcknowledgedResponse>() {
            @Override
            public void onResponse(AcknowledgedResponse acknowledgedResponse) {

            }

            @Override
            public void onFailure(Exception e) {

            }
        };
        restHighLevelClient.snapshot().deleteAsync(request, RequestOptions.DEFAULT,listener);
    }




}
