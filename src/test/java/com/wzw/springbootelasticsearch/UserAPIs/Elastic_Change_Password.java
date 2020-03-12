package com.wzw.springbootelasticsearch.UserAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.security.ChangePasswordRequest;
import org.elasticsearch.client.security.EnableUserRequest;
import org.elasticsearch.client.security.RefreshPolicy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
@Slf4j
public class Elastic_Change_Password {
    @Autowired
    private RestHighLevelClient restHighLevelClient;




    //同步执行
    @Test
    public void changePassword() throws IOException {

        char[] newPassword=new char[]{'p', 'a', 's', 's', 'w', 'o', 'r', 'd'};
        ChangePasswordRequest request=new ChangePasswordRequest("change_password_user", newPassword, RefreshPolicy.NONE);

    }



    //异步执行
    @Test
    public void changePassword_Async() throws IOException, InterruptedException {



    }




}
