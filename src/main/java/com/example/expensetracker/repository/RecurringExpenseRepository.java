package com.example.expensetracker.repository;

import com.example.expensetracker.model.RecurringExpense;
import com.example.expensetracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecurringExpenseRepository extends JpaRepository<RecurringExpense, Long> {
    List<RecurringExpense> findByUser(User user);
}
