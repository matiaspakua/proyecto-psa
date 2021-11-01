package com.aninfo.factory;

import com.aninfo.exceptions.InvalidTransactionTypeException;
import com.aninfo.model.Transaction;
import com.aninfo.model.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void create_withoutTransactionTypeWillThrowAnInvalidTransactionTypeException() {
        InvalidTransactionTypeException exception = assertThrows(InvalidTransactionTypeException.class,
                () -> this.transactionFactory.create(CBU, SUM, null));
        assertEquals(EXPECTED_INVALID_TRANSACTION_TYPE_MESSAGE, exception.getMessage());
    }

    @Test
    void createSuccessfulTransaction() {
        Transaction transaction = this.transactionFactory.create(CBU, SUM, TRANSACTION_TYPE);

        assertEquals(CBU, transaction.getCbu());
        assertEquals(SUM, transaction.getSum());
        assertEquals(TRANSACTION_TYPE, transaction.getType());
        assertEquals(SUCCESSFUL, transaction.getStatus());
    }
}
