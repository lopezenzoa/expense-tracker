package com.portfolio.expense_tracker.mapper;

import com.portfolio.expense_tracker.dto.CredentialDTO;
import com.portfolio.expense_tracker.model.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component // by this way, I let Spring inject the class when needed
public class CredentialMapper {
    private final PasswordEncoder encoder;

    @Autowired
    public CredentialMapper(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public CredentialDTO toDto(Credential entity) {
        return new CredentialDTO(
                entity.getId(),
                entity.getEmail(),
                entity.getPassword()
        );
    }

    public Credential toEntity(CredentialDTO dto) {
        return new Credential(
                dto.getId(),
                dto.getEmail(),
                encoder.encode(dto.getPassword())
        );
    }
}
