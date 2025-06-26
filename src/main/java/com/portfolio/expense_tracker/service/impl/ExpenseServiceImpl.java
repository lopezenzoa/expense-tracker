package com.portfolio.expense_tracker.service.impl;

import com.portfolio.expense_tracker.dto.CurrencyDTO;
import com.portfolio.expense_tracker.dto.ExpenseDTO;
import com.portfolio.expense_tracker.dto.IndividualDTO;
import com.portfolio.expense_tracker.mapper.ExpenseMapper;
import com.portfolio.expense_tracker.model.Expense;
import com.portfolio.expense_tracker.repository.ExpenseRepository;
import com.portfolio.expense_tracker.service.CurrencyService;
import com.portfolio.expense_tracker.service.ExpenseService;
import com.portfolio.expense_tracker.service.IndividualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {
    @Autowired
    private ExpenseRepository repo;
    @Autowired private IndividualService individualService;
    @Autowired private CurrencyService currencyService;
    @Autowired private ExpenseMapper mapper;


    @Override
    public ExpenseDTO add(ExpenseDTO clientData) {
        Optional<IndividualDTO> individualOpt = individualService.getIfOwner();

        // the body of the request just need to define the sign of the currency (e.g: $, AR$)
        Optional<CurrencyDTO> currencyOpt = currencyService.getBySign(clientData.getCurrency().getSign());

        if (individualOpt.isPresent()) {
            clientData.setIndividual(individualOpt.get()); // the individual who is requesting
            clientData.setLoadDate(LocalDateTime.now()); // this exact moment

            currencyOpt.ifPresent(clientData::setCurrency); // if it's not present, the currency is sent as null

            Expense saved = repo.save(mapper.toEntity(clientData));
            return mapper.toDto(saved);
        }

        return null;
    }

    @Override
    public List<ExpenseDTO> getAll() {
        Optional<IndividualDTO> individualOpt = individualService.getIfOwner();

        if (individualOpt.isPresent()) {
            List<Expense> expenses = repo.getAllByIndividualId(individualOpt.get().getId());

            return mapper.toDtoList(expenses);
        }

        return List.of();
    }

    @Override
    public Optional<ExpenseDTO> getById(Long id) {
        Optional<IndividualDTO> individualOpt = individualService.getIfOwner();

        if (individualOpt.isPresent())
            return repo.findById(id).map(mapper::toDto);

        return Optional.empty();
    }

    @Override
    public Optional<ExpenseDTO> update(ExpenseDTO newClientData) {
        Optional<IndividualDTO> individualOpt = individualService.getIfOwner();
        Optional<ExpenseDTO> expenseOpt = getById(newClientData.getId());

        if (expenseOpt.isEmpty() || individualOpt.isEmpty())
            return Optional.empty();

        newClientData.setIndividual(individualOpt.get()); // setting the owner of the income

        // this section is to catch if the client add a currency to an income
        Optional<CurrencyDTO> currencyOpt = currencyService.getBySign(newClientData.getCurrency().getSign());
        currencyOpt.ifPresent(newClientData::setCurrency);

        Expense updated = repo.save(mapper.toEntity(newClientData));
        return Optional.of(mapper.toDto(updated));
    }

    @Override
    public boolean delete(Long id) {
        Optional<IndividualDTO> individualOpt = individualService.getIfOwner();

        if (individualOpt.isEmpty())
            return false;

        repo.deleteById(id);
        return true;
    }
}
