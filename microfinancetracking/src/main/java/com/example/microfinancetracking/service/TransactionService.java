package com.example.microfinancetracking.service;

import com.example.microfinancetracking.exception.InvalidTransactionException;
import com.example.microfinancetracking.model.Transaction;
import com.example.microfinancetracking.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction addTransaction(Transaction transaction) {
        if (transaction.getAmount() <= 0) {
            throw new InvalidTransactionException("İşlem miktarı sıfırdan büyük olmalı.");
        }
        // Diğer validasyonlar (örn: kategori veya kullanıcı ID'si geçerli mi?)
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactionsForUser(Long userId) {
        return transactionRepository.findByUserId(userId);
    }

    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    public Transaction updateTransaction(Long id, Transaction updatedTransaction) {
        Optional<Transaction> existingTransactionOpt = transactionRepository.findById(id);
        if (existingTransactionOpt.isPresent()) {
            Transaction existingTransaction = existingTransactionOpt.get();
            // Sadece güncellenecek alanları kopyalayın
            existingTransaction.setAmount(updatedTransaction.getAmount());
            existingTransaction.setDescription(updatedTransaction.getDescription());
            existingTransaction.setDate(updatedTransaction.getDate());
            existingTransaction.setCategoryId(updatedTransaction.getCategoryId());
            existingTransaction.setType(updatedTransaction.getType());
            return transactionRepository.save(existingTransaction);
        }
        return null; // İşlem bulunamadı
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    public List<Transaction> getMonthlyTransactionsForUser(Long userId, int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        return transactionRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
    }
}
