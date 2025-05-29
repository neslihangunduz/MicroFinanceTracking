package com.example.microfinancetracking.model;

import lombok.Data;
import java.time.LocalDate;


@Data
public class Transaction {
    private Long id;
    private Long userId;
    private Long categoryId;
    private Double amount;
    private String description;
    private LocalDate date;
    private String type;
}