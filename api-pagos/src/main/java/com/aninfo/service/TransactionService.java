package com.aninfo.service;

import com.aninfo.exceptions.InvalidTransactionTypeException;
import com.aninfo.model.Transaction;
import com.aninfo.model.TransactionType;
import com.aninfo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    @Transactional
    public Transaction createTransaction(Transaction transaction) {

        if (transaction.getType() == TransactionType.DEPOSIT) {
            accountService.deposit(transaction.getCbu(), transaction.getSum());

        } else if (transaction.getType() == TransactionType.WITHDRAWAL) {
            accountService.withdraw(transaction.getCbu(), transaction.getSum());

        } else {
            throw new InvalidTransactionTypeException("Invalid transaction type.");
        }

        return transactionRepository.save(transaction);
    }

    public Collection<Transaction> getTransactions(long cbu) {
        return transactionRepository.getTransactionsByCbu(cbu);
    }

}
