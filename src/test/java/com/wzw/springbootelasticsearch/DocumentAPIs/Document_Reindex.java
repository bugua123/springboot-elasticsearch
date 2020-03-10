package com.wzw.springbootelasticsearch.DocumentAPIs;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.bytes.BytesArray;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.ReindexRequest;
import org.elasticsearch.index.reindex.RemoteInfo;
import org.elasticsearch.index.reindex.ScrollableHitSource;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@SpringBootTest
@Slf4j
public class Document_Reindex {

@Autowired
    RestHighLevelClient restHighLevelClient;


    @Test
    public void testIndex() throws IOException {

        String INDEX_TEST="goods_1";
        reIndex(INDEX_TEST);

    }

    /**
     * Index
     * @param index
     * @throws IOException
     */
    public void reIndex(String index) throws IOException {

        try {
            ReindexRequest request=new ReindexRequest();
            request.setSourceIndices("source1","source2");
            request.setDestIndex("dest");

            request.setDestOpType("create");
            request.setSourceQuery(new TermQueryBuilder("user","kimchy"));
            request.setMaxDocs(10);
            request.setSourceBatchSize(100);
            request.setDestPipeline("my_pipeline");
            request.addSortField("field1", SortOrder.DESC);
            request.addSortField("field2", SortOrder.ASC);


//            request.setScript(
//                    new Script(
//                            ScriptType.INLINE, "painless",
//                            "if (ctx._source.user == 'kimchy') {ctx._source.likes++;}",
//                            Collections.emptyMap()));


//            request.setRemoteInfo(
//                    new RemoteInfo(
//                            "http", remoteHost, remotePort, null,
//                            new BytesArray(new MatchAllQueryBuilder().toString()),
//                            user, password, Collections.emptyMap(),
//                            new TimeValue(100, TimeUnit.MILLISECONDS),
//                            new TimeValue(100, TimeUnit.SECONDS)
//                    )
//            );

            BulkByScrollResponse bulkResponse = restHighLevelClient.reindex(request, RequestOptions.DEFAULT);


            //输出
            TimeValue timeTaken = bulkResponse.getTook();
            boolean timedOut = bulkResponse.isTimedOut();
            long totalDocs = bulkResponse.getTotal();
            long updatedDocs = bulkResponse.getUpdated();
            long createdDocs = bulkResponse.getCreated();
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
