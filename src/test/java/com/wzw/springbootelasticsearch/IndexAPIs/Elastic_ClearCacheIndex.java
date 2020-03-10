package com.wzw.springbootelasticsearch.IndexAPIs;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.cache.clear.ClearIndicesCacheRequest;

import org.elasticsearch.action.admin.indices.cache.clear.ClearIndicesCacheResponse;
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
 *    如果存在，ClearCache 索引
 *
 *
 */
@SpringBootTest
@Slf4j
public class Elastic_ClearCacheIndex {

@Autowired
    RestHighLevelClient restHighLevelClient;


    @Test
    public void testIndex() throws IOException {
        // 判断是否存在索引
        String INDEX_TEST="goods";
        if (!existsIndex(INDEX_TEST)) {
            System.out.println("索引不存在 ");
        }else {
            System.out.println("索引存在 ClearCache 索引");

            clearcacheIndex(INDEX_TEST);

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
    public void clearcacheIndex(String index) throws IOException {

        //单个索引
        ClearIndicesCacheRequest request=new ClearIndicesCacheRequest(index);

////        //多个索引
//        ClearIndicesCacheRequest requestMultiple=new ClearIndicesCacheRequest("index1","index2");
//        //所有索引
//        ClearIndicesCacheRequest requestAll=new ClearIndicesCacheRequest();

        request.indicesOptions(IndicesOptions.lenientExpandOpen());
        request.queryCache(true);
        request.fieldDataCache(true);
        request.requestCache(true);

//        request.fields("field1", "field2", "field3");
        System.out.println("索引 ClearCache 信息: " + JSON.toJSONString(request));


        try {
            ClearIndicesCacheResponse clearIndicesCacheResponse = restHighLevelClient.indices().clearCache(request, RequestOptions.DEFAULT);
            int totalShards = clearIndicesCacheResponse.getTotalShards();
            int successfulShards = clearIndicesCacheResponse.getSuccessfulShards();
            int failedShards = clearIndicesCacheResponse.getFailedShards();
            DefaultShardOperationFailedException[] failures = clearIndicesCacheResponse.getShardFailures();
            System.out.println("索引 ClearCache 信息 totalShards: " + totalShards);
            System.out.println("索引 ClearCache 信息 successfulShards: " + successfulShards);
            System.out.println("索引 ClearCache 信息 failedShards: " + failedShards);
            System.out.println("索引 ClearCache 信息 DefaultShardOperationFailedException: " + JSON.toJSONString(failures));


        } catch (ElasticsearchException exception) {
            if (exception.status() == RestStatus.NOT_FOUND) {

            }
        }




    }
}
