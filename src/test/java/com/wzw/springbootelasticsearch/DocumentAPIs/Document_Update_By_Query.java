package com.wzw.springbootelasticsearch.DocumentAPIs;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.ReindexRequest;
import org.elasticsearch.index.reindex.ScrollableHitSource;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Collections;
import java.util.List;


@SpringBootTest
@Slf4j
public class Document_Update_By_Query {

@Autowired
    RestHighLevelClient restHighLevelClient;


    @Test
    public void testIndex() throws IOException {


        updateByQuery();

    }

    /**
     * Index

     * @throws IOException
     */
    public void updateByQuery() throws IOException {

        try {
            UpdateByQueryRequest request=new UpdateByQueryRequest("source1","source2");
            request.setConflicts("proceed");
            request.setQuery(new TermQueryBuilder("user","kimchy"));
            request.setMaxDocs(10);
            request.setBatchSize(100);
            request.setPipeline("my_pipeline");


//
//            request.setScript(
//                    new Script(
//                            ScriptType.INLINE, "painless",
//                            "if (ctx._source.user == 'kimchy') {ctx._source.likes++;}",
//                            Collections.emptyMap()));

            request.setSlices(2);
            request.setScroll(TimeValue.timeValueMillis(10));
            request.setTimeout(TimeValue.timeValueMillis(2));
            request.setRefresh(true);
            request.setIndicesOptions(IndicesOptions.LENIENT_EXPAND_OPEN);

            BulkByScrollResponse bulkResponse = restHighLevelClient.updateByQuery(request, RequestOptions.DEFAULT);

            TimeValue timeTaken = bulkResponse.getTook();
            boolean timedOut = bulkResponse.isTimedOut();
            long totalDocs = bulkResponse.getTotal();
            long updatedDocs = bulkResponse.getUpdated();
            long deletedDocs = bulkResponse.getDeleted();
            long batches = bulkResponse.getBatches();
            long noops = bulkResponse.getNoops();
            long versionConflicts = bulkResponse.getVersionConflicts();
            long bulkRetries = bulkResponse.getBulkRetries();
            long searchRetries = bulkResponse.getSearchRetries();
            TimeValue throttledMillis = bulkResponse.getStatus().getThrottled();
            TimeValue throttledUntilMillis =
                    bulkResponse.getStatus().getThrottledUntil();
            List<ScrollableHitSource.SearchFailure> searchFailures =
                    bulkResponse.getSearchFailures();
            List<BulkItemResponse.Failure> bulkFailures =
                    bulkResponse.getBulkFailures();




        } catch (ElasticsearchException exception) {
            if (exception.status() == RestStatus.NOT_FOUND) {

            }
        }




    }
}
