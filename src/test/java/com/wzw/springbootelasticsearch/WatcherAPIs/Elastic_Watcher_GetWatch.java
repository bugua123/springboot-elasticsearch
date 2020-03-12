package com.wzw.springbootelasticsearch.WatcherAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.watcher.*;
import org.elasticsearch.common.bytes.BytesArray;
import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
@Slf4j
public class Elastic_Watcher_GetWatch {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void getWatch() throws IOException {
        GetWatchRequest request = new GetWatchRequest("my_watch_id");

        GetWatchResponse response = restHighLevelClient.watcher().getWatch(request, RequestOptions.DEFAULT);
        String watchId = response.getId();
        boolean found = response.isFound();
        long version = response.getVersion();
        WatchStatus status = response.getStatus();
        BytesReference source = response.getSource();

    }

    //异步执行
    @Test
    public void getWatch_Async() throws IOException, InterruptedException {


    }




}
