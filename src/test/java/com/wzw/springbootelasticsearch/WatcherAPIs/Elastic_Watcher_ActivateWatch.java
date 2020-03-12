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
public class Elastic_Watcher_ActivateWatch {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void activateWatch() throws IOException {
        ActivateWatchRequest request=new ActivateWatchRequest("my_watch_id");
        ActivateWatchResponse activateWatchResponse = restHighLevelClient.watcher().activateWatch(request, RequestOptions.DEFAULT);
        WatchStatus status = activateWatchResponse.getStatus();
    }

    //异步执行
    @Test
    public void activateWatch_Async() throws IOException, InterruptedException {


    }




}
