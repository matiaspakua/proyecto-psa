package com.aninfo.service;

import com.aninfo.model.Transaction;
import com.aninfo.model.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static com.aninfo.model.TransactionType.DEPOSIT;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class TransactionServiceDecoratedTest {

    @InjectMocks
    private TransactionServiceDecorated transactionServiceDecorated;

    @Mock
    private TransactionService transactionService;

    @Mock
    private Transaction transaction, expectedTransaction;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void createTransaction() {
        when(this.transactionService.createTransaction(this.transaction)).thenReturn(this.expectedTransaction);

        Transaction actualTransaction = this.transactionServiceDecorated.createTransaction(this.transaction);

        assertSame(this.expectedTransaction, actualTransaction);
        verify(this.transactionService).createTransaction(this.transaction);
        verifyNoMoreInteractions(this.transactionService);
    }

    @Test
    void createTransaction_asFailedTransaction() {
        final Long cbu = 1234567890L;
        final Double sum = 123D;
        final TransactionType transactionType = DEPOSIT;

        when(this.transaction.getCbu()).thenReturn(cbu);
        when(this.transaction.getSum()).thenReturn(sum);
        when(this.transaction.getType()).thenReturn(transactionType);

        IllegalArgumentException expectedException = mock(IllegalArgumentException.class);
        when(this.transactionService.createTransaction(this.transaction)).thenThrow(expectedException);

        Exception actualException = assertThrows(Exception.class,
                () -> this.transactionServiceDecorated.createTransaction(this.transaction));

        assertSame(expectedException, actualException);
        verify(this.transactionService).createTransaction(this.transaction);
        verify(this.transactionService).createFailedTransaction(cbu, sum, transactionType);
    }
}
