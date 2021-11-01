package com.aninfo.service;

import com.aninfo.exceptions.InvalidTransactionTypeException;
import com.aninfo.factory.TransactionFactory;
import com.aninfo.model.Transaction;
import com.aninfo.model.TransactionType;
import com.aninfo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Component
class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionFactory transactionFactory;

    @Transactional
    public void createTransaction(Long cbu, Double sum, TransactionType transactionType) {
        var transaction = this.transactionFactory.create(cbu, sum, transactionType);
        this.transactionRepository.save(transaction);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Transaction saveTransaction(Transaction transaction) {
        return this.transactionRepository.save(transaction);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Transaction createTransaction(Transaction transaction) {

        if (transaction.getType() == TransactionType.DEPOSIT) {
            this.accountService.deposit(transaction.getCbu(), transaction.getSum());

        } else if (transaction.getType() == TransactionType.WITHDRAWAL) {
            this.accountService.withdraw(transaction.getCbu(), transaction.getSum());

        } else {
            throw new InvalidTransactionTypeException("Invalid transaction type.");
        }

        return this.transactionRepository.save(transaction);
    }

    public Collection<Transaction> getTransactions(long cbu) {
        return this.transactionRepository.getTransactionsByCbu(cbu);
    }
}
