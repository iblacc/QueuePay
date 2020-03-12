package com.dev.QueuePay.services;


import com.dev.QueuePay.exceptions.BadRequestException;
import com.dev.QueuePay.exceptions.ResourceNotFoundException;
import com.dev.QueuePay.payload.TransactionRequest;
import com.dev.QueuePay.user.models.CustomerTransaction;
import com.dev.QueuePay.user.models.user.User;
import com.dev.QueuePay.user.repositories.CustomerTransactionRepository;
import com.dev.QueuePay.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerTransactionServiceImpl implements CustomerTransactionService {

    private final UserRepository userRepository;

    private final CustomerTransactionRepository customerTransactionRepository;

    @Autowired
    public CustomerTransactionServiceImpl(UserRepository userRepository, CustomerTransactionRepository customerTransactionRepository) {
        this.userRepository = userRepository;
        this.customerTransactionRepository = customerTransactionRepository;
    }

    @Override
    public List<CustomerTransaction> getAllTransactions(Long merchantId) {
        Optional<User> user = userRepository.findById(merchantId);

        return customerTransactionRepository.findAll();

    }

    @Override
    public CustomerTransaction getTransaction(Long merchantId, Long id) {
        Optional<User> user = userRepository.findById(merchantId);
        return customerTransactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("CustomerTransaction", "ID", id));
    }

    @Override
    public CustomerTransaction setTransaction(Long merchantId, TransactionRequest transactionRequest) {
        Optional<User> user = userRepository.findById(merchantId);
        CustomerTransaction customerTransaction = new CustomerTransaction();

        customerTransaction.setUser(user.get());
        customerTransaction.setAmount(transactionRequest.getAmount());
        customerTransaction.setCustomerName(transactionRequest.getCustomerName());
        customerTransaction.setCardType(transactionRequest.getCardType());
        customerTransaction.setStatus(transactionRequest.getStatus());

        return customerTransactionRepository.save(customerTransaction);

    }


    //    public CustomerTransaction createCustomerTransaction(CustomerTransaction customerTransaction) throws ResourceNotFoundException {
//        return customerTransactionRepository.save(customerTransaction);
//    }
    private void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if (size < 0) {
            throw new BadRequestException("Size number cannot be less than zero.");
        }

        if (size > 30) {
            throw new BadRequestException("Page size must not be greater than 30");
        }
    }
}
