package com.wzw.springbootelasticsearch.DocumentAPIs;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
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
import java.util.Map;


@SpringBootTest
@Slf4j
public class Document_Update_Index {

@Autowired
    RestHighLevelClient restHighLevelClient;


    @Test
    public void testIndex() throws IOException {

        String INDEX_TEST="goods_1";
        updateIndex(INDEX_TEST);

    }

    /**
     * Index
     * @param index
     * @throws IOException
     */
    public void updateIndex(String index) throws IOException {

        try {
            UpdateRequest request=new UpdateRequest(
                    index,"bvrIU2kfSAWKQHycnLZG1w"
            );

            //方式一 Updates with a Script
            Map<String,Object> parameters= Collections.singletonMap("count",4);
            Script inline=new Script(ScriptType.INLINE,"painless",
                    "ctx._source.field+=params.count",parameters);

            request.script(inline);

          // Or as a stored script:
            Script stored = new Script(
                    ScriptType.STORED, null, "increment-field", parameters);
            request.script(stored);

//          //Updates with a partial document
//
//            UpdateRequest request = new UpdateRequest("posts", "1");
//            String jsonString = "{" +
//                    "\"updated\":\"2017-01-01\"," +
//                    "\"reason\":\"daily update\"" +
//                    "}";
//            request.doc(jsonString, XContentType.JSON);
//
//            Map<String, Object> jsonMap = new HashMap<>();
//            jsonMap.put("updated", new Date());
//            jsonMap.put("reason", "daily update");
//            UpdateRequest request = new UpdateRequest("posts", "1")
//                    .doc(jsonMap);
//
//            XContentBuilder builder = XContentFactory.jsonBuilder();
//            builder.startObject();
//            {
//                builder.timeField("updated", new Date());
//                builder.field("reason", "daily update");
//            }
//            builder.endObject();
//            UpdateRequest request = new UpdateRequest("posts", "1")
//                    .doc(builder);

//
//            UpdateRequest request = new UpdateRequest("posts", "1")
//                    .doc("updated", new Date(),
//                            "reason", "daily update");


            String[] includes = new String[]{"updated", "r*"};
            String[] excludes = Strings.EMPTY_ARRAY;
            request.fetchSource(
                    new FetchSourceContext(true, includes, excludes));

            UpdateResponse updateResponse = restHighLevelClient.update(
                    request, RequestOptions.DEFAULT);

            System.out.println("updateResponse:"+updateResponse);

        } catch (ElasticsearchException exception) {
            if (exception.status() == RestStatus.NOT_FOUND) {

            }
        }




    }
}
