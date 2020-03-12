package com.wzw.springbootelasticsearch.RollupAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.cluster.storedscripts.DeleteStoredScriptRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.AcknowledgedResponse;
import org.elasticsearch.client.rollup.PutRollupJobRequest;
import org.elasticsearch.client.rollup.job.config.*;
import org.elasticsearch.client.security.PutRoleMappingRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Slf4j
public class Elastic_Put_RollupJob {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void putRollupJob() throws IOException {
        final String indexPattern = "docs";
        final String rollupIndexName = "rollup";
        final String cron = "*/1 * * * * ?";
        final int pageSize = 100;
        final TimeValue timeout = null;
        List<MetricConfig> metrics = new ArrayList<>();
        metrics.add(new MetricConfig("temperature", Arrays.asList("min", "max", "sum")));
        metrics.add(new MetricConfig("voltage", Arrays.asList("avg", "value_count")));

        DateHistogramGroupConfig dateHistogram =
                new DateHistogramGroupConfig("timestamp", DateHistogramInterval.HOUR, new DateHistogramInterval("7d"), "UTC");
        TermsGroupConfig terms = new TermsGroupConfig("hostname", "datacenter");
        HistogramGroupConfig histogram = new HistogramGroupConfig(5L, "load", "net_in", "net_out");

        GroupConfig groups = new GroupConfig(dateHistogram, histogram, terms);

        String id = "job_1";
        RollupJobConfig config = new RollupJobConfig(id, indexPattern, rollupIndexName, cron,
                pageSize, groups, metrics, timeout);

        PutRollupJobRequest request = new PutRollupJobRequest(config);
        AcknowledgedResponse response = restHighLevelClient.rollup().putRollupJob(request, RequestOptions.DEFAULT);

        boolean acknowledged = response.isAcknowledged();
    }

    //异步执行
    @Test
    public void putRollupJob_Async() throws IOException, InterruptedException {
      
    }

}
