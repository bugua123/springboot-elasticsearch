package com.wzw.springbootelasticsearch.RollupAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.AcknowledgedResponse;
import org.elasticsearch.client.rollup.DeleteRollupJobRequest;
import org.elasticsearch.client.rollup.StopRollupJobRequest;
import org.elasticsearch.client.rollup.StopRollupJobResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
@Slf4j
public class Elastic_Delete_RollupJob {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void deleteRollupJob() throws IOException {
        String jobId="jobId";

        DeleteRollupJobRequest request=new DeleteRollupJobRequest(jobId);
        AcknowledgedResponse acknowledgedResponse = restHighLevelClient.rollup().deleteRollupJob(request, RequestOptions.DEFAULT);

    }

    //异步执行
    @Test
    public void deleteRollupJob_Async() throws IOException, InterruptedException {
      
    }

}
