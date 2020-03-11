package com.wzw.springbootelasticsearch.SearchAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.ml.EvaluateDataFrameRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.rankeval.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Slf4j
public class Elastic_Ranking_Evaluation {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void rankingEvaluation() throws IOException {

        EvaluationMetric evaluationMetric=new PrecisionAtK();
        ArrayList<RatedDocument> ratedDocuments = new ArrayList<>();
        ratedDocuments.add(new RatedDocument("posts", "1", 1));
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("user", "kimchy"));
        RatedRequest ratedRequest=new RatedRequest("kimchy_query",ratedDocuments ,searchSourceBuilder );

        List<RatedRequest> ratedRequests= Arrays.asList(ratedRequest);
        RankEvalSpec rankEvalSpec=new RankEvalSpec(ratedRequests,evaluationMetric);

        RankEvalRequest rankEvalRequest=new RankEvalRequest(rankEvalSpec,new String[]{"posts"} );


        RankEvalResponse rankEvalResponse = restHighLevelClient.rankEval(rankEvalRequest, RequestOptions.DEFAULT);

        //返回值

        double evaluationResult=rankEvalResponse.getMetricScore();

        Map<String,EvalQueryQuality> partialResults=rankEvalResponse.getPartialResults();

        EvalQueryQuality evalQueryQuality=partialResults.get("kimchy_query");

        double qualityLevel=evalQueryQuality.metricScore();

        List<RatedSearchHit> hitsAndRatings=evalQueryQuality.getHitsAndRatings();

        RatedSearchHit ratedSearchHit=hitsAndRatings.get(2);

        MetricDetail metricDetails=evalQueryQuality.getMetricDetails();

        String metricName=metricDetails.getMetricName();

        PrecisionAtK.Detail detail= (PrecisionAtK.Detail) metricDetails;


    }

    //异步执行
    @Test
    public void rankingEvaluation_Async() throws IOException {

        EvaluationMetric evaluationMetric=new PrecisionAtK();
        ArrayList<RatedDocument> ratedDocuments = new ArrayList<>();
        ratedDocuments.add(new RatedDocument("posts", "1", 1));
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("user", "kimchy"));
        RatedRequest ratedRequest=new RatedRequest("kimchy_query",ratedDocuments ,searchSourceBuilder );

        List<RatedRequest> ratedRequests= Arrays.asList(ratedRequest);
        RankEvalSpec rankEvalSpec=new RankEvalSpec(ratedRequests,evaluationMetric);

        RankEvalRequest rankEvalRequest=new RankEvalRequest(rankEvalSpec,new String[]{"posts"} );


        ActionListener<RankEvalResponse> listener=new ActionListener<RankEvalResponse>() {
            @Override
            public void onResponse(RankEvalResponse rankEvalResponse) {
                    //返回值

                    double evaluationResult=rankEvalResponse.getMetricScore();

                    Map<String,EvalQueryQuality> partialResults=rankEvalResponse.getPartialResults();

                    EvalQueryQuality evalQueryQuality=partialResults.get("kimchy_query");

                    double qualityLevel=evalQueryQuality.metricScore();

                    List<RatedSearchHit> hitsAndRatings=evalQueryQuality.getHitsAndRatings();

                    RatedSearchHit ratedSearchHit=hitsAndRatings.get(2);

                    MetricDetail metricDetails=evalQueryQuality.getMetricDetails();

                    String metricName=metricDetails.getMetricName();

                    PrecisionAtK.Detail detail= (PrecisionAtK.Detail) metricDetails;
            }

            @Override
            public void onFailure(Exception e) {

            }
        };
        restHighLevelClient.rankEvalAsync(rankEvalRequest, RequestOptions.DEFAULT,listener);




    }




}
