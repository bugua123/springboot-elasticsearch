package com.wzw.springbootelasticsearch.DocumentAPIs;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Map;


@SpringBootTest
@Slf4j
public class Document_Multi_Get_Index {

@Autowired
    RestHighLevelClient restHighLevelClient;


    @Test
    public void testIndex() throws IOException {

        String INDEX_TEST="goods_1";
        multiGetIndex(INDEX_TEST);

    }

    /**
     * Index
     * @param index
     * @throws IOException
     */
    public void multiGetIndex(String index) throws IOException {

        try {
            MultiGetRequest request=new MultiGetRequest();
            request.add(new MultiGetRequest.Item(
                    "index",
                    "example_id"
            ));

//            request.add(new MultiGetRequest.Item("index", "example_id")
//                    .fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE));
            request.add(new MultiGetRequest.Item("index","another_id"));

            String[] includes= Strings.EMPTY_ARRAY;
            String[] excludes=new String[]{"foo","*r"};
            FetchSourceContext fetchSourceContext=new FetchSourceContext(true,includes,excludes);
            request.add(new MultiGetRequest.Item("index","example_id").fetchSourceContext(fetchSourceContext));
//后续补充其他方式

            //执行
            MultiGetResponse multiGetResponse = restHighLevelClient.mget(request, RequestOptions.DEFAULT);
            MultiGetItemResponse firstItem = multiGetResponse.getResponses()[0];
            GetResponse firstGet = firstItem.getResponse();
            String index_ = firstItem.getIndex();
            String id_ = firstItem.getId();

            System.out.println(firstGet);


            if(null!=firstGet){
                if (firstGet.isExists()) {
                    long version = firstGet.getVersion();
                    String sourceAsString = firstGet.getSourceAsString();
                    Map<String, Object> sourceAsMap = firstGet.getSourceAsMap();
                    byte[] sourceAsBytes = firstGet.getSourceAsBytes();
                } else {

                }
            }
        } catch (ElasticsearchException exception) {
            if (exception.status() == RestStatus.NOT_FOUND) {

            }
        }




    }
}
