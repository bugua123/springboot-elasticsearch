package com.wzw.springbootelasticsearch.DocumentAPIs;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.MultiTermVectorsRequest;
import org.elasticsearch.client.core.MultiTermVectorsResponse;
import org.elasticsearch.client.core.TermVectorsRequest;
import org.elasticsearch.client.core.TermVectorsResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;


@SpringBootTest
@Slf4j
public class Document_Multi_Term_Vectors {

@Autowired
    RestHighLevelClient restHighLevelClient;


    @Test
    public void testIndex() throws IOException {
// 没有调试通，可能原因elasticsearch 索引问题
        multiTermVectors();

    }

    /**
     * Index
     * @throws IOException
     */
    public void multiTermVectors() throws IOException {

        try {
            MultiTermVectorsRequest request = new MultiTermVectorsRequest();
            TermVectorsRequest termVectorsRequest1=new TermVectorsRequest("authors", "1");
            termVectorsRequest1.setFields("user");
            request.add(termVectorsRequest1);


            XContentBuilder docBuilder  = XContentFactory.jsonBuilder();
            docBuilder.startObject().field("user","guest-user").endObject();
            TermVectorsRequest termVectorsRequest2=new TermVectorsRequest("authors" ,docBuilder);
            request.add(termVectorsRequest2);


//            TermVectorsRequest tvrequestTemplate =
//                    new TermVectorsRequest("authors", "fake_id");
//            tvrequestTemplate.setFields("user");
//            String[] ids = {"1", "2"};
//            MultiTermVectorsRequest request =
//                    new MultiTermVectorsRequest(ids, tvrequestTemplate);


            MultiTermVectorsResponse response =
                    restHighLevelClient.mtermvectors(request, RequestOptions.DEFAULT);
            List<TermVectorsResponse> tvresponseList =
                    response.getTermVectorsResponses();
            if (tvresponseList != null) {
                for (TermVectorsResponse tvresponse : tvresponseList) {
                }
            }

        } catch (ElasticsearchException exception) {
            if (exception.status() == RestStatus.NOT_FOUND) {

            }
        }




    }
}
