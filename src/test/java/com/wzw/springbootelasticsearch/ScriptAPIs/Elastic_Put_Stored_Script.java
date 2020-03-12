package com.wzw.springbootelasticsearch.ScriptAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.cluster.storedscripts.GetStoredScriptRequest;
import org.elasticsearch.action.admin.cluster.storedscripts.GetStoredScriptResponse;
import org.elasticsearch.action.admin.cluster.storedscripts.PutStoredScriptRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.bytes.BytesArray;
import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.script.StoredScriptSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Map;

@SpringBootTest
@Slf4j
public class Elastic_Put_Stored_Script {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void putStoredScript() throws IOException {
        PutStoredScriptRequest request = new PutStoredScriptRequest();

        //方式一
        request.id("id_123");
//        request.content(new BytesArray(
//                "{\n" +
//                        "\"script\": {\n" +
//                        "\"lang\": \"painless\",\n" +
//                        "\"source\": \"Math.log(_score * 2) + params.multiplier\"" +
//                        "}\n" +
//                        "}\n"
//        ), XContentType.JSON);
////方式二
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.startObject("script");
            {
                builder.field("lang", "painless");
                builder.field("source", "Math.log(_score * 2) + params.multiplier");
            }
            builder.endObject();
        }
        builder.endObject();
        request.content(BytesReference.bytes(builder), XContentType.JSON);
////*************************************************************************
//        XContentBuilder builder = XContentFactory.jsonBuilder();
//        builder.startObject();
//        {
//            builder.startObject("script");
//            {
//                builder.field("lang", "mustache");
//                builder.field("source", "{\"query\":{\"match\":{\"title\":\"{{query_string}}\"}}}");
//            }
//            builder.endObject();
//        }
//        builder.endObject();
//        request.content(BytesReference.bytes(builder), XContentType.JSON);


//        request.context("context");//加上这个参数报错，不知道为啥
        request.timeout(TimeValue.timeValueMinutes(2));

        request.masterNodeTimeout("1m");


        AcknowledgedResponse acknowledgedResponse = restHighLevelClient.putScript(request, RequestOptions.DEFAULT);
        boolean acknowledged = acknowledgedResponse.isAcknowledged();


    }

    //异步执行
    @Test
    public void putStoredScript_Async() throws IOException, InterruptedException {
        PutStoredScriptRequest request = new PutStoredScriptRequest();

        request.id("id_12");

        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.startObject("script");
            {
                builder.field("lang", "mustache");
                builder.field("source", "{\"query\":{\"match\":{\"title\":\"{{query_string}}\"}}}");
            }
            builder.endObject();
        }
        builder.endObject();
        request.content(BytesReference.bytes(builder), XContentType.JSON);


//        request.context("context");
        request.timeout(TimeValue.timeValueMinutes(2));

        request.masterNodeTimeout("1m");

        ActionListener<AcknowledgedResponse> listener=new ActionListener<AcknowledgedResponse>() {
            @Override
            public void onResponse(AcknowledgedResponse acknowledgedResponse) {

            }

            @Override
            public void onFailure(Exception e) {

            }
        };
        restHighLevelClient.putScriptAsync(request,RequestOptions.DEFAULT ,listener );

    }




}
