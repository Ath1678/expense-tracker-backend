package com.example.expensetracker.service;

import com.example.expensetracker.model.Expense;
import com.example.expensetracker.model.RecurringExpense;
import com.example.expensetracker.model.User;
import com.example.expensetracker.repository.ExpenseRepository;
import com.example.expensetracker.repository.RecurringExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RecurringExpenseService {

    @Autowired
    private RecurringExpenseRepository recurringExpenseRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    public List<RecurringExpense> getRecurringExpensesByUser(User user) {
        return recurringExpenseRepository.findByUser(user);
    }

    public RecurringExpense addRecurringExpense(RecurringExpense recurringExpense) {
        return recurringExpenseRepository.save(recurringExpense);
    }

    public void deleteRecurringExpense(Long id) {
        recurringExpenseRepository.deleteById(id);
    }

    @Transactional
    public Expense payRecurringExpense(Long id, User user) {
        Optional<RecurringExpense> recurringOpt = recurringExpenseRepository.findById(id);
        
        if (recurringOpt.isPresent()) {
            RecurringExpense recurring = recurringOpt.get();
            
            // 1. Create a real Expense
            Expense expense = new Expense();
            expense.setTitle(recurring.getTitle());
            expense.setAmount(recurring.getAmount());
            expense.setCategory(recurring.getCategory());
            expense.setDate(LocalDate.now());
            expense.setNotes("Recurring Payment processed manually");
            expense.setUser(user);
            
            Expense savedExpense = expenseRepository.save(expense);
            
            // 2. Update the recurring expense's last paid date
            recurring.setLastPaidDate(LocalDate.now());
            recurringExpenseRepository.save(recurring);
            
            return savedExpense;
        }
        
        throw new RuntimeException("Recurring expense not found");
    }
}
