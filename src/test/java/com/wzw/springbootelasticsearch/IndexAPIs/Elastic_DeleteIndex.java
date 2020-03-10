package com.wzw.springbootelasticsearch.IndexAPIs;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;


/**
 * 1、判断索引是否存在
 * 如果存在，删除索引
 */
@SpringBootTest
@Slf4j
public class Elastic_DeleteIndex {

@Autowired
    RestHighLevelClient restHighLevelClient;


    @Test
    public void testIndex() throws IOException {
        // 判断是否存在索引
        String INDEX_TEST="goods_test";
        if (!existsIndex(INDEX_TEST)) {
            System.out.println("索引不存在 ");
        }else {
            System.out.println("索引存在即将删除");
            deleteIndex(INDEX_TEST);

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
     * 删除索引
     * @param index
     * @throws IOException
     */
    public void deleteIndex(String index) throws IOException {

        DeleteIndexRequest request=new DeleteIndexRequest(index);
        request.timeout(TimeValue.timeValueMillis(2));
        AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println("删除索引信息: " + JSON.toJSONString(acknowledgedResponse));
    }
}
