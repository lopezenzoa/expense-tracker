package com.portfolio.expense_tracker.controller;

import com.portfolio.expense_tracker.dto.CurrencyDTO;
import com.portfolio.expense_tracker.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// btw, this controller is defined for a future implementation of an admin to control currencies
@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {
    @Autowired private CurrencyService service;

    @PostMapping("/add")
    public ResponseEntity<CurrencyDTO> add(@RequestBody CurrencyDTO clientData) {
        CurrencyDTO currency = service.add(clientData);
        return ResponseEntity.ok(currency);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CurrencyDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/update")
    public ResponseEntity<CurrencyDTO> update(@RequestBody CurrencyDTO newClientData) {
        Optional<CurrencyDTO> currencyOpt = service.update(newClientData);
        return currencyOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = service.delete(id);

        if (deleted)
            return ResponseEntity.ok().build();

        return ResponseEntity.notFound().build();
    }
}
