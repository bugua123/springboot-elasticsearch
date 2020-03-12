package com.wzw.springbootelasticsearch.WatcherAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.watcher.PutWatchRequest;
import org.elasticsearch.client.watcher.PutWatchResponse;
import org.elasticsearch.client.watcher.StopWatchServiceRequest;
import org.elasticsearch.common.bytes.BytesArray;
import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
@Slf4j
public class Elastic_Watcher_PutWatchService {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void putWatchService() throws IOException {

        BytesReference watch = new BytesArray("{ \n" +
                "  \"trigger\": { \"schedule\": { \"interval\": \"10h\" } },\n" +
                "  \"input\": { \"simple\": { \"foo\" : \"bar\" } },\n" +
                "  \"actions\": { \"logme\": { \"logging\": { \"text\": \"{{ctx.payload}}\" } } }\n" +
                "}");
        PutWatchRequest request = new PutWatchRequest("my_watch_id", watch, XContentType.JSON);
        request.setActive(false);
        PutWatchResponse response = restHighLevelClient.watcher().putWatch(request, RequestOptions.DEFAULT);
        String watchId = response.getId();
        boolean isCreated = response.isCreated();
        long version = response.getVersion();
    }

    //异步执行
    @Test
    public void putWatchService_Async() throws IOException, InterruptedException {


    }




}
