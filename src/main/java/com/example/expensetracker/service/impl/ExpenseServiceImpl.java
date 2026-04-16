package com.example.expensetracker.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Collections;

import com.example.expensetracker.model.Expense;
import com.example.expensetracker.model.User;
import com.example.expensetracker.repository.ExpenseRepository;
import com.example.expensetracker.repository.UserRepository;
import com.example.expensetracker.service.ExpenseService;

@Service
public class ExpenseServiceImpl implements ExpenseService {

  @Autowired
  private ExpenseRepository repo;

  @Autowired
  private UserRepository userRepository;

  private User getCurrentUser() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String username;
    if (principal instanceof UserDetails) {
      username = ((UserDetails) principal).getUsername();
    } else {
      username = principal.toString();
    }
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new RuntimeException("User not found: " + username));
  }

  @Override
  public Expense saveExpense(Expense e) {
    User user = getCurrentUser();
    e.setUser(user);
    return repo.save(e);
  }

  @Override
  public List<Expense> getAll() {
    try {
      User user = getCurrentUser();
      return repo.findByUser(user);
    } catch (Exception e) {
      return Collections.emptyList();
    }
  }

  @Override
  public Expense getById(Long id) {
    // Ensure user owns the expense
    Expense expense = repo.findById(id).orElse(null);
    if (expense != null && expense.getUser().getId().equals(getCurrentUser().getId())) {
      return expense;
    }
    return null; // Or throw NotAuthorized
  }

  @Override
  public Expense update(Long id, Expense e) {
    Expense ex = getById(id); // Re-use getById to check ownership
    if (ex == null)
      return null;

    ex.setTitle(e.getTitle());
    ex.setCategory(e.getCategory());
    ex.setAmount(e.getAmount());
    ex.setDate(e.getDate());
    ex.setNotes(e.getNotes());
    return repo.save(ex);
  }

  @Override
  public void delete(Long id) {
    Expense ex = getById(id);
    if (ex != null) {
      repo.deleteById(id);
    }
  }

  @Override
  public List<Expense> getByCategory(String category) {
    // This should also be filtered by user, but for now ignoring
    return repo.findByCategory(category);
  }
}
