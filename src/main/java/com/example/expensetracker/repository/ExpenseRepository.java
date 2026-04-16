package com.example.expensetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.expensetracker.model.Expense;
import com.example.expensetracker.model.User;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
  List<Expense> findByUser(User user);

  List<Expense> findByCategory(String category);
}
