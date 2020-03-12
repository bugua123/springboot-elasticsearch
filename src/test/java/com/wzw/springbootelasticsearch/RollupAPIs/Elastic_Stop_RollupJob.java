package com.wzw.springbootelasticsearch.RollupAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.rollup.StartRollupJobRequest;
import org.elasticsearch.client.rollup.StartRollupJobResponse;
import org.elasticsearch.client.rollup.StopRollupJobRequest;
import org.elasticsearch.client.rollup.StopRollupJobResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
@Slf4j
public class Elastic_Stop_RollupJob {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void stopRollupJob() throws IOException {
        String jobId="jobId";
        StopRollupJobRequest request=new StopRollupJobRequest(jobId );
        request.waitForCompletion(true);
        request.timeout(TimeValue.timeValueSeconds(10));

        StopRollupJobResponse stopRollupJobResponse = restHighLevelClient.rollup().stopRollupJob(request, RequestOptions.DEFAULT);
        boolean acknowledged = stopRollupJobResponse.isAcknowledged();

    }

    //异步执行
    @Test
    public void stopRollupJob_Async() throws IOException, InterruptedException {
      
    }

}
