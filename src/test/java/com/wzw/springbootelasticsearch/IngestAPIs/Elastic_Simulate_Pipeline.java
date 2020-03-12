package com.wzw.springbootelasticsearch.IngestAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.ingest.*;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.bytes.BytesArray;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@SpringBootTest
@Slf4j
public class Elastic_Simulate_Pipeline {

    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void simulatePipeline() throws IOException {
        String source =
                "{\"" +
                        "pipeline\":{" +
                        "\"description\":\"_description\"," +
                        "\"processors\":[{\"set\":{\"field\":\"field2\",\"value\":\"_value\"}}]" +
                        "}," +
                        "\"docs\":[" +
                        "{\"_index\":\"index\",\"_id\":\"id\",\"_source\":{\"foo\":\"bar\"}}," +
                        "{\"_index\":\"index\",\"_id\":\"id\",\"_source\":{\"foo\":\"rab\"}}" +
                        "]" +
                        "}";

        SimulatePipelineRequest request=
                new SimulatePipelineRequest(new BytesArray(source.getBytes(StandardCharsets.UTF_8)), XContentType.JSON);

        request.setId("my-pipeline-id");
        request.setVerbose(true);

        SimulatePipelineResponse response = restHighLevelClient.ingest().simulate(request, RequestOptions.DEFAULT);

        //返回信息
        for (SimulateDocumentResult result:response.getResults()) {
            if(request.isVerbose()){
                SimulateDocumentVerboseResult verboseResult= (SimulateDocumentVerboseResult) result;
                for (SimulateProcessorResult processorResult:verboseResult.getProcessorResults()) {
                    processorResult.getIngestDocument();
                    processorResult.getFailure();
                }
            }else {
                SimulateDocumentBaseResult baseResult= (SimulateDocumentBaseResult) result;
                baseResult.getIngestDocument();
                baseResult.getFailure();
            }
        }

    }

    //异步执行
    @Test
    public void simulatePipeline_Async() throws IOException, InterruptedException {
        String source =
                "{\"" +
                        "pipeline\":{" +
                        "\"description\":\"_description\"," +
                        "\"processors\":[{\"set\":{\"field\":\"field2\",\"value\":\"_value\"}}]" +
                        "}," +
                        "\"docs\":[" +
                        "{\"_index\":\"index\",\"_id\":\"id\",\"_source\":{\"foo\":\"bar\"}}," +
                        "{\"_index\":\"index\",\"_id\":\"id\",\"_source\":{\"foo\":\"rab\"}}" +
                        "]" +
                        "}";

        SimulatePipelineRequest request=
                new SimulatePipelineRequest(new BytesArray(source.getBytes(StandardCharsets.UTF_8)), XContentType.JSON);

        request.setId("my-pipeline-id");
        request.setVerbose(true);
        ActionListener<SimulatePipelineResponse> listener=new ActionListener<SimulatePipelineResponse>() {
            @Override
            public void onResponse(SimulatePipelineResponse simulatePipelineResponse) {
                System.out.println(simulatePipelineResponse);
            }

            @Override
            public void onFailure(Exception e) {

            }
        };
        restHighLevelClient.ingest().simulateAsync(request,RequestOptions.DEFAULT,listener  );
    }




}
