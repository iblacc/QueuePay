package com.dev.QueuePay.controller;

import com.dev.QueuePay.payload.TransactionRequest;
import com.dev.QueuePay.services.CustomerTransactionService;
import com.dev.QueuePay.user.models.CustomerTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/transactions")
public class CustomerTransactionController {
    private final CustomerTransactionService customerTransactionService;

    @Autowired
    public CustomerTransactionController(CustomerTransactionService customerTransactionService) {
        this.customerTransactionService = customerTransactionService;
    }

    @GetMapping(path = "/{merchantId}/")
    public ResponseEntity<List<CustomerTransaction>> getAllCustomersTransactions(@PathVariable(value = "merchantId") Long merchantId) {
        List<CustomerTransaction> response = customerTransactionService.getAllTransactions(merchantId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/{merchantId}/{id}")
    public ResponseEntity<CustomerTransaction> getTransaction(@PathVariable(value = "merchantId") Long merchantId, @PathVariable(name = "id") Long id) {
        CustomerTransaction customerTransaction = customerTransactionService.getTransaction(merchantId, id);

        return new ResponseEntity<>(customerTransaction, HttpStatus.OK);
    }


    @PostMapping(path = "/{merchantId}/addTransaction")
    public ResponseEntity<CustomerTransaction> addTransaction(@PathVariable(value = "merchantId") Long merchantId, @Valid @RequestBody TransactionRequest transactionRequest) {
        CustomerTransaction newTransaction = customerTransactionService.setTransaction(merchantId, transactionRequest);

        return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
    }


}
