package com.wzw.springbootelasticsearch.RollupAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.AcknowledgedResponse;
import org.elasticsearch.client.rollup.DeleteRollupJobRequest;
import org.elasticsearch.client.rollup.GetRollupJobRequest;
import org.elasticsearch.client.rollup.GetRollupJobResponse;
import org.elasticsearch.client.rollup.job.config.RollupJobConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
@Slf4j
public class Elastic_Get_RollupJob {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void getRollupJob() throws IOException {

        //返回所有
        GetRollupJobRequest request=new GetRollupJobRequest();
        //单个
        GetRollupJobRequest request1=new GetRollupJobRequest("job_1");


        GetRollupJobResponse response = restHighLevelClient.rollup().getRollupJob(request, RequestOptions.DEFAULT);

        GetRollupJobResponse.JobWrapper jobWrapper = response.getJobs().get(0);
        RollupJobConfig job = jobWrapper.getJob();
        GetRollupJobResponse.RollupIndexerJobStats stats = jobWrapper.getStats();
        GetRollupJobResponse.RollupJobStatus status = jobWrapper.getStatus();



    }

    //异步执行
    @Test
    public void getRollupJob_Async() throws IOException, InterruptedException {
      
    }

}
