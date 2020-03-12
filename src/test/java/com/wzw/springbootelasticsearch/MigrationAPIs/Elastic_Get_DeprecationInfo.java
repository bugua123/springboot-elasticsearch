package com.wzw.springbootelasticsearch.MigrationAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.TaskOperationFailure;
import org.elasticsearch.action.admin.cluster.node.tasks.cancel.CancelTasksRequest;
import org.elasticsearch.action.admin.cluster.node.tasks.cancel.CancelTasksResponse;
import org.elasticsearch.action.admin.cluster.node.tasks.list.TaskGroup;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.migration.DeprecationInfoRequest;
import org.elasticsearch.client.migration.DeprecationInfoResponse;
import org.elasticsearch.tasks.TaskId;
import org.elasticsearch.tasks.TaskInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Slf4j
public class Elastic_Get_DeprecationInfo {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void getDeprecationInfo() throws IOException {

       List<String> indices=new ArrayList<>();
       indices.add("test");
        DeprecationInfoRequest request=new DeprecationInfoRequest(indices);

        DeprecationInfoResponse deprecationInfo = restHighLevelClient.migration().getDeprecationInfo(request, RequestOptions.DEFAULT);

        List<DeprecationInfoResponse.DeprecationIssue> clusterIssues =
                deprecationInfo.getClusterSettingsIssues();
        List<DeprecationInfoResponse.DeprecationIssue> nodeIssues =
                deprecationInfo.getNodeSettingsIssues();
        Map<String, List<DeprecationInfoResponse.DeprecationIssue>> indexIssues =
                deprecationInfo.getIndexSettingsIssues();
        List<DeprecationInfoResponse.DeprecationIssue> mlIssues =
                deprecationInfo.getMlSettingsIssues();

    }

    //异步执行
    @Test
    public void getDeprecationInfo_Async() throws IOException, InterruptedException {



    }




}
