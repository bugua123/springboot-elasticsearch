package com.wzw.springbootelasticsearch.SearchAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
public class Elastic_Clear_Scroll {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    @Test
    public void clearScroll() throws IOException {
        ClearScrollRequest request=new ClearScrollRequest();
        String scrollId="";
        //单个scrollId
        request.addScrollId(scrollId);

        //多个scrollIds
        List<String> scrollIds=new ArrayList<>();
        request.setScrollIds(scrollIds);

        ClearScrollResponse clearScrollResponse = restHighLevelClient.clearScroll(request, RequestOptions.DEFAULT);

        boolean success = clearScrollResponse.isSucceeded();
        int released = clearScrollResponse.getNumFreed();
    }





}
