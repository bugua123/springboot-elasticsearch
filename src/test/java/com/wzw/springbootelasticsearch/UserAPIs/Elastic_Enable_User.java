package com.wzw.springbootelasticsearch.UserAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.security.DeleteUserRequest;
import org.elasticsearch.client.security.DeleteUserResponse;
import org.elasticsearch.client.security.EnableUserRequest;
import org.elasticsearch.client.security.RefreshPolicy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
@Slf4j
public class Elastic_Enable_User {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void enableUser() throws IOException {
        EnableUserRequest request=new EnableUserRequest("enable_user", RefreshPolicy.NONE);
        boolean b = restHighLevelClient.security().enableUser(request, RequestOptions.DEFAULT);

    }



    //异步执行
    @Test
    public void enableUser_Async() throws IOException, InterruptedException {



    }




}
