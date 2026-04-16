package com.example.expensetracker.repository;

import com.example.expensetracker.model.Income;
import com.example.expensetracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
    List<Income> findByUser(User user);

    // Optional: for future backend filtering
    List<Income> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);
}
