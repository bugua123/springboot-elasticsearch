package com.wzw.springbootelasticsearch.SnapshotAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.cluster.repositories.put.PutRepositoryRequest;
import org.elasticsearch.action.admin.cluster.snapshots.create.CreateSnapshotRequest;
import org.elasticsearch.action.admin.cluster.snapshots.create.CreateSnapshotResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.repositories.fs.FsRepository;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.snapshots.SnapshotInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
@Slf4j
public class Elastic_Snapshot_Create_Snapshot {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void createSnapshot() throws IOException {
        CreateSnapshotRequest request=new CreateSnapshotRequest();
        String repositoryName="111";
        request.repository(repositoryName);
        String snapshotName="222";
        request.snapshot(snapshotName);

        request.indices("test_index1","test_index2");
        request.indicesOptions(IndicesOptions.fromOptions(false,false ,true ,true ));
        request.partial(false);
        request.includeGlobalState(true);

        request.masterNodeTimeout(TimeValue.timeValueMinutes(1));
        request.waitForCompletion(true);

        CreateSnapshotResponse createSnapshotResponse = restHighLevelClient.snapshot().create(request, RequestOptions.DEFAULT);
        SnapshotInfo snapshotInfo = createSnapshotResponse.getSnapshotInfo();
        RestStatus status = createSnapshotResponse.status();

    }

    //异步执行
    @Test
    public void createSnapshot_Async() throws IOException, InterruptedException {

        CreateSnapshotRequest request=new CreateSnapshotRequest();
        String repositoryName="111";
        request.repository(repositoryName);
        String snapshotName="222";
        request.snapshot(snapshotName);

        request.indices("test_index1","test_index2");
        request.indicesOptions(IndicesOptions.fromOptions(false,false ,true ,true ));
        request.partial(false);
        request.includeGlobalState(true);

        request.masterNodeTimeout(TimeValue.timeValueMinutes(1));
        request.waitForCompletion(true);

        ActionListener<CreateSnapshotResponse> listener=new ActionListener<CreateSnapshotResponse>() {
            @Override
            public void onResponse(CreateSnapshotResponse createSnapshotResponse) {

            }

            @Override
            public void onFailure(Exception e) {

            }
        };
        restHighLevelClient.snapshot().createAsync(request, RequestOptions.DEFAULT,listener);
    }




}
