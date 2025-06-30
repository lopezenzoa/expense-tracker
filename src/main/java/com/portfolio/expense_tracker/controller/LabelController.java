package com.portfolio.expense_tracker.controller;

import com.portfolio.expense_tracker.dto.LabelDTO;
import com.portfolio.expense_tracker.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/labels")
public class LabelController {
    @Autowired private LabelService service;

    @PostMapping("/add")
    public ResponseEntity<LabelDTO> add(@RequestBody LabelDTO clientData) {
        LabelDTO label = service.add(clientData);
        return ResponseEntity.ok(label);
    }

    @PostMapping("/add/{name}")
    public ResponseEntity<LabelDTO> addByName(@PathVariable String name) {
        Optional<LabelDTO> labelOpt = service.addByName(name);
        return labelOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<LabelDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity<LabelDTO> getByName(@PathVariable String name) {
        Optional<LabelDTO> labelOpt = service.getByName(name);
        return labelOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update")
    public ResponseEntity<LabelDTO> update(@RequestBody LabelDTO newClientData) {
        Optional<LabelDTO> labelOpt = service.update(newClientData);
        return labelOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /*
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = service.delete(id);

        if (deleted)
            return ResponseEntity.ok().build();

        return ResponseEntity.notFound().build();
    }

     */
}
