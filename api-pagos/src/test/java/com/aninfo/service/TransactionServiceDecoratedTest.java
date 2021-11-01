package com.aninfo.service;

import com.aninfo.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class TransactionServiceDecoratedTest {

    @InjectMocks
    private TransactionServiceDecorated transactionServiceDecorated;

    @Mock
    private TransactionService transactionService;

    @Mock
    private Transaction transaction, expectedTransaction, failedTransaction;

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
        when(this.transactionService.createTransaction(this.transaction)).thenThrow(new IllegalArgumentException());
        when(this.transaction.failed()).thenReturn(this.failedTransaction);
        when(this.transactionService.saveTransaction(this.failedTransaction)).thenReturn(this.expectedTransaction);

        Transaction actualTransaction = this.transactionServiceDecorated.createTransaction(this.transaction);

        assertSame(this.expectedTransaction, actualTransaction);

        InOrder transactionServiceInOrder = inOrder(this.transactionService);
        transactionServiceInOrder.verify(this.transactionService).createTransaction(this.transaction);
        transactionServiceInOrder.verify(this.transactionService).saveTransaction(this.failedTransaction);
        verify(this.transaction).failed();
        verifyNoMoreInteractions(this.transactionService);
    }
}
