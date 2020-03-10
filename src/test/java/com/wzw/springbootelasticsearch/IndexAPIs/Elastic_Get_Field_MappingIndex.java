package com.wzw.springbootelasticsearch.IndexAPIs;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.*;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.rest.RestStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Map;

@SpringBootTest
@Slf4j
public class Elastic_Get_Field_MappingIndex {

    /**
     * Get Field Mappings
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
            getFieldMappingIndex(INDEX_TEST);

        }

    }

    /**
     * getMappingIndex 索引
     * @param index
     * @throws IOException
     */

    public void getFieldMappingIndex(String index) throws IOException {
        try {
        GetFieldMappingsRequest request = new GetFieldMappingsRequest();
        request.indices(index);
        request.fields("message","timestamp");
            request.local(true);
            request.indicesOptions(IndicesOptions.lenientExpandOpen());

            GetFieldMappingsResponse fieldMapping = restHighLevelClient.indices().getFieldMapping(request, RequestOptions.DEFAULT);
            final Map<String, Map<String, GetFieldMappingsResponse.FieldMappingMetaData>> mappings =
                    fieldMapping.mappings();
            final Map<String, GetFieldMappingsResponse.FieldMappingMetaData> fieldMappings =
                    mappings.get(index);
            final GetFieldMappingsResponse.FieldMappingMetaData metaData =
                    fieldMappings.get("message");

            final String fullName = metaData.fullName();
            final Map<String, Object> source = metaData.sourceAsMap();

            System.out.println("索引 getFieldMappingIndex 信息 fullName: " + fullName);
            System.out.println("索引 getFieldMappingIndex 信息 source: " + source);


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
