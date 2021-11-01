package com.aninfo.model;

import java.util.Set;

public enum TransactionType {
    DEPOSIT,
    WITHDRAWAL;

    public static boolean isValid(TransactionType transactionType) {
        return transactionType != null
                && Set.of(DEPOSIT, WITHDRAWAL).contains(transactionType);
    }
}
