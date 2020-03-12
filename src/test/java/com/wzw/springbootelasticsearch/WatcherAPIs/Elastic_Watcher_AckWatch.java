package com.wzw.springbootelasticsearch.WatcherAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.watcher.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
@Slf4j
public class Elastic_Watcher_AckWatch {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void ackWatch() throws IOException {
        AckWatchRequest request=new AckWatchRequest("my_watch_id","logme","emailme");
        AckWatchResponse response = restHighLevelClient.watcher().ackWatch(request, RequestOptions.DEFAULT);
        WatchStatus watchStatus = response.getStatus();
        ActionStatus actionStatus = watchStatus.actionStatus("logme");
        ActionStatus.AckStatus.State ackState = actionStatus.ackStatus().state();
    }

    //异步执行
    @Test
    public void ackWatch_Async() throws IOException, InterruptedException {


    }




}
