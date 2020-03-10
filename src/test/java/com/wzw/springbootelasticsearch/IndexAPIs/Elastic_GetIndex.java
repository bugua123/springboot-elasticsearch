package com.wzw.springbootelasticsearch.IndexAPIs;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.*;
import org.elasticsearch.cluster.metadata.AliasMetaData;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.rest.RestStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Slf4j
public class Elastic_GetIndex {

    /**
     * Get Index
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
            getIndex(INDEX_TEST);
        }
    }

    /**
     * getIndex 索引
     * @param index
     * @throws IOException
     */

    public void getIndex(String index) throws IOException {
        try {

          GetIndexRequest request=new GetIndexRequest(index);
          request.includeDefaults(true);
          request.indicesOptions(IndicesOptions.lenientExpandOpen());
            GetIndexResponse getIndexResponse = restHighLevelClient.indices().get(request, RequestOptions.DEFAULT);
            MappingMetaData indexMappings = getIndexResponse.getMappings().get(index);
            Map<String, Object> indexTypeMappings = indexMappings.getSourceAsMap();
            List<AliasMetaData> indexAliases = getIndexResponse.getAliases().get(index);
            String numberOfShardsString = getIndexResponse.getSetting(index, "index.number_of_shards");
            Settings indexSettings = getIndexResponse.getSettings().get(index);
            Integer numberOfShards = indexSettings.getAsInt("index.number_of_shards", null);
            TimeValue time = getIndexResponse.getDefaultSettings().get(index)
                    .getAsTime("index.refresh_interval", null);

            System.out.println( " numberOfShards: " + numberOfShards);

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
