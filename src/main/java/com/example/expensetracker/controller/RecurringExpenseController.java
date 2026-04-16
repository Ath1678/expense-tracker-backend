package com.example.expensetracker.controller;

import com.example.expensetracker.model.Expense;
import com.example.expensetracker.model.RecurringExpense;
import com.example.expensetracker.model.User;
import com.example.expensetracker.repository.UserRepository;
import com.example.expensetracker.security.services.UserDetailsImpl;
import com.example.expensetracker.service.RecurringExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recurring")
public class RecurringExpenseController {

    @Autowired
    private RecurringExpenseService recurringExpenseService;

    @Autowired
    private UserRepository userRepository;

    private User getCurrentUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findById(userDetails.getId()).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @GetMapping
    public ResponseEntity<List<RecurringExpense>> getAll() {
        return ResponseEntity.ok(recurringExpenseService.getRecurringExpensesByUser(getCurrentUser()));
    }

    @PostMapping
    public ResponseEntity<RecurringExpense> create(@RequestBody RecurringExpense recurringExpense) {
        recurringExpense.setUser(getCurrentUser());
        return ResponseEntity.ok(recurringExpenseService.addRecurringExpense(recurringExpense));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recurringExpenseService.deleteRecurringExpense(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/pay")
    public ResponseEntity<Expense> pay(@PathVariable Long id) {
        return ResponseEntity.ok(recurringExpenseService.payRecurringExpense(id, getCurrentUser()));
    }
}
