package com.dev.QueuePay.controller;

import com.dev.QueuePay.payload.GateWayAccountRequest;
import com.dev.QueuePay.payload.GateWayCardRequest;
import com.dev.QueuePay.services.GateWayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/payments")
public class GateWayController {
    private final GateWayService gateWayService;

    @Autowired
    public GateWayController(GateWayService gateWayService) {
        this.gateWayService = gateWayService;
    }

    @PostMapping("/bank")
    public ResponseEntity<?> payViaBankAccount(
            @RequestBody HashMap<String, String> gateWayAccountRequest){
        System.out.println("hi");
        ResponseEntity<?> response;
        try{
            response = gateWayService
                    .sendTransactionsByAccount(gateWayAccountRequest, "http://192.168.88.143:8080/api/v1/transaction/account" );
        } catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("resend otp");
            response = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("horrible");
        }
        return response;
    }

    @PostMapping("/card")
    public ResponseEntity<?> payViaBankCard(@RequestBody HashMap<String, String> gateWayCardRequest){
        System.out.println("ho");
        ResponseEntity<?> response;
        try{
            response = gateWayService
                    .sendTransactionsByAccount(gateWayCardRequest, "http://192.168.88.143:8080/api/v1/transaction/card" );
        } catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("resend your token");
            response = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("horrible");
        }
        return response;
    }

    @PostMapping("/complete/token")
    public ResponseEntity<?> confirmTokenAndCompleteTransaction(@RequestBody HashMap<String, String> gateWayCardRequest){
        System.out.println("ho");
        ResponseEntity<?> response;
        try{
            response = gateWayService
                    .completeTransactionWithOtp(gateWayCardRequest, "http://192.168.88.143:8080/api/v1/transaction/token" );
        } catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("resend your token");
            response = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("horrible");
        }
        return response;
    }
}
