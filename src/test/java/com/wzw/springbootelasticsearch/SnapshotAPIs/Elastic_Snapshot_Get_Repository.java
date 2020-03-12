package com.wzw.springbootelasticsearch.SnapshotAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.cluster.repositories.get.GetRepositoriesRequest;
import org.elasticsearch.action.admin.cluster.repositories.get.GetRepositoriesResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.cluster.health.ClusterIndexHealth;
import org.elasticsearch.cluster.health.ClusterShardHealth;
import org.elasticsearch.cluster.metadata.RepositoryMetaData;
import org.elasticsearch.common.Priority;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.rest.RestStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Slf4j
public class Elastic_Snapshot_Get_Repository {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void getRepository() throws IOException {

        GetRepositoriesRequest request=new GetRepositoriesRequest();
        String repositoryName="";
        String[] repositories=new String[]{repositoryName};
        request.repositories(repositories);
        request.local(true);
        request.masterNodeTimeout(TimeValue.timeValueMinutes(1));
        GetRepositoriesResponse repository = restHighLevelClient.snapshot().getRepository(request, RequestOptions.DEFAULT);
        List<RepositoryMetaData> repositories1 = repository.repositories();

    }

    //异步执行
    @Test
    public void getRepository_Async() throws IOException, InterruptedException {
        GetRepositoriesRequest request=new GetRepositoriesRequest();
        String repositoryName="";
        String[] repositories=new String[]{repositoryName};
        request.repositories(repositories);
        request.local(true);
        request.masterNodeTimeout(TimeValue.timeValueMinutes(1));
        ActionListener<GetRepositoriesResponse> listener=new ActionListener<GetRepositoriesResponse>() {
            @Override
            public void onResponse(GetRepositoriesResponse getRepositoriesResponse) {
                List<RepositoryMetaData> repositories1 = getRepositoriesResponse.repositories();
            }

            @Override
            public void onFailure(Exception e) {

            }
        };
        restHighLevelClient.snapshot().getRepositoryAsync(request, RequestOptions.DEFAULT,listener);

    }




}
