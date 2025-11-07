package com.example.expensetracker.controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.expensetracker.model.Expense;
import com.example.expensetracker.service.ExpenseService;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin("*")
public class ExpenseController {
  private final ExpenseService service;
  public ExpenseController(ExpenseService service){this.service=service;}
  @PostMapping
  public Expense create(@RequestBody Expense e){return service.saveExpense(e);}
  @GetMapping
  public List<Expense> all(){return service.getAll();}
  @GetMapping("/{id}")
  public Expense get(@PathVariable Long id){return service.getById(id);}
  @PutMapping("/{id}")
  public Expense update(@PathVariable Long id,@RequestBody Expense e){return service.update(id,e);}
  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id){service.delete(id);}
  @GetMapping("/category")
  public List<Expense> byCat(@RequestParam String category){return service.getByCategory(category);}
}
