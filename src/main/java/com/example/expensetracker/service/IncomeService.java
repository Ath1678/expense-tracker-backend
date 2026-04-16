package com.example.expensetracker.service;

import com.example.expensetracker.model.Income;
import java.util.List;

public interface IncomeService {
    List<Income> getAllIncomes();

    Income addIncome(Income income);

    void deleteIncome(Long id);
}
