package com.wzw.springbootelasticsearch.SnapshotAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.cluster.repositories.delete.DeleteRepositoryRequest;
import org.elasticsearch.action.admin.cluster.repositories.verify.VerifyRepositoryRequest;
import org.elasticsearch.action.admin.cluster.repositories.verify.VerifyRepositoryResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
@Slf4j
public class Elastic_Snapshot_Verify_Repository {
    @Autowired
    private RestHighLevelClient restHighLevelClient;



    //同步执行
    @Test
    public void verifyRepository() throws IOException {
        String repositoryName="111";
        VerifyRepositoryRequest request=new VerifyRepositoryRequest(repositoryName);
        request.timeout("1m");
        request.masterNodeTimeout(TimeValue.timeValueMinutes(1));
        VerifyRepositoryResponse verifyRepositoryResponse = restHighLevelClient.snapshot().verifyRepository(request, RequestOptions.DEFAULT);
        List<VerifyRepositoryResponse.NodeView> nodes = verifyRepositoryResponse.getNodes();
    }

    //异步执行
    @Test
    public void verifyRepository_Async() throws IOException, InterruptedException {
        String repositoryName="111";
        VerifyRepositoryRequest request=new VerifyRepositoryRequest(repositoryName);
        request.timeout("1m");
        request.masterNodeTimeout(TimeValue.timeValueMinutes(1));
        ActionListener<VerifyRepositoryResponse> listener=new ActionListener<VerifyRepositoryResponse>() {
            @Override
            public void onResponse(VerifyRepositoryResponse verifyRepositoryResponse) {
                List<VerifyRepositoryResponse.NodeView> nodes = verifyRepositoryResponse.getNodes();


            }

            @Override
            public void onFailure(Exception e) {

            }
        };
        restHighLevelClient.snapshot().verifyRepositoryAsync(request, RequestOptions.DEFAULT,listener);


    }




}
