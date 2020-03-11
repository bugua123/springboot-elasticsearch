package com.wzw.springbootelasticsearch.SearchAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.fieldcaps.FieldCapabilities;
import org.elasticsearch.action.fieldcaps.FieldCapabilitiesRequest;
import org.elasticsearch.action.fieldcaps.FieldCapabilitiesResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.MultiSearchTemplateRequest;
import org.elasticsearch.script.mustache.MultiSearchTemplateResponse;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@Slf4j
public class Elastic_Field_Capabilities {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    @Test
    public void fieldCapabilities() throws IOException {
        FieldCapabilitiesRequest request=new FieldCapabilitiesRequest();
        request.fields("user");
        request.indices("posts","authors","contributors");

        request.indicesOptions(IndicesOptions.lenientExpandOpen());


        FieldCapabilitiesResponse fieldCapabilitiesResponse = restHighLevelClient.fieldCaps(request, RequestOptions.DEFAULT);

        Map<String, FieldCapabilities> userResponse=fieldCapabilitiesResponse.getField("user");
        FieldCapabilities textCapabilities=userResponse.get("keyword");

        boolean isSearchable=textCapabilities.isSearchable();
        boolean isAggregatable = textCapabilities.isAggregatable();

        String[] indices=textCapabilities.indices();
        String[] nonSearchableIndices=textCapabilities.nonSearchableIndices();
        String[] nonAggrregatableIndices=textCapabilities.nonAggregatableIndices();

        System.out.println("isSearchable:"+isSearchable);
        System.out.println("isAggregatable:"+isAggregatable);



    }



}


