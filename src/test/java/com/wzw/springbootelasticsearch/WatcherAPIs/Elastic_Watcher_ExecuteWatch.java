package com.wzw.springbootelasticsearch.WatcherAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.watcher.*;
import org.elasticsearch.common.xcontent.ObjectPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Map;

@SpringBootTest
@Slf4j
public class Elastic_Watcher_ExecuteWatch {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void executeWatch() throws IOException {
        ExecuteWatchRequest request = ExecuteWatchRequest.byId("my_watch_id");
        request.setAlternativeInput("{ \"foo\" : \"bar\" }");
        request.setActionMode("action1", ExecuteWatchRequest.ActionExecutionMode.SIMULATE);
        request.setRecordExecution(true);
        request.setIgnoreCondition(true);
        request.setTriggerData("{\"triggered_time\":\"now\"}");
        request.setDebug(true);
        ExecuteWatchResponse response = restHighLevelClient.watcher().executeWatch(request, RequestOptions.DEFAULT);

        String id = response.getRecordId();
        Map<String, Object> watch = response.getRecordAsMap();
        String watch_id = ObjectPath.eval("watch_record.watch_id", watch);



    }

    //异步执行
    @Test
    public void executeWatch_Async() throws IOException, InterruptedException {


    }




}
