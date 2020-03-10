package com.wzw.springbootelasticsearch.IndexAPIs;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetFieldMappingsRequest;
import org.elasticsearch.client.indices.GetFieldMappingsResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.PutIndexTemplateRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@Slf4j
public class Elastic_Put_IndexTemplate {

    /**
     * PutIndexTemplate
     */
    @Autowired
    RestHighLevelClient restHighLevelClient;



    @Test
    public void test() throws IOException {
      String  INDEX_TEST= "my-template";
            putIndexTemplate(INDEX_TEST);



    }

    /**
     * getMappingIndex 索引
     * @param temp
     * @throws IOException
     */

    public void putIndexTemplate(String temp) throws IOException {
        try {
            PutIndexTemplateRequest request=new PutIndexTemplateRequest(temp);
            request.patterns(Arrays.asList("pattern-1", "log-*"));
            request.settings(Settings.builder()
                    .put("index.number_of_shards", 3)
                    .put("index.number_of_replicas", 1)
            );

            //方式一
            request.mapping(
                    "{\n" +
                            "  \"properties\": {\n" +
                            "    \"message\": {\n" +
                            "      \"type\": \"text\"\n" +
                            "    }\n" +
                            "  }\n" +
                            "}",
                    XContentType.JSON);
//方式二
//            Map<String, Object> jsonMap = new HashMap<>();
//            {
//                Map<String, Object> properties = new HashMap<>();
//                {
//                    Map<String, Object> message = new HashMap<>();
//                    message.put("type", "text");
//                    properties.put("message", message);
//                }
//                jsonMap.put("properties", properties);
//            }
//            request.mapping(jsonMap);
//
//方式三
//            XContentBuilder builder = XContentFactory.jsonBuilder();
//            builder.startObject();
//            {
//                builder.startObject("properties");
//                {
//                    builder.startObject("message");
//                    {
//                        builder.field("type", "text");
//                    }
//                    builder.endObject();
//                }
//                builder.endObject();
//            }
//            builder.endObject();
//            request.mapping(builder);


            request.alias(new Alias("twitter_alias").filter(QueryBuilders.termQuery("user", "kimchy")));
            request.alias(new Alias("{index}_alias").searchRouting("xyz"));
            request.order(20);
            request.version(4);
            request.create(true);
            request.masterNodeTimeout(TimeValue.timeValueMinutes(1));
            request.masterNodeTimeout("1m");
            AcknowledgedResponse putTemplateResponse = restHighLevelClient.indices().putTemplate(request, RequestOptions.DEFAULT);

            boolean acknowledged = putTemplateResponse.isAcknowledged();
            System.out.println(" acknowledged: " + acknowledged);
        } catch (ElasticsearchException exception) {
            if (exception.status() == RestStatus.NOT_FOUND) {

            }
        }
    }

    /**
     * 判断索引是否存在
     * @param index
     * @return
     * @throws IOException
     */
    public boolean existsIndex(String index) throws IOException {
        GetIndexRequest request = new GetIndexRequest(index);
        request.local(false);
        request.humanReadable(true);
        boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println("existsIndex: " + exists);
        return exists;
    }
}
