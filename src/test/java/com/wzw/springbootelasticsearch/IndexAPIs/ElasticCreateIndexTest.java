package com.wzw.springbootelasticsearch.IndexAPIs;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
@SpringBootTest
@Slf4j
public class ElasticCreateIndexTest {

    /**
     * 基于配置文件的创建索引，暂时有问题 没进行测试
     */
    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Test
    public void init() throws Exception {
        this.createIndex("commodity");
    }



    /**
     * 创建索引
     * @param index
     * @throws IOException
     */

    public void createIndex(String index) throws IOException {
        //如果存在就不创建了
//        if(this.existsIndex(index)) {
//            System.out.println(index+"索引库已经存在!");
//            return;
//        }
        // 开始创建库
        CreateIndexRequest request = new CreateIndexRequest(index);
        //配置文件
        ClassPathResource seResource = new ClassPathResource("mapper/setting.json");
        InputStream seInputStream = seResource.getInputStream();
        String seJson = String.join("\n", IOUtils.readLines(seInputStream,"UTF-8"));
        seInputStream.close();
        //映射文件
        ClassPathResource mpResource = new ClassPathResource("mapper/"+index+"-mapping.json");
        InputStream mpInputStream = mpResource.getInputStream();
        String mpJson = String.join("\n",IOUtils.readLines(mpInputStream,"UTF-8"));
        mpInputStream.close();

        request.settings(seJson, XContentType.JSON);
        request.mapping(mpJson, XContentType.JSON);

        //设置别名
        request.alias(new Alias(index+"_alias"));
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        boolean falg = createIndexResponse.isAcknowledged();
        if(falg){
            System.out.println("创建索引库:"+index+"成功！" );
        }
    }
}
