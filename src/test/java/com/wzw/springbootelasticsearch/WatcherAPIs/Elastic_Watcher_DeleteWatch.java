package com.wzw.springbootelasticsearch.WatcherAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.watcher.*;
import org.elasticsearch.common.bytes.BytesReference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
@Slf4j
public class Elastic_Watcher_DeleteWatch {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void deleteWatch() throws IOException {
        DeleteWatchRequest request = new DeleteWatchRequest("my_watch_id");
        DeleteWatchResponse response = restHighLevelClient.watcher().deleteWatch(request, RequestOptions.DEFAULT);
        String watchId = response.getId();
        boolean found = response.isFound();
        long version = response.getVersion();
    }

    //异步执行
    @Test
    public void deleteWatch_Async() throws IOException, InterruptedException {


    }




}
