package com.wzw.springbootelasticsearch.DocumentAPIs;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;


@SpringBootTest
@Slf4j
public class Document_Delete_Index {

@Autowired
    RestHighLevelClient restHighLevelClient;


    @Test
    public void testIndex() throws IOException {

        String INDEX_TEST="goods_1";
        deleteIndex(INDEX_TEST);

    }

    /**
     * Index
     * @param index
     * @throws IOException
     */
    public void deleteIndex(String index) throws IOException {

        try {
            DeleteRequest request = new DeleteRequest(
                    index,
                    "bvrIU2kfSAWKQHycnLZG1w");

            DeleteResponse deleteResponse = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
            String index_ = deleteResponse.getIndex();
            String id_ = deleteResponse.getId();
            long version_ = deleteResponse.getVersion();
            ReplicationResponse.ShardInfo shardInfo = deleteResponse.getShardInfo();
            if (shardInfo.getTotal() != shardInfo.getSuccessful()) {

            }
            if (shardInfo.getFailed() > 0) {
                for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                    String reason = failure.reason();
                }
            }

            System.out.println("deleteResponse:"+deleteResponse);

        } catch (ElasticsearchException exception) {
            if (exception.status() == RestStatus.NOT_FOUND) {

            }
        }




    }
}
