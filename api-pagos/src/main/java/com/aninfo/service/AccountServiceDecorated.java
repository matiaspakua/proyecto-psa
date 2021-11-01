package com.aninfo.service;

import com.aninfo.factory.TransactionFactory;
import com.aninfo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

import static com.aninfo.model.TransactionType.DEPOSIT;
import static com.aninfo.model.TransactionType.WITHDRAWAL;

@Component
public class AccountServiceDecorated extends AccountService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionFactory transactionFactory;

    @Override
    public Account withdraw(Long cbu, Double sum) {
        try {
            return this.withdrawWithTransaction(cbu, sum);
        } catch (Exception e) {
            var transaction = this.transactionFactory.create(cbu, sum, WITHDRAWAL);
            this.transactionService.saveTransaction(transaction.failed());
            throw e;
        }
    }

    @Override
    public Account deposit(Long cbu, Double sum) {
        try {
            return this.depositWithTransaction(cbu, sum);
        } catch (Exception e) {
            var transaction = this.transactionFactory.create(cbu, sum, DEPOSIT);
            this.transactionService.saveTransaction(transaction.failed());
            throw e;
        }
    }

    @Transactional
    protected Account withdrawWithTransaction(Long cbu, Double sum) {
        var account = this.accountService.withdraw(cbu, sum);
        this.transactionService.createTransaction(cbu, sum, WITHDRAWAL);
        return account;
    }

    @Transactional
    protected Account depositWithTransaction(Long cbu, Double sum) {
        var account = this.accountService.deposit(cbu, sum);
        this.transactionService.createTransaction(cbu, sum, DEPOSIT);
        return account;
    }
}
