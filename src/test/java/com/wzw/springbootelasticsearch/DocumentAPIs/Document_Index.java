package com.wzw.springbootelasticsearch.DocumentAPIs;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.admin.indices.cache.clear.ClearIndicesCacheRequest;
import org.elasticsearch.action.admin.indices.cache.clear.ClearIndicesCacheResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.DefaultShardOperationFailedException;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.VersionType;
import org.elasticsearch.rest.RestStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@SpringBootTest
@Slf4j
public class Document_Index {

@Autowired
    RestHighLevelClient restHighLevelClient;


    @Test
    public void testIndex() throws IOException {
        // 判断是否存在索引
        String INDEX_TEST="goods_1";
        documentIndex(INDEX_TEST);

    }

    /**
     * Index
     * @param index
     * @throws IOException
     */
    public void documentIndex(String index) throws IOException {

        try {
            IndexRequest request=new IndexRequest(index);
            request.id("1");
            String jsonString= "{" +
                    "\"user\":\"kimchy\"," +
                    "\"postDate\":\"2013-01-30\"," +
                    "\"message\":\"trying out Elasticsearch\"" +
                    "}";
            request.source(jsonString, XContentType.JSON);




// 方式二
//            Map<String, Object> jsonMap = new HashMap<>();
//            jsonMap.put("user", "kimchy");
//            jsonMap.put("postDate", new Date());
//            jsonMap.put("message", "trying out Elasticsearch");
//            IndexRequest indexRequest = new IndexRequest("posts")
//                    .id("1").source(jsonMap);


//方式三
//            XContentBuilder builder = XContentFactory.jsonBuilder();
//            builder.startObject();
//            {
//                builder.field("user", "kimchy");
//                builder.timeField("postDate", new Date());
//                builder.field("message", "trying out Elasticsearch");
//            }
//            builder.endObject();
//            IndexRequest indexRequest = new IndexRequest("posts")
//                    .id("1").source(builder);

//方式四
//            IndexRequest indexRequest = new IndexRequest("posts")
//                    .id("1")
//                    .source("user", "kimchy",
//                            "postDate", new Date(),
//                            "message", "trying out Elasticsearch");



            request.timeout(TimeValue.timeValueSeconds(1));
            request.timeout("1s");
            request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
            request.setRefreshPolicy("wait_for");
//            request.version(2);
            request.versionType(VersionType.EXTERNAL);
            request.opType(DocWriteRequest.OpType.CREATE);
            request.opType("create");
            request.setPipeline("pipeline");

            IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);

//
//
//            IndexRequest request = new IndexRequest("posts")
//                    .id("1")
//                    .source("field", "value")
//                    .setIfSeqNo(10L)
//                    .setIfPrimaryTerm(20);
//            try {
//                IndexResponse response = client.index(request, RequestOptions.DEFAULT);
//            } catch(ElasticsearchException e) {
//                if (e.status() == RestStatus.CONFLICT) {
//
//                }
//            }
//


//            IndexRequest request = new IndexRequest("posts")
//                    .id("1")
//                    .source("field", "value")
//                    .opType(DocWriteRequest.OpType.CREATE);
//            try {
//                IndexResponse response = client.index(request, RequestOptions.DEFAULT);
//            } catch(ElasticsearchException e) {
//                if (e.status() == RestStatus.CONFLICT) {
//
//                }
//            }




//            System.out.println("索引 ClearCache 信息 DefaultShardOperationFailedException: " + JSON.toJSONString(failures));


        } catch (ElasticsearchException exception) {
            if (exception.status() == RestStatus.NOT_FOUND) {

            }
        }




    }
}
