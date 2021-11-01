package com.aninfo.service;

import com.aninfo.exceptions.InvalidTransactionTypeException;
import com.aninfo.factory.TransactionFactory;
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
    private TransactionFactory transactionFactory;

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
    public void setUp() {
        initMocks(this);

        when(this.transaction.getCbu()).thenReturn(CBU);
        when(this.transaction.getSum()).thenReturn(SUM);
    }

    @Test
    void createTransaction() {
        TransactionType transactionType = WITHDRAWAL;
        when(this.transactionFactory.create(CBU, SUM, transactionType)).thenReturn(this.transaction);
        when(this.transactionRepository.save(this.transaction)).thenReturn(this.otherTransaction);

        this.transactionService.createTransaction(CBU, SUM, transactionType);

        verify(this.transactionFactory).create(CBU, SUM, transactionType);
        verify(this.transactionRepository).save(this.transactionArgumentCaptor.capture());
        assertSame(this.transaction, this.transactionArgumentCaptor.getValue());
        verifyNoMoreInteractions(this.transactionFactory, this.transactionRepository);
    }

    @Test
    void saveTransaction() {
        when(this.transactionRepository.save(this.transaction)).thenReturn(this.expectedTransaction);

        Transaction actualTransaction = this.transactionService.saveTransaction(this.transaction);

        assertSame(this.expectedTransaction, actualTransaction);
    }

    @Test
    void createTransaction_withUnknownTransactionTypeThrowsInvalidTransactionTypeException() {
        when(this.transaction.getType()).thenReturn(null);
        InvalidTransactionTypeException exception = assertThrows(InvalidTransactionTypeException.class,
                () -> this.transactionService.createTransaction(this.transaction));
        assertEquals("Invalid transaction type.", exception.getMessage());
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

    private void verifyCommonTransactionCreation() {
        assertEquals(CBU, this.cbuArgumentCaptor.getValue());
        assertEquals(SUM, this.sumArgumentCaptor.getValue());
        verify(this.transactionRepository).save(this.transaction);
        verifyNoMoreInteractions(this.accountService);
    }
}
