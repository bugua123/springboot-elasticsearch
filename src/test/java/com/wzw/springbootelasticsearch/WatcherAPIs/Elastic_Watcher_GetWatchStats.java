package com.wzw.springbootelasticsearch.WatcherAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.watcher.ExecuteWatchRequest;
import org.elasticsearch.client.watcher.ExecuteWatchResponse;
import org.elasticsearch.client.watcher.WatcherStatsRequest;
import org.elasticsearch.client.watcher.WatcherStatsResponse;
import org.elasticsearch.common.xcontent.ObjectPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Slf4j
public class Elastic_Watcher_GetWatchStats {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void getWatcherStats() throws IOException {

        WatcherStatsRequest request=new WatcherStatsRequest(true,true);
        WatcherStatsResponse response = restHighLevelClient.watcher().watcherStats(request, RequestOptions.DEFAULT);
        List<WatcherStatsResponse.Node> nodes = response.getNodes();

    }

    //异步执行
    @Test
    public void getWatcherStats_Async() throws IOException, InterruptedException {


    }




}
