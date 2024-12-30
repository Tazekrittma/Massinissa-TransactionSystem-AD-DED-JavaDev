package org.example.massinissatransactionsystemaddedjavadev;


public class Transaction {
    private String transactionId;
    private String fromAccountId;
    private String toAccountId;
    private double amount;

    public Transaction(String transactionId, String fromAccountId, String toAccountId, double amount) {
        this.transactionId = transactionId;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getFromAccountId() {
        return fromAccountId;
    }

    public String getToAccountId() {
        return toAccountId;
    }

    public double getAmount() {
        return amount;
    }
}

