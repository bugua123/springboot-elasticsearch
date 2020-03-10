package com.wzw.springbootelasticsearch.IndexAPIs;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.cache.clear.ClearIndicesCacheRequest;
import org.elasticsearch.action.admin.indices.cache.clear.ClearIndicesCacheResponse;
import org.elasticsearch.action.admin.indices.forcemerge.ForceMergeRequest;
import org.elasticsearch.action.admin.indices.forcemerge.ForceMergeResponse;
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
 *    如果存在，ForceMerge 索引
 *
 *
 */
@SpringBootTest
@Slf4j
public class Elastic_Force_MergeIndex {

@Autowired
    RestHighLevelClient restHighLevelClient;


    @Test
    public void testIndex() throws IOException {
        // 判断是否存在索引
        String INDEX_TEST="goods";
        if (!existsIndex(INDEX_TEST)) {
            System.out.println("索引不存在 ");
        }else {
            System.out.println("索引存在 forceMerge 索引");

            forceMergeIndex(INDEX_TEST);

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
    public void forceMergeIndex(String index) throws IOException {

        //单个索引

        ForceMergeRequest request=new ForceMergeRequest(index);
////        //多个索引
//        ForceMergeRequest requestMultiple=new ForceMergeRequest("index1","index2");
//        //所有索引
//        ForceMergeRequest requestAll=new ForceMergeRequest();

        request.indicesOptions(IndicesOptions.lenientExpandOpen());
        request.maxNumSegments(1);
        request.onlyExpungeDeletes(true);
        request.flush(true);

        System.out.println("索引 forceMergeIndex 信息: " + JSON.toJSONString(request));

        try {
            ForceMergeResponse forceMergeResponse= restHighLevelClient.indices().forcemerge(request, RequestOptions.DEFAULT);
            int totalShards = forceMergeResponse.getTotalShards();
            int successfulShards = forceMergeResponse.getSuccessfulShards();
            int failedShards = forceMergeResponse.getFailedShards();
            DefaultShardOperationFailedException[] failures = forceMergeResponse.getShardFailures();
            System.out.println("索引 forceMergeIndex 信息 totalShards: " + totalShards);
            System.out.println("索引 forceMergeIndex 信息 successfulShards: " + successfulShards);
            System.out.println("索引 forceMergeIndex 信息 failedShards: " + failedShards);
            System.out.println("索引 forceMergeIndex 信息 DefaultShardOperationFailedException: " + JSON.toJSONString(failures));

        } catch (ElasticsearchException exception) {
            if (exception.status() == RestStatus.NOT_FOUND) {

            }
        }




    }
}
