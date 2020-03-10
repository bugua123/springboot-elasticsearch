package com.wzw.springbootelasticsearch.IndexAPIs;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexTemplatesRequest;
import org.elasticsearch.client.indices.GetIndexTemplatesResponse;
import org.elasticsearch.client.indices.IndexTemplateMetaData;
import org.elasticsearch.client.indices.PutIndexTemplateRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Slf4j
public class Elastic_GetTemplate {

    /**
     * Get Template
     */
    @Autowired
    RestHighLevelClient restHighLevelClient;



    @Test
    public void test() throws IOException {
      String  INDEX_TEST= "t-statistic-out-logstash-odoo";
            getTemplate(INDEX_TEST);



    }

    /**
     * getTemplate 索引
     * @param temp
     * @throws IOException
     */

    public void getTemplate(String temp) throws IOException {
        try {
            GetIndexTemplatesRequest request = new GetIndexTemplatesRequest(temp);
            request = new GetIndexTemplatesRequest("template-1", "template-2");
            request = new GetIndexTemplatesRequest("my-*");
            request.setMasterNodeTimeout(TimeValue.timeValueMinutes(1));
            request.setMasterNodeTimeout("1m");
            GetIndexTemplatesResponse getTemplatesResponse = restHighLevelClient.indices().getIndexTemplate(request, RequestOptions.DEFAULT);

            List<IndexTemplateMetaData> indexTemplates = getTemplatesResponse.getIndexTemplates();
            System.out.println(" indexTemplates: " + JSON.toJSONString(indexTemplates));


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
