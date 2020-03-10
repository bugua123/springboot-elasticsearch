package com.wzw.springbootelasticsearch.IndexAPIs;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsRequest;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetFieldMappingsRequest;
import org.elasticsearch.client.indices.GetFieldMappingsResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.rest.RestStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Map;

@SpringBootTest
@Slf4j
public class Elastic_GetSettingIndex {

    /**
     * GetSettingIndex
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
            getSettingIndex(INDEX_TEST);

        }

    }

    /**
     * getSettingIndex 索引
     * @param index
     * @throws IOException
     */

    public void getSettingIndex(String index) throws IOException {
        try {

            GetSettingsRequest request=new GetSettingsRequest().indices(index);
            request.names("index.number_of_shards");
            request.includeDefaults(true);
            request.indicesOptions(IndicesOptions.lenientExpandOpen());
            GetSettingsResponse getSettingsResponse=restHighLevelClient.indices().getSettings(request, RequestOptions.DEFAULT);

            String numberOfShardsString = getSettingsResponse.getSetting(index, "index.number_of_shards");
            Settings indexSettings = getSettingsResponse.getIndexToSettings().get(index);
            Integer numberOfShards = indexSettings.getAsInt("index.number_of_shards", null);
            System.out.println("索引 getSettingIndex 信息 numberOfShardsString: " + numberOfShardsString);
            System.out.println("索引 getSettingIndex 信息 numberOfShards: " + numberOfShards);


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
