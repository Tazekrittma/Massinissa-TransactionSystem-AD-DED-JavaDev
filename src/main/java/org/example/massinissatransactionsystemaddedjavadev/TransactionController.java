package org.example.massinissatransactionsystemaddedjavadev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/accounts")
    public String createAccount(@RequestParam String accountId, @RequestParam double initialBalance) {
        transactionService.createAccount(accountId, initialBalance);
        return "Account created: " + accountId;
    }

    @GetMapping("/accounts/{accountId}")
    public Account getAccount(@PathVariable String accountId) {
        return transactionService.getAccount(accountId);
    }

    @PostMapping("/transactions")
    public String createTransaction(@RequestBody Transaction transaction) {
        transactionService.processTransaction(transaction);
        return "Transaction submitted: " + transaction.getTransactionId();
    }

    @GetMapping("/transactions/log")
    public List<String> getTransactionLog() {
        return transactionService.getTransactionLog();
    }
}

