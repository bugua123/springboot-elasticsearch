package com.wzw.springbootelasticsearch.IngestAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.ingest.GetPipelineRequest;
import org.elasticsearch.action.ingest.GetPipelineResponse;
import org.elasticsearch.action.ingest.PutPipelineRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.bytes.BytesArray;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.ingest.PipelineConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Slf4j
public class Elastic_Get_Pipeline {

    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void getPipeline() throws IOException {
        GetPipelineRequest request=new GetPipelineRequest("my-pipeline-id");
        request.masterNodeTimeout(TimeValue.timeValueMinutes(1));
        request.masterNodeTimeout("1m");

        GetPipelineResponse response = restHighLevelClient.ingest().getPipeline(request, RequestOptions.DEFAULT);

        boolean found = response.isFound();
        List<PipelineConfiguration> pipelines = response.pipelines();
        for (PipelineConfiguration pipelineConfiguration:pipelines){
            Map<String, Object> configAsMap = pipelineConfiguration.getConfigAsMap();
            System.out.println(configAsMap);
        }
    }

    //异步执行
    @Test
    public void getPipeline_Async() throws IOException, InterruptedException {
        GetPipelineRequest request=new GetPipelineRequest("my-pipeline-id");
        request.masterNodeTimeout("1m");

        ActionListener<GetPipelineResponse> listener=new ActionListener<GetPipelineResponse>() {
            @Override
            public void onResponse(GetPipelineResponse getPipelineResponse) {
                boolean found = getPipelineResponse.isFound();
                System.out.println(found);
            }

            @Override
            public void onFailure(Exception e) {

            }
        };
        restHighLevelClient.ingest().getPipelineAsync(request, RequestOptions.DEFAULT,listener );


    }




}
