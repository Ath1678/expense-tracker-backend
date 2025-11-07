package com.example.expensetracker.service.impl;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.expensetracker.model.Expense;
import com.example.expensetracker.repository.ExpenseRepository;
import com.example.expensetracker.service.ExpenseService;

@Service
public class ExpenseServiceImpl implements ExpenseService {
  private final ExpenseRepository repo;
  public ExpenseServiceImpl(ExpenseRepository repo){this.repo=repo;}
  public Expense saveExpense(Expense e){return repo.save(e);}
  public List<Expense> getAll(){return repo.findAll();}
  public Expense getById(Long id){return repo.findById(id).orElse(null);}
  public Expense update(Long id, Expense e){
    Expense ex=repo.findById(id).orElse(null);
    if(ex==null) return null;
    ex.setTitle(e.getTitle());
    ex.setCategory(e.getCategory());
    ex.setAmount(e.getAmount());
    return repo.save(ex);
  }
  public void delete(Long id){repo.deleteById(id);}
  public List<Expense> getByCategory(String category){return repo.findByCategory(category);}
}
