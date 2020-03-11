package com.dev.QueuePay.services;

import com.dev.QueuePay.payload.GateWayAccountRequest;
import com.dev.QueuePay.payload.GateWayCardRequest;
import com.dev.QueuePay.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class GateWayService {
    private UserRepository userRepository;

    private RestTemplate restTemplate;

    @Autowired
    public GateWayService(UserRepository userRepository, RestTemplate restTemplate){
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<Object> sendTransactionsByCard(GateWayCardRequest gateWayCardRequest, String url){
        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<GateWayCardRequest> entity = new HttpEntity<>(gateWayCardRequest, headers);
         restTemplate.exchange(
                url, HttpMethod.POST, entity, Object.class
        );
         String message = "transaction completed";
         return ResponseEntity.ok().body(message);
    }
    public ResponseEntity<Object> sendTransactionsByAccount(GateWayAccountRequest gateWayAccountRequest, String url){
        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<GateWayAccountRequest> entity = new HttpEntity<>(gateWayAccountRequest, headers);
         restTemplate.exchange(
                url, HttpMethod.POST, entity, Object.class
        );
        String message = "transaction completed";
        return ResponseEntity.ok().body(message);
    }
}
