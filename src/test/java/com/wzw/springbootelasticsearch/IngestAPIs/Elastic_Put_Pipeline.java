package com.wzw.springbootelasticsearch.IngestAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.ingest.PutPipelineRequest;
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
public class Elastic_Put_Pipeline {

    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void putPipeline() throws IOException {
        String source =
                "{\"description\":\"my set of processors\"," +
                        "\"processors\":[{\"set\":{\"field\":\"foo\",\"value\":\"bar\"}}]}";
        PutPipelineRequest request=
                new PutPipelineRequest(
                        "my-pipeline-id",
                        new BytesArray(source.getBytes(StandardCharsets.UTF_8)),
                        XContentType.JSON);
        request.timeout(TimeValue.timeValueMinutes(2));
        request.timeout("2m");

        request.masterNodeTimeout(TimeValue.timeValueMinutes(1));
        request.masterNodeTimeout("1m");

        AcknowledgedResponse acknowledgedResponse = restHighLevelClient.ingest().putPipeline(request, RequestOptions.DEFAULT);
        boolean acknowledged=acknowledgedResponse.isAcknowledged();

        System.out.println(acknowledged);

    }

    //异步执行
    @Test
    public void putPipeline_Async() throws IOException, InterruptedException {

        String source =
                "{\"description\":\"my set of processors\"," +
                        "\"processors\":[{\"set\":{\"field\":\"foo\",\"value\":\"bar\"}}]}";
        PutPipelineRequest request=
                new PutPipelineRequest(
                        "my_pipeline-id",
                        new BytesArray(source.getBytes(StandardCharsets.UTF_8)),
                        XContentType.JSON);
        request.timeout(TimeValue.timeValueMinutes(2));
        request.timeout("2m");

        request.masterNodeTimeout(TimeValue.timeValueMinutes(1));
        request.masterNodeTimeout("1m");

        ActionListener<AcknowledgedResponse> listener=new ActionListener<AcknowledgedResponse>() {
            @Override
            public void onResponse(AcknowledgedResponse acknowledgedResponse) {
                boolean acknowledged=acknowledgedResponse.isAcknowledged();

                System.out.println(acknowledged);

            }

            @Override
            public void onFailure(Exception e) {

            }
        };
        restHighLevelClient.ingest().putPipelineAsync(request,RequestOptions.DEFAULT ,listener );

    }




}
