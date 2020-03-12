package com.wzw.springbootelasticsearch.TasksAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.TaskOperationFailure;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.cluster.node.tasks.cancel.CancelTasksResponse;
import org.elasticsearch.action.admin.cluster.node.tasks.list.ListTasksRequest;
import org.elasticsearch.action.admin.cluster.node.tasks.list.ListTasksResponse;
import org.elasticsearch.action.admin.cluster.node.tasks.list.TaskGroup;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.cluster.health.ClusterIndexHealth;
import org.elasticsearch.cluster.health.ClusterShardHealth;
import org.elasticsearch.common.Priority;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.tasks.TaskId;
import org.elasticsearch.tasks.TaskInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Slf4j
public class Elastic_List_Tasks {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void listTasks() throws IOException {

        ListTasksRequest request=new ListTasksRequest();
        request.setActions("cluster:*");
        request.setNodes("nodeId1","nodeId2");
        request.setParentTaskId(new TaskId("parentTaskId",42));
        request.setDetailed(true);

        request.setWaitForCompletion(true);
        request.setTimeout(TimeValue.timeValueSeconds(50));

        request.setTimeout("50s");

        ListTasksResponse response = restHighLevelClient.tasks().list(request, RequestOptions.DEFAULT);
        //返回信息
        List<TaskInfo> tasks = response.getTasks();

        Map<String, List<TaskInfo>> perNodeTasks = response.getPerNodeTasks();
        List<TaskGroup> taskGroups = response.getTaskGroups();
        List<ElasticsearchException> nodeFailures = response.getNodeFailures();
        List<TaskOperationFailure> taskFailures = response.getTaskFailures();
        System.out.println(tasks);

    }

    //异步执行
    @Test
    public void listTasks_Async() throws IOException, InterruptedException {

        ListTasksRequest request=new ListTasksRequest();
        request.setActions("cluster:*");
        request.setNodes("nodeId1","nodeId2");
        request.setParentTaskId(new TaskId("parentTaskId",42));
        request.setDetailed(true);

        request.setWaitForCompletion(true);
        request.setTimeout(TimeValue.timeValueSeconds(50));

        ActionListener<ListTasksResponse> listener=new ActionListener<ListTasksResponse>() {
            @Override
            public void onResponse(ListTasksResponse listTasksResponse) {
                Map<String, List<TaskInfo>> perNodeTasks = listTasksResponse.getPerNodeTasks();

            }

            @Override
            public void onFailure(Exception e) {

            }
        };
        restHighLevelClient.tasks().listAsync(request, RequestOptions.DEFAULT, listener);

    }




}
