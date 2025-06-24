package com.portfolio.expense_tracker.service.impl;

import com.portfolio.expense_tracker.dto.IndividualDTO;
import com.portfolio.expense_tracker.mapper.IndividualMapper;
import com.portfolio.expense_tracker.repository.IndividualRepository;
import com.portfolio.expense_tracker.service.CredentialService;
import com.portfolio.expense_tracker.service.IndividualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndividualServiceImpl implements IndividualService {
    @Autowired
    private IndividualRepository repo;
    @Autowired
    private CredentialService credService;
    @Autowired
    private IndividualMapper mapper;

    // by default, all the individual profile is empty, since is not necessary for the API to have all personal info
    // then, the Individual can add its name by updating its profile
    @Override
    public void create(IndividualDTO clientData) {
        repo.save(mapper.toEntity(clientData));
    }
}
