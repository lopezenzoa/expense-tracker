package com.portfolio.expense_tracker.controller;

import com.portfolio.expense_tracker.dto.ExpenseDTO;
import com.portfolio.expense_tracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    @Autowired private ExpenseService service;

    @PostMapping("/me/add")
    public ResponseEntity<ExpenseDTO> add(@RequestBody ExpenseDTO body) {
        ExpenseDTO expenseDTO = service.add(body);

        if (expenseDTO != null)
            return ResponseEntity.ok(expenseDTO);

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/me")
    public ResponseEntity<List<ExpenseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/me/{id}")
    public ResponseEntity<ExpenseDTO> getById(@PathVariable Long id) {
        return service.getById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/me/update")
    public ResponseEntity<ExpenseDTO> update(@RequestBody ExpenseDTO body) {
        Optional<ExpenseDTO> expenseDTO = service.update(body);
        return expenseDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/me/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = service.delete(id);

        if (deleted)
            return ResponseEntity.ok().build();

        return ResponseEntity.notFound().build();
    }
}
