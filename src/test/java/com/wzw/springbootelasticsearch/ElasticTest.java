package com.wzw.springbootelasticsearch;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.ClusterClient;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;


@SpringBootTest
@Slf4j
public class ElasticTest {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 测试连接
     */
    @Test
    public void TestJoin() throws IOException {

        ClusterClient cluster = restHighLevelClient.cluster();
        IndicesClient indices = restHighLevelClient.indices();
        log.info("===========测试连接成功===============> {}",indices);

    }


}
