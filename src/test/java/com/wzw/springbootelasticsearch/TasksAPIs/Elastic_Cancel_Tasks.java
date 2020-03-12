package com.wzw.springbootelasticsearch.TasksAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.TaskOperationFailure;
import org.elasticsearch.action.admin.cluster.node.tasks.cancel.CancelTasksRequest;
import org.elasticsearch.action.admin.cluster.node.tasks.cancel.CancelTasksResponse;
import org.elasticsearch.action.admin.cluster.node.tasks.list.ListTasksRequest;
import org.elasticsearch.action.admin.cluster.node.tasks.list.ListTasksResponse;
import org.elasticsearch.action.admin.cluster.node.tasks.list.TaskGroup;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.tasks.Task;
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
public class Elastic_Cancel_Tasks {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void cancelTasks() throws IOException {

        CancelTasksRequest request=new CancelTasksRequest();
        request.setTaskId(new TaskId("nodeId1"));
        request.setActions("cluster:*");
        request.setNodes("nodeId1","nodeId2");

        CancelTasksResponse response = restHighLevelClient.tasks().cancel(request, RequestOptions.DEFAULT);
        List<TaskInfo> tasks = response.getTasks();
        Map<String, List<TaskInfo>> perNodeTasks = response.getPerNodeTasks();
        List<TaskGroup> taskGroups = response.getTaskGroups();
        List<ElasticsearchException> nodeFailures = response.getNodeFailures();
        List<TaskOperationFailure> taskFailures = response.getTaskFailures();

    }

    //异步执行
    @Test
    public void cancelTasks_Async() throws IOException, InterruptedException {


        CancelTasksRequest request=new CancelTasksRequest();
        request.setTaskId(new TaskId("nodeId1"));
        request.setActions("cluster:*");
        request.setNodes("nodeId1","nodeId2");
        ActionListener<CancelTasksResponse> listener=new ActionListener<CancelTasksResponse>() {
            @Override
            public void onResponse(CancelTasksResponse cancelTasksResponse) {
                //返回信息
                System.out.println(cancelTasksResponse);
            }

            @Override
            public void onFailure(Exception e) {

            }
        };
        restHighLevelClient.tasks().cancelAsync(request,RequestOptions.DEFAULT ,listener );

    }




}
