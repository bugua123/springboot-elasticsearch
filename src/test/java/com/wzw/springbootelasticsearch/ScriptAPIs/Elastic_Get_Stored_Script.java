package com.wzw.springbootelasticsearch.ScriptAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.TaskOperationFailure;
import org.elasticsearch.action.admin.cluster.node.tasks.cancel.CancelTasksRequest;
import org.elasticsearch.action.admin.cluster.node.tasks.cancel.CancelTasksResponse;
import org.elasticsearch.action.admin.cluster.node.tasks.list.TaskGroup;
import org.elasticsearch.action.admin.cluster.storedscripts.GetStoredScriptRequest;
import org.elasticsearch.action.admin.cluster.storedscripts.GetStoredScriptResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.script.StoredScriptSource;
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
public class Elastic_Get_Stored_Script {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void getStoredScript() throws IOException {
        GetStoredScriptRequest request=new GetStoredScriptRequest("calculate-score");
        request.masterNodeTimeout(TimeValue.timeValueSeconds(50));
        request.masterNodeTimeout("50s");

        GetStoredScriptResponse response = restHighLevelClient.getScript(request, RequestOptions.DEFAULT);
        StoredScriptSource storedScriptSource = response.getSource();
        String lang = storedScriptSource.getLang();
        String source = storedScriptSource.getSource();
        Map<String, String> options = storedScriptSource.getOptions();

    }

    //异步执行
    @Test
    public void getStoredScript_Async() throws IOException, InterruptedException {
        GetStoredScriptRequest request=new GetStoredScriptRequest("calculate-score");
        request.masterNodeTimeout(TimeValue.timeValueSeconds(50));
        ActionListener<GetStoredScriptResponse> listener=new ActionListener<GetStoredScriptResponse>() {
            @Override
            public void onResponse(GetStoredScriptResponse getStoredScriptResponse) {

            }

            @Override
            public void onFailure(Exception e) {

            }
        };
        restHighLevelClient.getScriptAsync(request, RequestOptions.DEFAULT, listener);

    }




}
