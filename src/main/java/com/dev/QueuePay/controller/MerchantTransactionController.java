package com.dev.QueuePay.controller;

import com.dev.QueuePay.payload.PayOut;
import com.dev.QueuePay.payload.Performance;
import com.dev.QueuePay.services.MerchantTransactionService;
import com.dev.QueuePay.user.models.MerchantTransaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/merchants")
public class MerchantTransactionController {

    private final MerchantTransactionService mtService;

    public MerchantTransactionController(MerchantTransactionService mtService) {
        this.mtService = mtService;
    }

    @PostMapping(path = "/{merchantId}/payout")
    public ResponseEntity<?> payout(
            @PathVariable(value = "merchantId") Long merchantId,
            @Valid @RequestBody PayOut payOut){
        return new ResponseEntity<>(mtService.payout(merchantId,payOut), HttpStatus.OK);
    }


    @GetMapping(path = "/{merchantId}/performance")
    public ResponseEntity<?> performance(
            @PathVariable(value = "merchantId") Long merchantId) {
        Performance performance = mtService.getPerfomance(merchantId);

        if(performance != null){
            return new ResponseEntity<Performance>(mtService.getPerfomance(merchantId), HttpStatus.OK);
        }
        return  ResponseEntity.badRequest().body("No transaction has been carried ot for this merchant");
    }

    @GetMapping(path = "/{merchantId}")
    public ResponseEntity<List<MerchantTransaction>> getAllTransactions(@PathVariable(value = "merchantId") Long userId){

        return new ResponseEntity<>(mtService.getAllTransactions(userId), HttpStatus.OK);
    }
}
