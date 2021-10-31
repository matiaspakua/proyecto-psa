package com.aninfo.factory;

import com.aninfo.exceptions.InvalidTransactionTypeException;
import com.aninfo.model.Transaction;
import com.aninfo.model.TransactionStatus;
import com.aninfo.model.TransactionType;
import org.springframework.stereotype.Component;

import static com.aninfo.model.TransactionStatus.FAILED;
import static com.aninfo.model.TransactionStatus.SUCCESSFUL;
import static com.aninfo.model.TransactionType.isValid;

@Component
public class TransactionFactory {

    public Transaction createSuccessfulTransaction(Long cbu, Double sum, TransactionType transactionType) {
        return this.createTransaction(cbu, sum, transactionType, SUCCESSFUL);
    }

    public Transaction createFailedTransaction(Long cbu, Double sum, TransactionType transactionType) {
        return this.createTransaction(cbu, sum, transactionType, FAILED);
    }

    private Transaction createTransaction(Long cbu, Double sum, TransactionType transactionType, TransactionStatus transactionStatus) {
        if(!isValid(transactionType)) {
            throw new InvalidTransactionTypeException("Invalid transaction type.");
        }

        return new Transaction(cbu, transactionType, sum, transactionStatus);
    }
}
