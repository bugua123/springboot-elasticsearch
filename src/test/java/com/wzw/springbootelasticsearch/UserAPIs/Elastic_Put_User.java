package com.wzw.springbootelasticsearch.UserAPIs;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.TaskOperationFailure;
import org.elasticsearch.action.admin.cluster.node.tasks.cancel.CancelTasksRequest;
import org.elasticsearch.action.admin.cluster.node.tasks.cancel.CancelTasksResponse;
import org.elasticsearch.action.admin.cluster.node.tasks.list.TaskGroup;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.security.PutUserRequest;
import org.elasticsearch.client.security.PutUserResponse;
import org.elasticsearch.client.security.RefreshPolicy;
import org.elasticsearch.client.security.user.User;
import org.elasticsearch.tasks.TaskId;
import org.elasticsearch.tasks.TaskInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Slf4j
public class Elastic_Put_User {
    @Autowired
    private RestHighLevelClient restHighLevelClient;


    /**
     * Create or Update User with a Password
     *
     * @throws IOException
     */

    //同步执行
    @Test
    public void putUser() throws IOException {

        char[] password=new char[]{'p', 'a', 's', 's', 'w', 'o', 'r', 'd'};
        User user= new User("example", Collections.singletonList("superuser"));
        PutUserRequest request=PutUserRequest.withPassword(user,password ,true , RefreshPolicy.NONE);

        PutUserResponse putUserResponse = restHighLevelClient.security().putUser(request, RequestOptions.DEFAULT);
        boolean created = putUserResponse.isCreated();

    }

    /**
     *Create or Update User with a Hashed Password
     *
     * @throws IOException
     */

    //同步执行
    @Test
    public void putUser2() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        char[] password=new char[]{'p', 'a', 's', 's', 'w', 'o', 'r', 'd'};
        User user= new User("example", Collections.singletonList("superuser"));
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2withHMACSHA512");

        byte[] salt=null;
        PBEKeySpec keySpec = new PBEKeySpec(password, salt, 10000, 256);
        final byte[] pbkdfEncoded = secretKeyFactory.generateSecret(keySpec).getEncoded();
        char[] passwordHash = ("{PBKDF2}10000$" + Base64.getEncoder().encodeToString(salt)
                + "$" + Base64.getEncoder().encodeToString(pbkdfEncoded)).toCharArray();
        PutUserRequest request = PutUserRequest.withPasswordHash(user, passwordHash, true, RefreshPolicy.NONE);
        PutUserResponse putUserResponse = restHighLevelClient.security().putUser(request, RequestOptions.DEFAULT);
        boolean created = putUserResponse.isCreated();

    }

    //异步执行
    @Test
    public void putUser_Async() throws IOException, InterruptedException {



    }




}
