package com.wzw.springbootelasticsearch.UserAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.security.DeleteUserRequest;
import org.elasticsearch.client.security.DeleteUserResponse;
import org.elasticsearch.client.security.GetUsersRequest;
import org.elasticsearch.client.security.GetUsersResponse;
import org.elasticsearch.client.security.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
public class Elastic_Delete_User {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void deleteUser() throws IOException {
        DeleteUserRequest request=new DeleteUserRequest("testUser");
        DeleteUserResponse deleteUserResponse = restHighLevelClient.security().deleteUser(request, RequestOptions.DEFAULT);
        boolean acknowledged = deleteUserResponse.isAcknowledged();

    }



    //异步执行
    @Test
    public void deleteUser_Async() throws IOException, InterruptedException {



    }




}
