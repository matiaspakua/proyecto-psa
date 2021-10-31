package com.aninfo.service;

import com.aninfo.exceptions.DepositNegativeSumException;
import com.aninfo.exceptions.InsufficientFundsException;
import com.aninfo.model.Account;
import com.aninfo.model.TransactionType;
import com.aninfo.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.aninfo.model.TransactionType.DEPOSIT;
import static com.aninfo.model.TransactionType.WITHDRAWAL;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class AccountServiceTest {

    private static final Long CBU = 1234567890L;

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionService transactionService;

    @Mock
    private Account account, otherAccount, expectedAccount;

    @Captor
    private ArgumentCaptor<Double> balanceArgumentCaptor;

    @BeforeEach
    public void before() {
        initMocks(this);
    }

    @Test
    void createAccount() {
        when(this.accountRepository.save(this.account)).thenReturn(this.expectedAccount);

        Account actualAccount = this.accountService.createAccount(this.account);

        assertSame(this.expectedAccount, actualAccount);
        verify(this.accountRepository).save(this.account);
    }

    @Test
    void getAccounts() {
        // Repository without accounts
        when(this.accountRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(this.accountService.getAccounts().isEmpty());

        // Repository with only one account
        when(this.accountRepository.findAll()).thenReturn(List.of(account));
        Collection<Account> accounts = this.accountService.getAccounts();

        assertEquals(1, accounts.size());
        assertTrue(accounts.contains(account));

        // Repository with two accounts
        when(this.accountRepository.findAll()).thenReturn(List.of(account, otherAccount));
        accounts = this.accountService.getAccounts();

        assertEquals(2, accounts.size());
        assertTrue(accounts.contains(account));
        assertTrue(accounts.contains(otherAccount));

        verify(this.accountRepository, times(3)).findAll();
    }

    @Test
    void findById() {
        // Didn't find any account
        when(this.accountRepository.findById(CBU)).thenReturn(Optional.empty());
        assertFalse(this.accountService.findById(CBU).isPresent());

        // Actually find some account
        when(this.accountRepository.findById(CBU)).thenReturn(Optional.of(this.expectedAccount));
        Optional<Account> actualAccount = this.accountService.findById(CBU);

        assertTrue(actualAccount.isPresent());
        assertSame(this.expectedAccount, actualAccount.get());

        verify(this.accountRepository, times(2)).findById(CBU);
    }

    @Test
    void save() {
        when(this.accountRepository.save(this.account)).thenReturn(any(Account.class));

        this.accountService.save(this.account);

        verify(this.accountRepository).save(this.account);
    }

    @Test
    void deleteById() {
        doNothing().when(this.accountRepository).deleteById(CBU);

        this.accountService.deleteById(CBU);

        verify(this.accountRepository).deleteById(CBU);
    }

    @Test
    void withdraw_withInsufficientFundsThrowsInsufficientFundsException() {
        Double sum = 1200D;
        Double balance = 10D;

        when(this.accountRepository.findAccountByCbu(CBU)).thenReturn(this.account);
        when(this.account.getBalance()).thenReturn(balance);

        assertThrows(InsufficientFundsException.class,
                () -> this.accountService.withdraw(CBU, sum));
    }

    @Test
    void withdraw_sameSumAndBalanceLeftAccountWithBalanceInZero() {
        Double sum = 1200D;
        Double balance = 1200D;
        Double expectedBalance = 0D;

        loadAccountTransactionContext(balance, sum, WITHDRAWAL);
        Account actualAccount = this.accountService.withdraw(CBU, sum);
        assertAccountTransaction(actualAccount, sum, expectedBalance, WITHDRAWAL);
    }

    @Test
    void withdraw() {
        Double sum = 200D;
        Double balance = 1200D;
        Double expectedBalance = 1000D;

        loadAccountTransactionContext(balance, sum, WITHDRAWAL);
        Account actualAccount = this.accountService.withdraw(CBU, sum);
        assertAccountTransaction(actualAccount, sum, expectedBalance, WITHDRAWAL);
    }

    @Test
    void deposit_withoutSumToAddThrowsDepositNegativeSumException() {
        assertThrows(DepositNegativeSumException.class,
                () -> this.accountService.deposit(CBU, 0D));
    }

    @Test
    void deposit_withNegativeSumToAddThrowsDepositNegativeSumException() {
        assertThrows(DepositNegativeSumException.class,
                () -> this.accountService.deposit(CBU, -10D));
    }

    @Test
    void deposit() {
        Double sum = 200D;
        Double balance = 1200D;
        Double expectedBalance = 1400D;

        loadAccountTransactionContext(balance, sum, DEPOSIT);
        Account actualAccount = this.accountService.deposit(CBU, sum);
        assertAccountTransaction(actualAccount, sum, expectedBalance, DEPOSIT);

    }

    private void loadAccountTransactionContext(Double balance, Double sum, TransactionType transactionType) {
        when(this.accountRepository.findAccountByCbu(CBU)).thenReturn(this.account);
        when(this.account.getBalance()).thenReturn(balance);
        when(this.accountRepository.save(this.account)).thenReturn(mock(Account.class));
        doNothing().when(this.transactionService).createTransaction(CBU, sum, transactionType);
    }

    private void assertAccountTransaction(Account actualAccount, Double sum, Double expectedBalance, TransactionType transactionType) {
        assertSame(this.account, actualAccount);
        verify(this.account).setBalance(this.balanceArgumentCaptor.capture());
        assertEquals(expectedBalance, this.balanceArgumentCaptor.getValue());
        verify(this.accountRepository).findAccountByCbu(CBU);
        verify(this.accountRepository).save(account);
        verify(this.transactionService).createTransaction(CBU, sum, transactionType);
    }
}