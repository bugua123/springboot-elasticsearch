package com.wzw.springbootelasticsearch.IndexAPIs;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.flush.FlushRequest;
import org.elasticsearch.action.admin.indices.flush.FlushResponse;
import org.elasticsearch.action.admin.indices.flush.SyncedFlushRequest;
import org.elasticsearch.action.support.DefaultShardOperationFailedException;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.SyncedFlushResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.rest.RestStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Map;


/**
 * 1、判断索引是否存在
 *    如果存在，Flush Synced 索引
 *
 *
 */
@SpringBootTest
@Slf4j
public class Elastic_Flush_SyncedIndex {

@Autowired
    RestHighLevelClient restHighLevelClient;


    @Test
    public void testIndex() throws IOException {
        // 判断是否存在索引
        String INDEX_TEST="goods";
        if (!existsIndex(INDEX_TEST)) {
            System.out.println("索引不存在 ");
        }else {
            System.out.println("索引存在Flush Synced索引");

            flushSyncedIndex(INDEX_TEST);

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
    public void flushSyncedIndex(String index) throws IOException {

        //单个索引
        SyncedFlushRequest request=new SyncedFlushRequest(index);

////        //多个索引
//        SyncedFlushRequest requestMultiple=new SyncedFlushRequest("index1","index2");
//        //所有索引
//        SyncedFlushRequest requestAll=new SyncedFlushRequest();

        request.indicesOptions(IndicesOptions.lenientExpandOpen());

        System.out.println("索引 Flush Synced信息: " + JSON.toJSONString(request));


        try {
            SyncedFlushResponse flushSyncedResponse = restHighLevelClient.indices().flushSynced(request, RequestOptions.DEFAULT);



            int totalShards = flushSyncedResponse.totalShards();
            int successfulShards = flushSyncedResponse.successfulShards();
            int failedShards = flushSyncedResponse.failedShards();

            for (Map.Entry<String, SyncedFlushResponse.IndexResult> responsePerIndexEntry:
                    flushSyncedResponse.getIndexResults().entrySet()) {
                String indexName = responsePerIndexEntry.getKey();
                SyncedFlushResponse.IndexResult indexResult = responsePerIndexEntry.getValue();
                int totalShardsForIndex = indexResult.totalShards();
                int successfulShardsForIndex = indexResult.successfulShards();
                int failedShardsForIndex = indexResult.failedShards();
                if (failedShardsForIndex > 0) {
                    for (SyncedFlushResponse.ShardFailure failureEntry: indexResult.failures()) {
                        int shardId = failureEntry.getShardId();
                        String failureReason = failureEntry.getFailureReason();
                        Map<String, Object> routing = failureEntry.getRouting();
                    }
                }
            }


            System.out.println("索引Flush Synced信息 totalShards: " + totalShards);
            System.out.println("索引 Flush Synced信息 successfulShards: " + successfulShards);
            System.out.println("索引 Flush Synced信息 failedShards: " + failedShards);

        } catch (ElasticsearchException exception) {
            if (exception.status() == RestStatus.NOT_FOUND) {

            }
        }


    }
}
