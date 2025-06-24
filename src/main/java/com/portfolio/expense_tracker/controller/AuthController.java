package com.portfolio.expense_tracker.controller;

import com.portfolio.expense_tracker.dto.AuthDTO;
import com.portfolio.expense_tracker.dto.CredentialDTO;
import com.portfolio.expense_tracker.service.AuthService;
import com.portfolio.expense_tracker.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired private AuthService service;
    @Autowired private CredentialService credService;

    @PostMapping("/register")
    public ResponseEntity<AuthDTO> register(@RequestBody AuthDTO body) {
        Optional<AuthDTO> response = service.register(body);
        return response.map(authDTO -> ResponseEntity.ok().body(authDTO)).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CredentialDTO body) {
        Optional<String> tokenOpt = credService.authenticate(body);
        return tokenOpt.<ResponseEntity<?>>map(s -> ResponseEntity.ok(Map.of("token", s))).orElseGet(() -> ResponseEntity.badRequest().body(Map.of("error", "Bad Credentials")));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.ok().build();
    }
}
