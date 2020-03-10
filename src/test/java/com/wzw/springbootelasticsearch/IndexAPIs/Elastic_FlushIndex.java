package com.wzw.springbootelasticsearch.IndexAPIs;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.flush.FlushRequest;
import org.elasticsearch.action.admin.indices.flush.FlushResponse;
import org.elasticsearch.action.support.DefaultShardOperationFailedException;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.rest.RestStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;


/**
 * 1、判断索引是否存在
 *    如果存在，Flush 索引
 *
 *
 */
@SpringBootTest
@Slf4j
public class Elastic_FlushIndex {

@Autowired
    RestHighLevelClient restHighLevelClient;


    @Test
    public void testIndex() throws IOException {
        // 判断是否存在索引
        String INDEX_TEST="goods";
        if (!existsIndex(INDEX_TEST)) {
            System.out.println("索引不存在 ");
        }else {
            System.out.println("索引存在Flush索引");

            refreshIndex(INDEX_TEST);

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
    /**
     *  Refresh索引
     * @param index
     * @throws IOException
     */
    public void refreshIndex(String index) throws IOException {

        //单个索引
        FlushRequest request=new FlushRequest(index);
////        //多个索引
//        FlushRequest requestMultiple=new FlushRequest("index1","index2");
//        //所有索引
//        FlushRequest requestAll=new FlushRequest();

        request.indicesOptions(IndicesOptions.lenientExpandOpen());
        request.waitIfOngoing(true);
        request.force(true);


        FlushResponse flushRequest=null;
        try {
            flushRequest = restHighLevelClient.indices().flush(request, RequestOptions.DEFAULT);
        } catch (ElasticsearchException exception) {
            if (exception.status() == RestStatus.NOT_FOUND) {

            }
        }

        System.out.println("索引 Flush信息: " + JSON.toJSONString(request));


        int totalShards = flushRequest.getTotalShards();
        int successfulShards = flushRequest.getSuccessfulShards();
        int failedShards = flushRequest.getFailedShards();
        DefaultShardOperationFailedException[] failures = flushRequest.getShardFailures();
        System.out.println("索引Flush信息 totalShards: " + totalShards);
        System.out.println("索引 Flush信息 successfulShards: " + successfulShards);
        System.out.println("索引 Flush信息 failedShards: " + failedShards);
        System.out.println("索引 Flush信息 DefaultShardOperationFailedException: " + JSON.toJSONString(failures));



    }
}
