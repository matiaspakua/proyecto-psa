package com.aninfo.factory;

import com.aninfo.exceptions.InvalidTransactionTypeException;
import com.aninfo.model.Transaction;
import com.aninfo.model.TransactionStatus;
import com.aninfo.model.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.aninfo.model.TransactionStatus.FAILED;
import static com.aninfo.model.TransactionStatus.SUCCESSFUL;
import static com.aninfo.model.TransactionType.DEPOSIT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.MockitoAnnotations.initMocks;

class TransactionFactoryTest {

    private static final Long CBU = 1234567890L;
    private static final Double SUM = 123D;
    private static final TransactionType TRANSACTION_TYPE = DEPOSIT;
    private static final String EXPECTED_INVALID_TRANSACTION_TYPE_MESSAGE = "Invalid transaction type.";

    private final TransactionFactory transactionFactory = new TransactionFactory();

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void createSuccessfulTransaction_withoutTransactionTypeWillThrowAnInvalidTransactionTypeException() {
        InvalidTransactionTypeException exception = assertThrows(InvalidTransactionTypeException.class,
                () -> this.transactionFactory.createSuccessfulTransaction(CBU, SUM, null));
        assertEquals(EXPECTED_INVALID_TRANSACTION_TYPE_MESSAGE, exception.getMessage());
    }

    @Test
    void createSuccessfulTransaction() {
        assertTransaction(this.transactionFactory.createSuccessfulTransaction(CBU, SUM, TRANSACTION_TYPE),
                CBU, SUM, TRANSACTION_TYPE, SUCCESSFUL);
    }

    @Test
    void createFailedTransaction_withoutTransactionTypeWillThrowAnInvalidTransactionTypeException() {
        InvalidTransactionTypeException exception = assertThrows(InvalidTransactionTypeException.class,
                () -> this.transactionFactory.createFailedTransaction(CBU, SUM, null));
        assertEquals(EXPECTED_INVALID_TRANSACTION_TYPE_MESSAGE, exception.getMessage());
    }

    @Test
    void createFailedTransaction() {
        assertTransaction(this.transactionFactory.createFailedTransaction(CBU, SUM, TRANSACTION_TYPE),
                CBU, SUM, TRANSACTION_TYPE, FAILED);
    }

    private void assertTransaction(Transaction actualTransaction,
                                   Long expectedCbu,
                                   Double expectedSum,
                                   TransactionType expectedTransactionType,
                                   TransactionStatus expectedTransactionStatus) {
        assertEquals(expectedCbu, actualTransaction.getCbu());
        assertEquals(expectedSum, actualTransaction.getSum());
        assertEquals(expectedTransactionType, actualTransaction.getType());
        assertEquals(expectedTransactionStatus, actualTransaction.getStatus());
    }
}
