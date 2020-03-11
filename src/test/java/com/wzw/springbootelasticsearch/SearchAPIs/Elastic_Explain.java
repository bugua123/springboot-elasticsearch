package com.wzw.springbootelasticsearch.SearchAPIs;


import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.Explanation;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.explain.ExplainRequest;
import org.elasticsearch.action.explain.ExplainResponse;
import org.elasticsearch.client.Cancellable;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.index.get.GetResult;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.rankeval.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sound.midi.SoundbankResource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Slf4j
public class Elastic_Explain {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void explain() throws IOException {
        ExplainRequest request=new ExplainRequest("contributors","1");
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("user", "tanguy");
        request.query(termQueryBuilder);

        request.routing("routing");

        request.preference("_local");

        request.fetchSourceContext(new FetchSourceContext(true,new String[]{"user"},null));

        request.storedFields(new String[]{"user"});

        ExplainResponse response = restHighLevelClient.explain(request, RequestOptions.DEFAULT);

        String index = response.getIndex();
        String id = response.getId();
        boolean exists = response.isExists();
        boolean match = response.isMatch();
        boolean hasExplanation = response.hasExplanation();
        Explanation explanation = response.getExplanation();
        GetResult getResult = response.getGetResult();


        Map<String, Object> source = getResult.getSource();
        Map<String, DocumentField> fields = getResult.getFields();

    }

    //异步执行
    @Test
    public void explain_Async() throws IOException {

        ExplainRequest request=new ExplainRequest("contributors","1");
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("user", "tanguy");
        request.query(termQueryBuilder);

        request.routing("routing");

        request.preference("_local");

        request.fetchSourceContext(new FetchSourceContext(true,new String[]{"user"},null));

        request.storedFields(new String[]{"user"});

        ActionListener<ExplainResponse> listener=new ActionListener<ExplainResponse>() {
            @Override
            public void onResponse(ExplainResponse explainResponse) {
                String index = explainResponse.getIndex();
                String id = explainResponse.getId();
                boolean exists = explainResponse.isExists();
                boolean match = explainResponse.isMatch();
                boolean hasExplanation = explainResponse.hasExplanation();
                Explanation explanation = explainResponse.getExplanation();
                GetResult getResult = explainResponse.getGetResult();
                System.out.println("测试输出");
            }

            @Override
            public void onFailure(Exception e) {

            }
        };

        restHighLevelClient.explainAsync(request, RequestOptions.DEFAULT,listener );



    }




}
