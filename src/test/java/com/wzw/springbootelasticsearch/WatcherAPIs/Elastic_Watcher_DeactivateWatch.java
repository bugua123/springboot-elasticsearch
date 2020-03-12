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
public class Elastic_Watcher_DeactivateWatch {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void deactivateWatch() throws IOException {
        DeactivateWatchRequest request=new DeactivateWatchRequest("my_watch_id");
        DeactivateWatchResponse deactivateWatchResponse = restHighLevelClient.watcher().deactivateWatch(request, RequestOptions.DEFAULT);
    }

    //异步执行
    @Test
    public void deactivateWatch_Async() throws IOException, InterruptedException {


    }




}
