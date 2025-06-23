package com.portfolio.expense_tracker.controller;

import com.portfolio.expense_tracker.dto.CredentialDTO;
import com.portfolio.expense_tracker.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class CredentialController {
    @Autowired private CredentialService service;

    @PostMapping("/register")
    public ResponseEntity<CredentialDTO> register(@RequestBody CredentialDTO body) {
        CredentialDTO response = service.create(body);

        if (response != null)
            return ResponseEntity.ok().body(response);

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CredentialDTO body) {
        Optional<String> tokenOpt = service.authenticate(body);
        return tokenOpt.<ResponseEntity<?>>map(s -> ResponseEntity.ok(Map.of("token", s))).orElseGet(() -> ResponseEntity.badRequest().body(Map.of("error", "Bad Credentials")));
    }
}
