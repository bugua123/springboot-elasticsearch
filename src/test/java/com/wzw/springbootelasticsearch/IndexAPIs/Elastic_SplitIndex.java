package com.wzw.springbootelasticsearch.IndexAPIs;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.shrink.ResizeRequest;
import org.elasticsearch.action.admin.indices.shrink.ResizeResponse;
import org.elasticsearch.action.admin.indices.shrink.ResizeType;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;


/**
 * 1、判断索引是否存在
 *    如果存在，拆分索引
 *    注意：  拆分索引时，必须为只读
 *
 *    PUT /goods/_settings
 * {
 *
 *   "settings": {
 *     "index.blocks.write": true
 *
 *   }
 *
 * }
 */
@SpringBootTest
@Slf4j
public class Elastic_SplitIndex {

@Autowired
    RestHighLevelClient restHighLevelClient;


    @Test
    public void testIndex() throws IOException {
        // 判断是否存在索引
        String INDEX_TEST="goods";
        if (!existsIndex(INDEX_TEST)) {
            System.out.println("索引不存在 ");
        }else {
            System.out.println("索引存在拆分索引");

            splitIndex(INDEX_TEST);

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
     * 收缩索引
     * @param index
     * @throws IOException
     */
    public void splitIndex(String index) throws IOException {

        ResizeRequest request=new ResizeRequest("target_index_1",index );
        /**
         * 设置参数
         */
        request.setResizeType(ResizeType.SPLIT);

//        request.timeout(TimeValue.timeValueMillis(2));
//        request.timeout("2m");
//        request.masterNodeTimeout(TimeValue.timeValueMillis(1));
//        request.masterNodeTimeout("1m");
//        request.setWaitForActiveShards(2);
//        request.setWaitForActiveShards(ActiveShardCount.DEFAULT);
        request.getTargetIndexRequest().settings(Settings.builder()
        .put("index.number_of_shards",4)
        );
        request.getTargetIndexRequest().alias(new Alias("target_alias"));

        //执行
        ResizeResponse resizeResponse = restHighLevelClient.indices().split(request, RequestOptions.DEFAULT);

        System.out.println("索引拆分信息: " + JSON.toJSONString(resizeResponse));
    }
}
