package com.wzw.springbootelasticsearch.IngestAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.ingest.DeletePipelineRequest;
import org.elasticsearch.action.ingest.GetPipelineRequest;
import org.elasticsearch.action.ingest.GetPipelineResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.ingest.PipelineConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Slf4j
public class Elastic_delete_Pipeline {

    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void deletePipeline() throws IOException {
        DeletePipelineRequest request=new DeletePipelineRequest("my_pipeline-id");
        request.timeout(TimeValue.timeValueMinutes(2));
        request.timeout("2m");

        request.masterNodeTimeout(TimeValue.timeValueMinutes(1));
        request.masterNodeTimeout("1m");

        AcknowledgedResponse acknowledgedResponse = restHighLevelClient.ingest().deletePipeline(request, RequestOptions.DEFAULT);
        boolean acknowledged = acknowledgedResponse.isAcknowledged();
        System.out.println(acknowledged);

    }

    //异步执行
    @Test
    public void deletePipeline_Async() throws IOException, InterruptedException {
        DeletePipelineRequest request=new DeletePipelineRequest("my_pipeline-id");
        request.timeout("2m");

        request.masterNodeTimeout("1m");

        ActionListener<AcknowledgedResponse> listener=new ActionListener<AcknowledgedResponse>() {
            @Override
            public void onResponse(AcknowledgedResponse acknowledgedResponse) {
                boolean acknowledged = acknowledgedResponse.isAcknowledged();
                System.out.println(acknowledged);
            }
            @Override
            public void onFailure(Exception e) {

            }
        };
        restHighLevelClient.ingest().deletePipelineAsync(request, RequestOptions.DEFAULT,listener );


    }




}
