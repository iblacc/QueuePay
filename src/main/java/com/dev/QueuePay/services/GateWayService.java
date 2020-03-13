package com.dev.QueuePay.services;

import com.dev.QueuePay.payload.GateWayAccountRequest;
import com.dev.QueuePay.payload.GateWayCardRequest;
import com.dev.QueuePay.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;

@Service
public class GateWayService {
    private UserRepository userRepository;

    private RestTemplate restTemplate;

    @Autowired
    public GateWayService(UserRepository userRepository, RestTemplate restTemplate){
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<?> sendTransactionsByCard(HashMap<String, String> gateWayAccountRequest, String url){
        return consumeApi(gateWayAccountRequest, url);
    }
    public ResponseEntity<?> sendTransactionsByAccount(
            HashMap<String, String> gateWayAccountRequest, String url
        ){
        //String message = "transaction completed";
      return consumeApi(gateWayAccountRequest, url);

    }
    public ResponseEntity<?> completeTransactionWithOtp(HashMap<String,String> request, String url){
        return consumeApi(request, url);
    }
    private ResponseEntity<?> consumeApi( HashMap<String, String> gateWayAccountRequest,
                                          String url){
        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("email",
                "victoronushaibu@gmail.com");
        headers.set("secret-key",
                "$2a$11$QQX0ZsDeTPpKv/xvZnzPlOdylcGHOBtc2XF87arNwTv6DqGYEVT.O");
        HttpEntity<HashMap<String, String>> entity = new HttpEntity<>(gateWayAccountRequest, headers);
        return  restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
    }
}
