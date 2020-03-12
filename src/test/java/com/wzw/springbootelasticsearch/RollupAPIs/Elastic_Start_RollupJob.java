package com.wzw.springbootelasticsearch.RollupAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.rollup.StartRollupJobRequest;
import org.elasticsearch.client.rollup.StartRollupJobResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
@Slf4j
public class Elastic_Start_RollupJob {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void startRollupJob() throws IOException {
        String id="job_Id";
        StartRollupJobRequest request=new StartRollupJobRequest(id);
        StartRollupJobResponse startRollupJobResponse = restHighLevelClient.rollup().startRollupJob(request, RequestOptions.DEFAULT);
        boolean acknowledged = startRollupJobResponse.isAcknowledged();

    }

    //异步执行
    @Test
    public void startRollupJob_Async() throws IOException, InterruptedException {
      
    }

}
