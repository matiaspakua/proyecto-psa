package com.aninfo.service;

import com.aninfo.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionServiceDecorated extends TransactionService {

    @Autowired
    private TransactionService transactionService;

    @Override
    public Transaction createTransaction(Transaction transaction) {
        try {
            return this.transactionService.createTransaction(transaction);
        } catch (Exception e) {
            return this.transactionService.saveTransaction(transaction.failed());
        }
    }
}
