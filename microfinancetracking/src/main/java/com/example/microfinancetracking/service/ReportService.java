package com.example.microfinancetracking.service;

import com.example.microfinancetracking.model.Transaction;
import com.example.microfinancetracking.model.Category;
import com.example.microfinancetracking.repository.TransactionRepository;
import com.example.microfinancetracking.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;

    public ReportService(TransactionRepository transactionRepository, CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
    }

    public Map<String, Object> getMonthlySummary(Long userId, int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        List<Transaction> transactions = transactionRepository.findByUserIdAndDateBetween(userId, startDate, endDate);

        double totalIncome = 0;
        double totalExpense = 0;
        Map<String, Double> categoryExpenses = new HashMap<>(); // En çok harcama yapılan kategori için

        for (Transaction transaction : transactions) {
            if ("GELİR".equalsIgnoreCase(transaction.getType())) {
                totalIncome += transaction.getAmount();
            } else if ("GİDER".equalsIgnoreCase(transaction.getType())) {
                totalExpense += transaction.getAmount();

                categoryRepository.findById(transaction.getCategoryId()).ifPresent(category -> {
                    categoryExpenses.merge(category.getName(), transaction.getAmount(), Double::sum);
                });
            }
        }

        double savings = totalIncome - totalExpense;

        String mostSpentCategory = null;
        double maxAmount = 0;
        for (Map.Entry<String, Double> entry : categoryExpenses.entrySet()) {
            if (entry.getValue() > maxAmount) {
                maxAmount = entry.getValue();
                mostSpentCategory = entry.getKey();
            }
        }

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalIncome", totalIncome);
        summary.put("totalExpense", totalExpense);
        summary.put("savings", savings);
        summary.put("mostSpentCategory", mostSpentCategory);
        summary.put("categoryExpenses", categoryExpenses);

        return summary;
    }
}