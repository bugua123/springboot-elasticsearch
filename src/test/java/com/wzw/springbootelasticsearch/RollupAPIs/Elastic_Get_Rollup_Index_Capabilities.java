package com.wzw.springbootelasticsearch.RollupAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.rollup.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Slf4j
public class Elastic_Get_Rollup_Index_Capabilities {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void getRollupIndexCapabilities() throws IOException {

        GetRollupIndexCapsRequest request=new GetRollupIndexCapsRequest("rollup");
        GetRollupIndexCapsResponse capsResponse = restHighLevelClient.rollup().getRollupIndexCapabilities(request, RequestOptions.DEFAULT);

        Map<String, RollableIndexCaps> rolledPatterns = capsResponse.getJobs();
        RollableIndexCaps docsPattern = rolledPatterns.get("rollup");

        String indexName = docsPattern.getIndexName();

        List<RollupJobCaps> rollupJobs = docsPattern.getJobCaps();
        RollupJobCaps jobCaps = rollupJobs.get(0);

        String jobID = jobCaps.getJobID();

        String rollupIndex = jobCaps.getRollupIndex();

        Map<String, RollupJobCaps.RollupFieldCaps> fieldCaps = jobCaps.getFieldCaps();

        List<Map<String, Object>> timestampCaps = fieldCaps.get("timestamp").getAggs();

        List<Map<String, Object>> temperatureCaps = fieldCaps.get("temperature").getAggs();
    }

    //异步执行
    @Test
    public void getRollupIndexCapabilities_Async() throws IOException, InterruptedException {
      
    }

}
