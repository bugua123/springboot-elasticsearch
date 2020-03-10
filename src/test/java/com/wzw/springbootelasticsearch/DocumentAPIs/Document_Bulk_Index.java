package com.wzw.springbootelasticsearch.DocumentAPIs;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;


@SpringBootTest
@Slf4j
public class Document_Bulk_Index {

@Autowired
    RestHighLevelClient restHighLevelClient;


    @Test
    public void testIndex() throws IOException {

        String INDEX_TEST="goods_1";
        bulkIndex(INDEX_TEST);

    }

    /**
     * Index
     * @param index
     * @throws IOException
     */
    public void bulkIndex(String index) throws IOException {

        try {
            //方式一
            BulkRequest request = new BulkRequest();
            request.add(new IndexRequest("posts").id("1")
                    .source(XContentType.JSON,"field", "foo"));
            request.add(new IndexRequest("posts").id("2")
                    .source(XContentType.JSON,"field", "bar"));
            request.add(new IndexRequest("posts").id("3")
                    .source(XContentType.JSON,"field", "baz"));

//方式二
//            BulkRequest request = new BulkRequest();
//            request.add(new DeleteRequest("posts", "3"));
//            request.add(new UpdateRequest("posts", "2")
//                    .doc(XContentType.JSON,"other", "test"));
//            request.add(new IndexRequest("posts").id("4")
//                    .source(XContentType.JSON,"field", "baz"));



            request.timeout(TimeValue.timeValueMinutes(2));
            request.timeout("2m");
            request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
            request.setRefreshPolicy("wait_for");
            request.waitForActiveShards(2);
            request.waitForActiveShards(ActiveShardCount.ALL);
            request.pipeline("pipelineId");
            request.routing("routingId");

            BulkResponse bulkResponse = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);

            for (BulkItemResponse bulkItemResponse : bulkResponse) {
                DocWriteResponse itemResponse = bulkItemResponse.getResponse();

                if (bulkItemResponse.isFailed()) {
                    BulkItemResponse.Failure failure =
                            bulkItemResponse.getFailure();
                }

                switch (bulkItemResponse.getOpType()) {
                    case INDEX:
                    case CREATE:
                        IndexResponse indexResponse = (IndexResponse) itemResponse;
                        break;
                    case UPDATE:
                        UpdateResponse updateResponse = (UpdateResponse) itemResponse;
                        break;
                    case DELETE:
                        DeleteResponse deleteResponse = (DeleteResponse) itemResponse;
                }
            }


            System.out.println("bulkResponse:"+bulkResponse);

        } catch (ElasticsearchException exception) {
            if (exception.status() == RestStatus.NOT_FOUND) {

            }
        }




    }
}
