package com.aninfo.service;

import com.aninfo.factory.TransactionFactory;
import com.aninfo.model.Account;
import com.aninfo.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static com.aninfo.model.TransactionType.DEPOSIT;
import static com.aninfo.model.TransactionType.WITHDRAWAL;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class AccountServiceDecoratedTest {

    private static final Long CBU = 1234567890L;
    private static final Double SUM = 123D;

    @InjectMocks
    private AccountServiceDecorated accountServiceDecorated;

    @Mock
    private AccountService accountService;

    @Mock
    private TransactionService transactionService;

    @Mock
    private TransactionFactory transactionFactory;

    @Mock
    private Account expectedAccount;

    @Mock
    private Transaction transaction, failedTransaction;

    @Mock
    private IllegalArgumentException exception;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void withdraw() {
        when(this.accountService.withdraw(CBU, SUM)).thenReturn(this.expectedAccount);
        doNothing().when(this.transactionService).createTransaction(CBU, SUM, WITHDRAWAL);

        Account actualAccount = this.accountServiceDecorated.withdraw(CBU, SUM);

        assertSame(this.expectedAccount, actualAccount);
        verify(this.accountService).withdraw(CBU, SUM);
        verify(this.transactionService).createTransaction(CBU, SUM, WITHDRAWAL);
        verifyNoMoreInteractions(this.accountService, this.transactionService);
    }

    @Test
    void withdraw_asFailedTransaction() {
        when(this.transactionFactory.create(CBU, SUM, WITHDRAWAL)).thenReturn(this.transaction);
        when(this.transaction.failed()).thenReturn(this.failedTransaction);
        when(this.accountService.withdraw(CBU, SUM)).thenThrow(this.exception);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> this.accountServiceDecorated.withdraw(CBU, SUM));

        assertSame(this.exception, exception);

        InOrder withdrawalTransactionInOrder = inOrder(this.accountService, this.transactionFactory, this.transaction, this.transactionService);
        withdrawalTransactionInOrder.verify(this.accountService).withdraw(CBU, SUM);
        withdrawalTransactionInOrder.verify(this.transactionFactory).create(CBU, SUM, WITHDRAWAL);
        withdrawalTransactionInOrder.verify(this.transaction).failed();
        withdrawalTransactionInOrder.verify(this.transactionService).saveTransaction(this.failedTransaction);
        verifyNoMoreInteractions(this.accountService, this.transactionService);
    }

    @Test
    void deposit() {
        when(this.accountService.deposit(CBU, SUM)).thenReturn(this.expectedAccount);
        doNothing().when(this.transactionService).createTransaction(CBU, SUM, DEPOSIT);

        Account actualAccount = this.accountServiceDecorated.deposit(CBU, SUM);

        assertSame(this.expectedAccount, actualAccount);
        verify(this.accountService).deposit(CBU, SUM);
        verify(this.transactionService).createTransaction(CBU, SUM, DEPOSIT);
        verifyNoMoreInteractions(this.accountService, this.transactionService);
    }

    @Test
    void deposit_asFailedTransaction() {
        when(this.transactionFactory.create(CBU, SUM, DEPOSIT)).thenReturn(this.transaction);
        when(this.transaction.failed()).thenReturn(this.failedTransaction);
        when(this.accountService.deposit(CBU, SUM)).thenReturn(this.expectedAccount);
        doThrow(this.exception).when(this.transactionService).createTransaction(CBU, SUM, DEPOSIT);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> this.accountServiceDecorated.deposit(CBU, SUM));

        assertSame(this.exception, exception);

        InOrder withdrawalTransactionInOrder = inOrder(this.accountService, this.transactionService, this.transactionFactory, this.transaction);
        withdrawalTransactionInOrder.verify(this.accountService).deposit(CBU, SUM);
        withdrawalTransactionInOrder.verify(this.transactionService).createTransaction(CBU, SUM, DEPOSIT);
        withdrawalTransactionInOrder.verify(this.transactionFactory).create(CBU, SUM, DEPOSIT);
        withdrawalTransactionInOrder.verify(this.transaction).failed();
        withdrawalTransactionInOrder.verify(this.transactionService).saveTransaction(this.failedTransaction);
        verifyNoMoreInteractions(this.accountService, this.transactionService);
    }
}
