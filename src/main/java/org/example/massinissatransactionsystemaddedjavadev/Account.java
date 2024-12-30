package org.example.massinissatransactionsystemaddedjavadev;



public class Account {
    private String accountId;
    private double balance;

    public Account(String accountId, double initialBalance) {
        this.accountId = accountId;
        this.balance = initialBalance;
    }

    public String getAccountId() {
        return accountId;
    }

    public double getBalance() {
        return balance;
    }

    public synchronized boolean updateBalance(double amount) {
        if (balance + amount < 0) {
            return false;
        }
        balance += amount;
        return true;
    }
}
