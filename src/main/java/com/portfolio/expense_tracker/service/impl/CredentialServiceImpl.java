package com.portfolio.expense_tracker.service.impl;

import com.portfolio.expense_tracker.dto.CredentialDTO;
import com.portfolio.expense_tracker.mapper.CredentialMapper;
import com.portfolio.expense_tracker.model.Credential;
import com.portfolio.expense_tracker.repository.CredentialRepository;
import com.portfolio.expense_tracker.security.JwtService;
import com.portfolio.expense_tracker.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CredentialServiceImpl implements CredentialService {
    @Autowired private CredentialRepository repo;
    @Autowired private CredentialMapper mapper;
    @Autowired private PasswordEncoder encoder;
    @Autowired private AuthenticationManager authManager;
    @Autowired private UserDetailsService userDetailsService;
    @Autowired private JwtService jwtService;

    @Override
    public CredentialDTO create(CredentialDTO clientData) {
        Credential saved = repo.save(mapper.toEntity(clientData));
        return mapper.toDto(saved);
    }

    // This method "authenticate" returns an optional of type String with a generated jwt or empty (if the user cannot be authenticated)
    @Override
    public Optional<String> authenticate(CredentialDTO clientData) {
        // Authenticates the user with the password
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            clientData.getEmail(),
                            clientData.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            return Optional.empty();
        }

        // Gets the user info from the DB
        UserDetails user = userDetailsService.loadUserByUsername(clientData.getEmail());

        // Generates the jwt token
        String token = jwtService.generateToken(user);

        return Optional.of(token);
    }
}
