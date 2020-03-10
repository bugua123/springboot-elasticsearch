package com.wzw.springbootelasticsearch.DocumentAPIs;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.TermVectorsRequest;
import org.elasticsearch.client.core.TermVectorsResponse;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootTest
@Slf4j
public class Document_Term_Vectors_Index {

@Autowired
    RestHighLevelClient restHighLevelClient;


    @Test
    public void testIndex() throws IOException {

        String INDEX_TEST="test_out";
        termVectorsIndex(INDEX_TEST);

    }

    /**
     * Index
     * @param index
     * @throws IOException
     */
    public void termVectorsIndex(String index) throws IOException {

        try {
            TermVectorsRequest request=new TermVectorsRequest(index, "24");
            request.setFields("user");

//            XContentBuilder docBuilder = XContentFactory.jsonBuilder();
//            docBuilder.startObject().field("user", "guest-user").endObject();
//            TermVectorsRequest request = new TermVectorsRequest("authors",
//                    docBuilder);


            request.setFieldStatistics(false);
            request.setTermStatistics(true);
            request.setPositions(false);
            request.setOffsets(false);
            request.setPayloads(false);

            Map<String, Integer> filterSettings = new HashMap<>();
            filterSettings.put("max_num_terms", 3);
            filterSettings.put("min_term_freq", 1);
            filterSettings.put("max_term_freq", 10);
            filterSettings.put("min_doc_freq", 1);
            filterSettings.put("max_doc_freq", 100);
            filterSettings.put("min_word_length", 1);
            filterSettings.put("max_word_length", 10);

            request.setFilterSettings(filterSettings);

            Map<String, String> perFieldAnalyzer = new HashMap<>();
            perFieldAnalyzer.put("user", "keyword");
            request.setPerFieldAnalyzer(perFieldAnalyzer);

            request.setRealtime(false);
            request.setRouting("routing");

            TermVectorsResponse termVectorsResponse =
                    restHighLevelClient.termvectors(request, RequestOptions.DEFAULT);
            System.out.println("termVectorsResponse:"+termVectorsResponse);

            String index_ = termVectorsResponse.getIndex();
            String id_ = termVectorsResponse.getId();
            boolean found_ = termVectorsResponse.getFound();



            for (TermVectorsResponse.TermVector tv : termVectorsResponse.getTermVectorsList()) {
                String fieldname = tv.getFieldName();
                int docCount = tv.getFieldStatistics().getDocCount();
                long sumTotalTermFreq =
                        tv.getFieldStatistics().getSumTotalTermFreq();
                long sumDocFreq = tv.getFieldStatistics().getSumDocFreq();
                if (tv.getTerms() != null) {
                    List<TermVectorsResponse.TermVector.Term> terms =
                            tv.getTerms();
                    for (TermVectorsResponse.TermVector.Term term : terms) {
                        String termStr = term.getTerm();
                        int termFreq = term.getTermFreq();
                        int docFreq = term.getDocFreq();
                        long totalTermFreq = term.getTotalTermFreq();
                        float score = term.getScore();
                        if (term.getTokens() != null) {
                            List<TermVectorsResponse.TermVector.Token> tokens =
                                    term.getTokens();
                            for (TermVectorsResponse.TermVector.Token token : tokens) {
                                int position = token.getPosition();
                                int startOffset = token.getStartOffset();
                                int endOffset = token.getEndOffset();
                                String payload = token.getPayload();
                            }
                        }
                    }
                }
            }

        } catch (ElasticsearchException exception) {
            if (exception.status() == RestStatus.NOT_FOUND) {

            }
        }
    }
}
