package org.example.massinissatransactionsystemaddedjavadev;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class MassinissaTransactionSystemAdDedJavaDevApplicationTests {

    @Autowired
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        // This will run before each test to ensure a clean state
        transactionService.createAccount("A1", 1000.00);
        transactionService.createAccount("A2", 500.00);
    }

    @Test
    void contextLoads() {
    }

    @Test
    void testCreateAccount() {
        // Arrange
        String accountId = "A3";
        double initialBalance = 1500.00;

        // Act
        transactionService.createAccount(accountId, initialBalance);
        Account account = transactionService.getAccount(accountId);

        // Assert
        assertNotNull(account);
        assertEquals(accountId, account.getAccountId());
        assertEquals(initialBalance, account.getBalance());
    }

    @Test
    void testCreateTransaction() {
        // Arrange
        String fromAccountId = "A1";
        String toAccountId = "A2";
        double amount = 100.00;
        Transaction transaction = new Transaction("T1", fromAccountId, toAccountId, amount);

        // Act
        transactionService.processTransaction(transaction);
        Account fromAccount = transactionService.getAccount(fromAccountId);
        Account toAccount = transactionService.getAccount(toAccountId);

        // Assert
        assertNotNull(fromAccount);
        assertNotNull(toAccount);
        assertEquals(900.00, fromAccount.getBalance(), 0.01); // A1 should be reduced by 100
        assertEquals(600.00, toAccount.getBalance(), 0.01); // A2 should be increased by 100
    }

    @Test
    void testInsufficientFundsTransaction() {
        // Arrange
        String fromAccountId = "A1";
        String toAccountId = "A2";
        double amount = 1100.00; // Insufficient funds
        Transaction transaction = new Transaction("T2", fromAccountId, toAccountId, amount);

        // Act
        transactionService.processTransaction(transaction);
        Account fromAccount = transactionService.getAccount(fromAccountId);
        Account toAccount = transactionService.getAccount(toAccountId);

        // Assert
        assertNotNull(fromAccount);
        assertNotNull(toAccount);
        assertEquals(1000.00, fromAccount.getBalance(), 0.01); // Balance of A1 should remain the same
        assertEquals(500.00, toAccount.getBalance(), 0.01); // Balance of A2 should remain the same
    }



    @Test
    void testGetAccountDetails() {
        // Act
        Account account = transactionService.getAccount("A1");

        // Assert
        assertNotNull(account);
        assertEquals("A1", account.getAccountId());
        assertEquals(1000.00, account.getBalance(), 0.01);
    }
}
