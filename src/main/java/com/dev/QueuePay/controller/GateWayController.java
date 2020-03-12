package com.dev.QueuePay.controller;

import com.dev.QueuePay.payload.GateWayAccountRequest;
import com.dev.QueuePay.payload.GateWayCardRequest;
import com.dev.QueuePay.services.GateWayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class GateWayController {
    private final GateWayService gateWayService;

    @Autowired
    public GateWayController(GateWayService gateWayService) {
        this.gateWayService = gateWayService;
    }

    @PostMapping("/bank")
    public ResponseEntity<Object> payViaBankAccount(@RequestHeader(name = "email", defaultValue = "queuepay@gmail.com") String email,
                                                    @RequestHeader(name = "secret-key", defaultValue = "!@#$%^&*()QWERTY") String secretKey,
                                                    @RequestBody GateWayAccountRequest gateWayAccountRequest){
        System.out.println("hi");
        return gateWayService.sendTransactionsByAccount(gateWayAccountRequest, "http://192.168.88.143:8080/api/v1/transaction/account" );
    }

    @PostMapping("/card")
    public ResponseEntity<Object> payViaBankAccount(@RequestHeader(name = "email", defaultValue = "queuepay@gmail.com") String email,
                                                    @RequestHeader(name = "secret-key", defaultValue = "!@#$%^&*()QWERTY") String secretKey,
                                                    @RequestBody GateWayCardRequest gateWayCardRequest){
        System.out.println("hi");
        return gateWayService.sendTransactionsByCard(gateWayCardRequest, "http://192.168.88.143:8080/api/v1/transaction/card" );
    }
}
