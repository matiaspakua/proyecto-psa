package com.aninfo.service;

import com.aninfo.exceptions.InvalidTransactionTypeException;
import com.aninfo.model.Transaction;
import com.aninfo.model.TransactionType;
import com.aninfo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static com.aninfo.model.TransactionType.isValid;

@Component
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    @Transactional
    public void createTransaction(Long cbu, Double sum, TransactionType transactionType) {
        if(!isValid(transactionType)) {
            throw new InvalidTransactionTypeException("Invalid transaction type.");
        }

        var transaction = new Transaction(cbu, transactionType, sum);
        this.transactionRepository.save(transaction);
    }

    @Transactional
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
