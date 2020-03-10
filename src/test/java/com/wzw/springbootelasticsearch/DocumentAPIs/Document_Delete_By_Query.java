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
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.ScrollableHitSource;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.rest.RestStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;


@SpringBootTest
@Slf4j
public class Document_Delete_By_Query {

@Autowired
    RestHighLevelClient restHighLevelClient;


    @Test
    public void testIndex() throws IOException {
        deleteByQuery();
    }

    /**
     * Index

     * @throws IOException
     */
    public void deleteByQuery() throws IOException {

        try {
            DeleteByQueryRequest request=new DeleteByQueryRequest("source1","source2");


            request.setConflicts("proceed");
            request.setQuery(new TermQueryBuilder("user","kimchy"));
            request.setMaxDocs(10);
            request.setBatchSize(100);
            request.setSlices(2);
            request.setScroll(TimeValue.timeValueMinutes(10));
            request.setRouting("=cat");
            request.setTimeout(TimeValue.timeValueMinutes(2));
            request.setRefresh(true);
            request.setIndicesOptions(IndicesOptions.LENIENT_EXPAND_OPEN);

            BulkByScrollResponse bulkResponse = restHighLevelClient.deleteByQuery(request, RequestOptions.DEFAULT);

            TimeValue timeTaken = bulkResponse.getTook();
            boolean timedOut = bulkResponse.isTimedOut();
            long totalDocs = bulkResponse.getTotal();
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
