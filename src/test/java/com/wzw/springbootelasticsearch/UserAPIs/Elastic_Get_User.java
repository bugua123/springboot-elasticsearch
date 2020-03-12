package com.wzw.springbootelasticsearch.UserAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.security.*;
import org.elasticsearch.client.security.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

@SpringBootTest
@Slf4j
public class Elastic_Get_User {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void getUser() throws IOException {
        String[] usernames=new String[] {"user1","user2"};
        GetUsersRequest request=new GetUsersRequest(usernames[0]);

//        GetUsersRequest request1 = new GetUsersRequest(usernames);
//
//        GetUsersRequest request2 = new GetUsersRequest();


        GetUsersResponse response = restHighLevelClient.security().getUsers(request, RequestOptions.DEFAULT);


        List<User> users = new ArrayList<>();
        users.addAll(response.getUsers());

    }



    //异步执行
    @Test
    public void getUser_Async() throws IOException, InterruptedException {



    }




}
