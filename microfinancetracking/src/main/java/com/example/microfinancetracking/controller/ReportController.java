package com.example.microfinancetracking.controller;

import com.example.microfinancetracking.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/users/{id}/summary")
    public ResponseEntity<Map<String, Object>> getMonthlySummary(
            @PathVariable("id") Long userId,
            @RequestParam int year,
            @RequestParam int month) {
        Map<String, Object> summary = reportService.getMonthlySummary(userId, year, month);
        return new ResponseEntity<>(summary, HttpStatus.OK);
    }
}