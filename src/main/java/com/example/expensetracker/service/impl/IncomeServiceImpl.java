package com.example.expensetracker.service.impl;

import com.example.expensetracker.model.Income;
import com.example.expensetracker.model.User;
import com.example.expensetracker.repository.IncomeRepository;
import com.example.expensetracker.repository.UserRepository;
import com.example.expensetracker.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomeServiceImpl implements IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private UserRepository userRepository;

    private User getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<Income> getAllIncomes() {
        return incomeRepository.findByUser(getCurrentUser());
    }

    @Override
    public Income addIncome(Income income) {
        income.setUser(getCurrentUser());
        return incomeRepository.save(income);
    }

    @Override
    public void deleteIncome(Long id) {
        Income income = incomeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Income not found"));

        // Ensure user owns the income before deleting
        if (!income.getUser().getId().equals(getCurrentUser().getId())) {
            throw new RuntimeException("Unauthorized");
        }

        incomeRepository.delete(income);
    }
}
