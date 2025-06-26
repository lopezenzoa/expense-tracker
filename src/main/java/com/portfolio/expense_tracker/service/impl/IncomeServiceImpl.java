package com.portfolio.expense_tracker.service.impl;

import com.portfolio.expense_tracker.dto.CurrencyDTO;
import com.portfolio.expense_tracker.dto.IncomeDTO;
import com.portfolio.expense_tracker.dto.IndividualDTO;
import com.portfolio.expense_tracker.mapper.IncomeMapper;
import com.portfolio.expense_tracker.model.Income;
import com.portfolio.expense_tracker.repository.IncomeRepository;
import com.portfolio.expense_tracker.service.CurrencyService;
import com.portfolio.expense_tracker.service.IncomeService;
import com.portfolio.expense_tracker.service.IndividualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

@Service
public class IncomeServiceImpl implements IncomeService {
    @Autowired private IncomeRepository repo;
    @Autowired private IndividualService individualService;
    @Autowired private CurrencyService currencyService;
    @Autowired private IncomeMapper mapper;


    @Override
    public IncomeDTO add(IncomeDTO clientData) {
        Optional<IndividualDTO> individualOpt = individualService.getIfOwner();

            // the body of the request just need to define the sign of the currency (e.g: $, AR$)
        Optional<CurrencyDTO> currencyOpt = currencyService.getBySign(clientData.getCurrency().getSign());

        if (individualOpt.isPresent()) {
            clientData.setIndividual(individualOpt.get()); // the individual who is requesting
            clientData.setLoadDate(LocalDateTime.now()); // this exact moment

            currencyOpt.ifPresent(clientData::setCurrency); // if it's not present, the currency is sent as null

            Income saved = repo.save(mapper.toEntity(clientData));
            return mapper.toDto(saved);
        }

        return null;
    }

    @Override
    public List<IncomeDTO> getAll() {
        Optional<IndividualDTO> individualOpt = individualService.getIfOwner();

        if (individualOpt.isPresent()) {
            List<Income> incomes = repo.getAllByIndividualId(individualOpt.get().getId());

            return mapper.toDtoList(incomes);
        }

        return List.of();
    }

    @Override
    public Optional<IncomeDTO> getById(Long id) {
        Optional<IndividualDTO> individualOpt = individualService.getIfOwner();

        if (individualOpt.isPresent())
            return repo.findById(id).map(mapper::toDto);

        return Optional.empty();
    }

    @Override
    public Optional<IncomeDTO> update(IncomeDTO newClientData) {
        Optional<IndividualDTO> individualOpt = individualService.getIfOwner();
        Optional<IncomeDTO> incomeOpt = getById(newClientData.getId());

        if (incomeOpt.isEmpty() || individualOpt.isEmpty())
            return Optional.empty();

        newClientData.setIndividual(individualOpt.get()); // setting the owner of the income

        // this section is to catch if the client add a currency to an income
        Optional<CurrencyDTO> currencyOpt = currencyService.getBySign(newClientData.getCurrency().getSign());
        currencyOpt.ifPresent(newClientData::setCurrency);

        Income updated = repo.save(mapper.toEntity(newClientData));
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
