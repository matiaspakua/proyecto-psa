package com.aninfo.service;

import com.aninfo.exceptions.InvalidTransactionTypeException;
import com.aninfo.model.Transaction;
import com.aninfo.model.TransactionType;
import com.aninfo.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.aninfo.model.TransactionType.DEPOSIT;
import static com.aninfo.model.TransactionType.WITHDRAWAL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class TransactionServiceTest {

    private static final Long CBU = 1234567890L;
    private static final Double SUM = 123D;

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountService accountService;

    @Mock
    private Transaction transaction, otherTransaction, expectedTransaction;

    @Captor
    private ArgumentCaptor<Transaction> transactionArgumentCaptor;

    @Captor
    private ArgumentCaptor<Long> cbuArgumentCaptor;

    @Captor
    private ArgumentCaptor<Double> sumArgumentCaptor;

    @BeforeEach
    public void before() {
        initMocks(this);

        when(this.transaction.getCbu()).thenReturn(CBU);
        when(this.transaction.getSum()).thenReturn(SUM);
    }

    @Test
    void createTransaction_withoutTransactionTypeThrowsInvalidTransactionTypeException() {
        assertThrows(InvalidTransactionTypeException.class,
                () -> this.transactionService.createTransaction(CBU, SUM, null));
    }

    @Test
    void createTransaction_withDepositAsTransactionType() {
        this.transactionService.createTransaction(CBU, SUM, DEPOSIT);

        verify(this.transactionRepository).save(this.transactionArgumentCaptor.capture());
        Transaction transactionToSave = this.transactionArgumentCaptor.getValue();
        assertTransaction(transactionToSave, CBU, SUM, DEPOSIT);
    }

    @Test
    void createTransaction_withWithdrawalAsTransactionType() {
        this.transactionService.createTransaction(CBU, SUM, WITHDRAWAL);

        verify(this.transactionRepository).save(this.transactionArgumentCaptor.capture());
        Transaction transactionToSave = this.transactionArgumentCaptor.getValue();
        assertTransaction(transactionToSave, CBU, SUM, WITHDRAWAL);
    }

    @Test
    void createTransaction_withUnknownTransactionTypeThrowsInvalidTransactionTypeException() {
        when(this.transaction.getType()).thenReturn(null);
        assertThrows(InvalidTransactionTypeException.class,
                () -> this.transactionService.createTransaction(this.transaction));
    }

    @Test
    void createTransaction_asDeposit() {
        when(this.transaction.getType()).thenReturn(DEPOSIT);
        when(this.accountService.deposit(CBU, SUM)).thenReturn(any());
        when(this.transactionRepository.save(this.transaction)).thenReturn(this.expectedTransaction);

        Transaction actualTransaction = this.transactionService.createTransaction(this.transaction);

        assertSame(this.expectedTransaction, actualTransaction);
        verify(this.accountService).deposit(this.cbuArgumentCaptor.capture(), this.sumArgumentCaptor.capture());
        verifyCommonTransactionCreation();
    }

    @Test
    void createTransaction_asWithdrawal() {
        when(this.transaction.getType()).thenReturn(WITHDRAWAL);
        when(this.accountService.withdraw(CBU, SUM)).thenReturn(any());
        when(this.transactionRepository.save(this.transaction)).thenReturn(this.expectedTransaction);

        Transaction actualTransaction = this.transactionService.createTransaction(this.transaction);

        assertSame(this.expectedTransaction, actualTransaction);
        verify(this.accountService).withdraw(this.cbuArgumentCaptor.capture(), this.sumArgumentCaptor.capture());
        verifyCommonTransactionCreation();
    }

    @Test
    void getTransactions() {
        // Repository without transactions
        when(this.transactionRepository.getTransactionsByCbu(CBU)).thenReturn(new ArrayList<>());
        assertTrue(this.transactionService.getTransactions(CBU).isEmpty());

        // Repository with only one transaction
        when(this.transactionRepository.getTransactionsByCbu(CBU)).thenReturn(List.of(this.transaction));
        Collection<Transaction> transactions = this.transactionService.getTransactions(CBU);
        assertEquals(1, transactions.size());
        assertTrue(transactions.contains(this.transaction));

        // Repository with two transaction
        when(this.transactionRepository.getTransactionsByCbu(CBU)).thenReturn(List.of(this.transaction, this.otherTransaction));
        transactions = this.transactionService.getTransactions(CBU);
        assertEquals(2, transactions.size());
        assertTrue(transactions.contains(this.transaction));
        assertTrue(transactions.contains(this.otherTransaction));

        verify(this.transactionRepository, times(3)).getTransactionsByCbu(CBU);
    }

    private void assertTransaction(Transaction transaction,
                                   Long expectedCbu,
                                   Double expectedSum,
                                   TransactionType expectedTransactionType) {
        assertEquals(expectedCbu, transaction.getCbu());
        assertEquals(expectedSum, transaction.getSum());
        assertEquals(expectedTransactionType, transaction.getType());
    }

    private void verifyCommonTransactionCreation() {
        assertEquals(CBU, this.cbuArgumentCaptor.getValue());
        assertEquals(SUM, this.sumArgumentCaptor.getValue());
        verify(this.transactionRepository).save(this.transaction);
        verifyNoMoreInteractions(this.accountService);
    }
}
