package com.wzw.springbootelasticsearch.ScriptAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.cluster.storedscripts.DeleteStoredScriptRequest;
import org.elasticsearch.action.admin.cluster.storedscripts.PutStoredScriptRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
@Slf4j
public class Elastic_Delete_Stored_Script {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void deleteStoredScript() throws IOException {
        DeleteStoredScriptRequest request=new DeleteStoredScriptRequest("calculate-score");
        request.timeout("60s");
        request.masterNodeTimeout("50s");
        AcknowledgedResponse acknowledgedResponse = restHighLevelClient.deleteScript(request, RequestOptions.DEFAULT);
        boolean acknowledged = acknowledgedResponse.isAcknowledged();


    }

    //异步执行
    @Test
    public void deleteStoredScript_Async() throws IOException, InterruptedException {
        DeleteStoredScriptRequest request=new DeleteStoredScriptRequest("calculate-score");
        request.timeout("60s");
        request.masterNodeTimeout("50s");
        ActionListener<AcknowledgedResponse> listener=new ActionListener<AcknowledgedResponse>() {
            @Override
            public void onResponse(AcknowledgedResponse acknowledgedResponse) {
                boolean acknowledged = acknowledgedResponse.isAcknowledged();
            }

            @Override
            public void onFailure(Exception e) {

            }
        };
        restHighLevelClient.deleteScriptAsync(request, RequestOptions.DEFAULT,listener);
    }




}
