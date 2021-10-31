package com.aninfo.service;

import com.aninfo.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    private Account expectedAccount;

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
        when(this.accountService.withdraw(CBU, SUM)).thenThrow(this.exception);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> this.accountServiceDecorated.withdraw(CBU, SUM));

        assertSame(this.exception, exception);
        verify(this.accountService).withdraw(CBU, SUM);
        verify(this.transactionService).createFailedTransaction(CBU, SUM, WITHDRAWAL);
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
        when(this.accountService.deposit(CBU, SUM)).thenReturn(this.expectedAccount);
        doThrow(this.exception).when(this.transactionService).createTransaction(CBU, SUM, DEPOSIT);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> this.accountServiceDecorated.deposit(CBU, SUM));

        assertSame(this.exception, exception);
        verify(this.accountService).deposit(CBU, SUM);
        verify(this.transactionService).createTransaction(CBU, SUM, DEPOSIT);
        verify(this.transactionService).createFailedTransaction(CBU, SUM, DEPOSIT);
        verifyNoMoreInteractions(this.accountService, this.transactionService);
    }
}
