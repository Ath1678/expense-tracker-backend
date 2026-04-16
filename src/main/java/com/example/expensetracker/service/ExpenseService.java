package com.example.expensetracker.service;
import com.example.expensetracker.model.Expense;
import java.util.List;
public interface ExpenseService {
  Expense saveExpense(Expense e);
  List<Expense> getAll();
  Expense getById(Long id);
  Expense update(Long id, Expense e);
  void delete(Long id);
  List<Expense> getByCategory(String category);
}
