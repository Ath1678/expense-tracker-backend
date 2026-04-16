package com.example.expensetracker.controller;

import com.example.expensetracker.model.Income;
import com.example.expensetracker.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/incomes")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @GetMapping
    public ResponseEntity<List<Income>> getAllIncomes() {
        return ResponseEntity.ok(incomeService.getAllIncomes());
    }

    @PostMapping
    public ResponseEntity<Income> addIncome(@RequestBody Income income) {
        return ResponseEntity.ok(incomeService.addIncome(income));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIncome(@PathVariable Long id) {
        incomeService.deleteIncome(id);
        return ResponseEntity.ok("Income deleted successfully");
    }
}
