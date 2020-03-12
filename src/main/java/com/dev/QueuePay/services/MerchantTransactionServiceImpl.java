package com.dev.QueuePay.services;


import com.dev.QueuePay.exceptions.BadRequestException;
import com.dev.QueuePay.payload.PayOut;
import com.dev.QueuePay.payload.Performance;
import com.dev.QueuePay.user.models.CustomerTransaction;
import com.dev.QueuePay.user.models.MerchantTransaction;
import com.dev.QueuePay.user.models.Status;
import com.dev.QueuePay.user.models.user.User;
import com.dev.QueuePay.user.repositories.CustomerTransactionRepository;
import com.dev.QueuePay.user.repositories.MerchantTransactionRepository;
import com.dev.QueuePay.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MerchantTransactionServiceImpl implements MerchantTransactionService {
    private final MerchantTransactionRepository merchantTransactionRepository;

    private final CustomerTransactionRepository customerTransactionRepository;

    private final UserRepository userRepository;


    @Autowired
    public MerchantTransactionServiceImpl(MerchantTransactionRepository merchantTransactionRepository, CustomerTransactionRepository customerTransactionRepository, UserRepository userRepository) {
        this.merchantTransactionRepository = merchantTransactionRepository;
        this.customerTransactionRepository = customerTransactionRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public MerchantTransaction payout(Long merchantId,PayOut payout) throws BadRequestException {
        Optional<User> user = userRepository.findById(merchantId);

        Double totalBalance = getTotalBalance(merchantId);

//        String password = user.isPresent() ? user.get().getPassword(): "User not found";
//        System.out.println(password);
//        System.out.println(payout.getPassword());
//        if ((!password.equals(payout.getPassword()))) {
//            throw new BadRequestException("Password is incorrect please re-enter password");
//        }
        if (payout.getAmount() > totalBalance) {
            throw new BadRequestException("Insufficient balance");
        }


        Double amount = payout.getAmount();
        System.out.println(amount);
        MerchantTransaction merchantTransaction = new MerchantTransaction();
        merchantTransaction.setUser(user.get());
        merchantTransaction.setAccountNumber(payout.getBankAccountNumber());
        merchantTransaction.setBankName(payout.getBankName());
        merchantTransaction.setAmount(amount);
        merchantTransaction.setStatus(Status.SUCCESSFUL);

        System.out.println(merchantTransaction);
        return merchantTransactionRepository.save(merchantTransaction);

    }


    @Override
    public List<MerchantTransaction> getAllTransactions(Long merchantId) {

        return merchantTransactionRepository.findAllByUserId(merchantId);
    }

    @Override
    public Performance getPerfomance(Long merchantId) {
        List<CustomerTransaction> customerTransactions = customerTransactionRepository.findByUserId(merchantId);
        int volume = 0;
        Double value = 0.0;
        Integer successfulTransaction = 0;
        Integer failedTransaction = 0;
        double totalBalance = getTotalBalance(merchantId);
        Performance performance = new Performance();

        for (CustomerTransaction customerTransaction : customerTransactions) {
            volume++;

            value += customerTransaction.getAmount();
            System.out.println(value);
            if (customerTransaction.getStatus().equals(Status.FAILED)) {
                failedTransaction++;
            } else  if (customerTransaction.getStatus().equals(Status.SUCCESSFUL)) {
                successfulTransaction++;
            }
        }

        performance.setTotalVolume(volume);
        performance.setTotalValue(value);
        performance.setTotalFailedTransaction(failedTransaction);
        performance.setTotalSuccessfulTransaction(successfulTransaction);
        performance.setBalance(totalBalance);

        return performance;
    }


    @Override
    public Double getTotalBalance(Long merchantId) {
        List<CustomerTransaction> merchant = customerTransactionRepository.findByUserId(merchantId);
        List<MerchantTransaction> merchant1 = merchantTransactionRepository.findAllByUserId(merchantId);
        if (merchant.isEmpty()){
            double totalCustomerTransactionAmount = 0.0;
            double totalMerchantTransactionAmount = merchantTransactionRepository.getMerchantTransactionByAmount(merchantId);

            return totalCustomerTransactionAmount - totalMerchantTransactionAmount;
        }
        else if (merchant1.isEmpty()){
            double totalMerchantTransactionAmount = 0.0;
            double totalCustomerTransactionAmount = customerTransactionRepository.getCustomerTransactionByAmount(merchantId);
            return totalCustomerTransactionAmount - totalMerchantTransactionAmount;
        }

        double totalCustomerTransactionAmount = customerTransactionRepository.getCustomerTransactionByAmount(merchantId);
        double totalMerchantTransactionAmount = merchantTransactionRepository.getMerchantTransactionByAmount(merchantId);
        return totalCustomerTransactionAmount - totalMerchantTransactionAmount;

    }

}
