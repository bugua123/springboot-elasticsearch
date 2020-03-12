package com.wzw.springbootelasticsearch.SnapshotAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.cluster.repositories.get.GetRepositoriesRequest;
import org.elasticsearch.action.admin.cluster.repositories.get.GetRepositoriesResponse;
import org.elasticsearch.action.admin.cluster.repositories.put.PutRepositoryRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.metadata.RepositoryMetaData;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.repositories.fs.FsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Slf4j
public class Elastic_Snapshot_Create_Repository {
    @Autowired
    private RestHighLevelClient restHighLevelClient;


    /**
     * 暂时有问题 为建立成功
     * @throws IOException
     */

    //同步执行
    @Test
    public void createRepository() throws IOException {

        PutRepositoryRequest request=new PutRepositoryRequest();


        String locationKey = FsRepository.LOCATION_SETTING.getKey();
        String locationValue = ".";
        String compressKey = FsRepository.COMPRESS_SETTING.getKey();
        boolean compressValue = true;

        //方式一
        Settings settings = Settings.builder()
                .put(locationKey, locationValue)
                .put(compressKey, compressValue)
                .build();
        request.settings(settings);

//方式二
//        Settings.Builder settingsBuilder = Settings.builder()
//                .put(locationKey, locationValue)
//                .put(compressKey, compressValue);
//        request.settings(settingsBuilder);

//方式三
//        request.settings("{\"location\": \".\", \"compress\": \"true\"}",
//                XContentType.JSON);

//方式四
//        Map<String, Object> map = new HashMap<>();
//        map.put(locationKey, locationValue);
//        map.put(compressKey, compressValue);
//        request.settings(map);


        String repositoryName="repositoryName";
        request.name(repositoryName);
        request.type(FsRepository.TYPE);
        request.timeout("1m");
        request.masterNodeTimeout("1m");
        request.verify(true);

        AcknowledgedResponse response = restHighLevelClient.snapshot().createRepository(request, RequestOptions.DEFAULT);
        boolean acknowledged = response.isAcknowledged();
    }

    //异步执行
    @Test
    public void createRepository_Async() throws IOException, InterruptedException {

        PutRepositoryRequest request=new PutRepositoryRequest();


        String locationKey = FsRepository.LOCATION_SETTING.getKey();
        String locationValue = ".";
        String compressKey = FsRepository.COMPRESS_SETTING.getKey();
        boolean compressValue = true;

        //方式一
        Settings settings = Settings.builder()
                .put(locationKey, locationValue)
                .put(compressKey, compressValue)
                .build();
        request.settings(settings);




        String repositoryName="repositoryName";
        request.name(repositoryName);
        request.type(FsRepository.TYPE);
        request.timeout("1m");
        request.masterNodeTimeout("1m");
        request.verify(true);


        ActionListener<AcknowledgedResponse> listener=new ActionListener<AcknowledgedResponse>() {
            @Override
            public void onResponse(AcknowledgedResponse acknowledgedResponse) {
                boolean acknowledged = acknowledgedResponse.isAcknowledged();
            }

            @Override
            public void onFailure(Exception e) {

            }
        };
        restHighLevelClient.snapshot().createRepositoryAsync(request, RequestOptions.DEFAULT,listener );
    }




}
