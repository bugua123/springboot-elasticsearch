package com.wzw.springbootelasticsearch.WatcherAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.watcher.StartWatchServiceRequest;
import org.elasticsearch.client.watcher.StopWatchServiceRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
@Slf4j
public class Elastic_Watcher_StopWatchService {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void stopWatchService() throws IOException {

        StopWatchServiceRequest request=new StopWatchServiceRequest();
        AcknowledgedResponse acknowledgedResponse = restHighLevelClient.watcher().stopWatchService(request, RequestOptions.DEFAULT);
    }

    //异步执行
    @Test
    public void stopWatchService_Async() throws IOException, InterruptedException {


    }




}
