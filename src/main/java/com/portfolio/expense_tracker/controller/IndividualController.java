package com.portfolio.expense_tracker.controller;

import com.portfolio.expense_tracker.dto.IndividualDTO;
import com.portfolio.expense_tracker.service.IndividualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/individuals")
public class IndividualController {
    @Autowired private IndividualService service;

    @GetMapping("/me")
    public ResponseEntity<IndividualDTO> getCurrentIndividual() {
        Optional<IndividualDTO> individualOpt = service.getIfOwner();
        return individualOpt.map(individual -> ResponseEntity.ok().body(individual)).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/me")
    public ResponseEntity<IndividualDTO> updateCurrentIndividual(@RequestBody IndividualDTO body) {
        Optional<IndividualDTO> individualOpt = service.updateIfOwner(body);
        return individualOpt.map(individual -> ResponseEntity.ok().body(individual)).orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
