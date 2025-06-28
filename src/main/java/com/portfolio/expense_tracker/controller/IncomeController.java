package com.portfolio.expense_tracker.controller;

import com.portfolio.expense_tracker.dto.IncomeDTO;
import com.portfolio.expense_tracker.dto.IncomeLabelDTO;
import com.portfolio.expense_tracker.dto.LabelDTO;
import com.portfolio.expense_tracker.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/incomes")
public class IncomeController {
    @Autowired private IncomeService service;

    @PostMapping("/me/add")
    public ResponseEntity<IncomeDTO> add(@RequestBody IncomeDTO body) {
        IncomeDTO incomeDTO = service.add(body);

        if (incomeDTO != null)
            return ResponseEntity.ok(incomeDTO);

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/me")
    public ResponseEntity<List<IncomeDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/me/filter/{labelName}")
    public ResponseEntity<List<IncomeDTO>> filterByLabel(@PathVariable String labelName) {
        Optional<List<IncomeDTO>> response = service.filterByLabel(labelName);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/me/filter")
    public ResponseEntity<List<IncomeDTO>> filterByLabel(@RequestBody List<LabelDTO> body) {
        Optional<List<IncomeDTO>> response = service.filterByMultipleLabels(body);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/me/{id}")
    public ResponseEntity<IncomeDTO> getById(@PathVariable Long id) {
        return service.getById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/me/update")
    public ResponseEntity<IncomeDTO> update(@RequestBody IncomeDTO body) {
        Optional<IncomeDTO> incomeOpt = service.update(body);
        return incomeOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/me/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = service.delete(id);

        if (deleted)
            return ResponseEntity.ok().build();

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/me/labels/add")
    public ResponseEntity<Void> addLabel(@RequestBody IncomeLabelDTO body) {
        boolean attached = service.addLabel(body);

        if (attached)
            return ResponseEntity.ok().build();

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/me/labels/add/{id}")
    public ResponseEntity<Void> addLabels(@PathVariable("id") Long id, @RequestBody List<LabelDTO> body) {
        boolean attached = service.addLabels(id, body);

        if (attached)
            return ResponseEntity.ok().build();

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/me/labels/remove")
    public ResponseEntity<Void> removeLabel(@RequestBody IncomeLabelDTO body) {
        boolean attached = service.removeLabel(body);

        if (attached)
            return ResponseEntity.ok().build();

        return ResponseEntity.badRequest().build();
    }
}
