package com.portfolio.expense_tracker.service.impl;

import com.portfolio.expense_tracker.dto.AuthDTO;
import com.portfolio.expense_tracker.dto.CredentialDTO;
import com.portfolio.expense_tracker.dto.IndividualDTO;
import com.portfolio.expense_tracker.service.AuthService;
import com.portfolio.expense_tracker.service.IndividualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired private IndividualService individualService;

    @Override
    public Optional<AuthDTO> register(AuthDTO clientData) {
        CredentialDTO credDTO = new CredentialDTO();
        IndividualDTO individualDTO = new IndividualDTO();

        // btw, this must be in a mapper class
        credDTO.setEmail(clientData.getEmail());
        credDTO.setPassword(clientData.getPassword());

        // btw, this must be in a mapper class
        individualDTO.setFirstName(clientData.getFirstName());
        individualDTO.setLastName(clientData.getLastName());
        individualDTO.setCredentials(credDTO);

        individualService.create(individualDTO); // saving the individual section of the form (firstname, lastname and cred)

        return Optional.of(clientData);
    }
}
