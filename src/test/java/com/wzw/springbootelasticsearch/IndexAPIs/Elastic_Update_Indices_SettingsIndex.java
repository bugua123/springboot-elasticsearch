package com.wzw.springbootelasticsearch.IndexAPIs;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequest;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetFieldMappingsRequest;
import org.elasticsearch.client.indices.GetFieldMappingsResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@Slf4j
public class Elastic_Update_Indices_SettingsIndex {

    /**
     *  Update Indices Settings
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
            updateIndexSettings(INDEX_TEST);

        }

    }

    /**
     * updateIndexSettings 索引
     * @param index
     * @throws IOException
     */

    public void updateIndexSettings(String index) throws IOException {
        try {
            //单个索引
            UpdateSettingsRequest request=new UpdateSettingsRequest(index);
//            //多个索引
//            UpdateSettingsRequest requestMultiple=new UpdateSettingsRequest("index1","index2");
//            //全部索引
//            UpdateSettingsRequest requestAll=new UpdateSettingsRequest();

            String settingKey="index.number_of_replicas";
            int settingValue=0;
            Settings settings=
                    Settings.builder()
                    .put(settingKey,settingValue )
                    .build();
            //方式一
            Settings.Builder settingsBuilder =
                    Settings.builder()
                            .put(settingKey, settingValue);
            request.settings(settingsBuilder);

//方式二
//            request.settings(
//                    "{\"index.number_of_replicas\": \"2\"}"
//                    , XContentType.JSON);
//方式三
//            Map<String, Object> map = new HashMap<>();
//            map.put(settingKey, settingValue);
//            request.settings(map);



            request.setPreserveExisting(false);
            request.timeout(TimeValue.timeValueMillis(2));
            request.timeout("2m");
            request.masterNodeTimeout(TimeValue.timeValueMinutes(1));
            request.masterNodeTimeout("1m");
            request.indicesOptions(IndicesOptions.lenientExpandOpen());

            AcknowledgedResponse updateSettingsResponse = restHighLevelClient.indices().putSettings(request, RequestOptions.DEFAULT);

            boolean acknowledged = updateSettingsResponse.isAcknowledged();
            if(acknowledged){
                System.out.println("索引 updateSettingsResponse 信息  : " + acknowledged);
            }else{
                System.out.println("索引 updateSettingsResponse 信息  : " + acknowledged);
            }
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
