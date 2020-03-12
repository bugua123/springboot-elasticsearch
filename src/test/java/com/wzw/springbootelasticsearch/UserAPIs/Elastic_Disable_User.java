package com.wzw.springbootelasticsearch.UserAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.security.DisableUserRequest;
import org.elasticsearch.client.security.EnableUserRequest;
import org.elasticsearch.client.security.RefreshPolicy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
@Slf4j
public class Elastic_Disable_User {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void disableUser() throws IOException {
        DisableUserRequest request=new DisableUserRequest("disable_user",RefreshPolicy.NONE );
        boolean b = restHighLevelClient.security().disableUser(request, RequestOptions.DEFAULT);

    }



    //异步执行
    @Test
    public void disableUser_Async() throws IOException, InterruptedException {



    }




}
