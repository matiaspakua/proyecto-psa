package com.aninfo.factory;

import com.aninfo.exceptions.InvalidTransactionTypeException;
import com.aninfo.model.Transaction;
import com.aninfo.model.TransactionType;
import org.springframework.stereotype.Component;

import static com.aninfo.model.TransactionType.isValid;

@Component
public class TransactionFactory {

    public Transaction create(Long cbu, Double sum, TransactionType transactionType) {
        if(!isValid(transactionType)) {
            throw new InvalidTransactionTypeException("Invalid transaction type.");
        }

        return new Transaction(cbu, transactionType, sum);
    }
}
