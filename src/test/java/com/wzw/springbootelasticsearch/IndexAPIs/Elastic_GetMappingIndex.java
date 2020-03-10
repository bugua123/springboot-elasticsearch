package com.wzw.springbootelasticsearch.IndexAPIs;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetMappingsRequest;
import org.elasticsearch.client.indices.GetMappingsResponse;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Map;

@SpringBootTest
@Slf4j
public class Elastic_GetMappingIndex {

    /**
     * Get Mappings
     */
    @Autowired
    RestHighLevelClient restHighLevelClient;



    @Test
    public void testIndex() throws IOException {
        // 判断是否存在索引
        String INDEX_TEST="goods_1";
        if (!existsIndex(INDEX_TEST)) {
            System.out.println("索引不存在 ");
        }else {
            System.out.println("索引存在 ");
            getMappingIndex(INDEX_TEST);

        }

    }

    /**
     * getMappingIndex 索引
     * @param index
     * @throws IOException
     */

    public void getMappingIndex(String index) throws IOException {
        try {
        GetMappingsRequest request = new GetMappingsRequest();
        request.indices(index);

        request.setTimeout(TimeValue.timeValueMillis(2));
        request.indicesOptions(IndicesOptions.lenientExpandOpen());

        GetMappingsResponse getMappingsResponse= restHighLevelClient.indices().getMapping(request, RequestOptions.DEFAULT);
        Map<String, MappingMetaData> allMappings = getMappingsResponse.mappings();
        MappingMetaData indexMapping = allMappings.get(index);
        Map<String, Object> mapping = indexMapping.sourceAsMap();

            System.out.println("索引 getMappingIndex 信息 allMappings: " + allMappings);
            System.out.println("索引 getMappingIndex 信息 indexMapping: " + indexMapping);
            System.out.println("索引 getMappingIndex 信息 mapping: " + mapping);


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
