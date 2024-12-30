package org.example.massinissatransactionsystemaddedjavadev;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class TransactionService {
    private final Map<String, Account> accounts = new ConcurrentHashMap<>();
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private final List<String> transactionLog = Collections.synchronizedList(new ArrayList<>());

    public void createAccount(String accountId, double initialBalance) {
        accounts.put(accountId, new Account(accountId, initialBalance));
    }

    public Account getAccount(String accountId) {
        return accounts.get(accountId);
    }

    public void processTransaction(Transaction transaction) {
        executorService.submit(() -> {
            Account fromAccount = accounts.get(transaction.getFromAccountId());
            Account toAccount = accounts.get(transaction.getToAccountId());
            String logEntry;

            if (fromAccount == null || toAccount == null) {
                logEntry = "Transaction " + transaction.getTransactionId() + " failed: Invalid account(s).";
            } else {
                synchronized (fromAccount) {
                    synchronized (toAccount) {
                        if (fromAccount.updateBalance(-transaction.getAmount())) {
                            toAccount.updateBalance(transaction.getAmount());
                            logEntry = "Transaction " + transaction.getTransactionId() + " succeeded.";
                        } else {
                            logEntry = "Transaction " + transaction.getTransactionId() + " failed: Insufficient funds.";
                        }
                    }
                }
            }
            transactionLog.add(logEntry);
        });
    }

    public List<String> getTransactionLog() {
        return transactionLog;
    }
}

