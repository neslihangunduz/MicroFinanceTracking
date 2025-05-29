package com.example.microfinancetracking.controller;

import com.example.microfinancetracking.exception.InvalidTransactionException;
import com.example.microfinancetracking.model.Transaction;
import com.example.microfinancetracking.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addTransaction(@RequestBody Transaction transaction) {
        try {
            Transaction newTransaction = transactionService.addTransaction(transaction);
            return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
        } catch (InvalidTransactionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactionsForUser(@RequestParam Long userId) {
        List<Transaction> transactions = transactionService.getAllTransactionsForUser(userId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id)
                .map(transaction -> new ResponseEntity<>(transaction, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id, @RequestBody Transaction transaction) {
        Transaction updated = transactionService.updateTransaction(id, transaction);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
    }

    @GetMapping("/monthly")
    public ResponseEntity<List<Transaction>> getMonthlyTransactions(
            @RequestParam int month,
            @RequestParam int year,
            @RequestParam Long userId) {
        List<Transaction> transactions = transactionService.getMonthlyTransactionsForUser(userId, year, month);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}