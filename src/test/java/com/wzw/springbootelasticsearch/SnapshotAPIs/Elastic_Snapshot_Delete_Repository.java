package com.wzw.springbootelasticsearch.SnapshotAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.cluster.repositories.delete.DeleteRepositoryRequest;
import org.elasticsearch.action.admin.cluster.repositories.put.PutRepositoryRequest;
import org.elasticsearch.action.admin.cluster.snapshots.delete.DeleteSnapshotRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.repositories.fs.FsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
@Slf4j
public class Elastic_Snapshot_Delete_Repository {
    @Autowired
    private RestHighLevelClient restHighLevelClient;



    //同步执行
    @Test
    public void deleteRepository() throws IOException {
        String repositoryName="111";
        DeleteRepositoryRequest request=new DeleteRepositoryRequest (repositoryName);
        request.timeout("1m");
        request.masterNodeTimeout("1m");

        AcknowledgedResponse acknowledgedResponse = restHighLevelClient.snapshot().deleteRepository(request, RequestOptions.DEFAULT);

    }

    //异步执行
    @Test
    public void deleteRepository_Async() throws IOException, InterruptedException {

        String repositoryName="111";
        DeleteRepositoryRequest request=new DeleteRepositoryRequest (repositoryName);
        request.timeout("1m");
        request.masterNodeTimeout("1m");

        ActionListener<AcknowledgedResponse> listener=new ActionListener<AcknowledgedResponse>() {
            @Override
            public void onResponse(AcknowledgedResponse acknowledgedResponse) {
                boolean acknowledged = acknowledgedResponse.isAcknowledged();
            }

            @Override
            public void onFailure(Exception e) {

            }
        };
        restHighLevelClient.snapshot().deleteRepositoryAsync(request, RequestOptions.DEFAULT,listener);

    }




}
