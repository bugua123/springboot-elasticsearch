package com.wzw.springbootelasticsearch.IndexAPIs;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.refresh.RefreshRequest;
import org.elasticsearch.action.admin.indices.shrink.ResizeRequest;
import org.elasticsearch.action.admin.indices.shrink.ResizeResponse;
import org.elasticsearch.action.admin.indices.shrink.ResizeType;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;


/**
 * 1、判断索引是否存在
 *    如果存在，Refresh索引
 *
 *
 */
@SpringBootTest
@Slf4j
public class Elastic_RefreshIndex {

@Autowired
    RestHighLevelClient restHighLevelClient;


    @Test
    public void testIndex() throws IOException {
        // 判断是否存在索引
        String INDEX_TEST="goods";
        if (!existsIndex(INDEX_TEST)) {
            System.out.println("索引不存在 ");
        }else {
            System.out.println("索引存在Refresh索引");

            refreshIndex(INDEX_TEST);

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
     *  Refresh索引
     * @param index
     * @throws IOException
     */
    public void refreshIndex(String index) throws IOException {

        //单个索引
        RefreshRequest request=new RefreshRequest(index);
//        //多个索引
//        RefreshRequest requestMultiple=new RefreshRequest("index1","index2");
//        //所有索引
//        RefreshRequest requestAll=new RefreshRequest();

        request.indicesOptions(IndicesOptions.lenientExpandOpen());
        System.out.println("索引 Refresh信息: " + JSON.toJSONString(request));
    }
}
