package com.portfolio.expense_tracker.service.impl;

import com.portfolio.expense_tracker.dto.*;
import com.portfolio.expense_tracker.mapper.IncomeMapper;
import com.portfolio.expense_tracker.mapper.LabelMapper;
import com.portfolio.expense_tracker.model.Expense;
import com.portfolio.expense_tracker.model.Income;
import com.portfolio.expense_tracker.repository.IncomeRepository;
import com.portfolio.expense_tracker.service.CurrencyService;
import com.portfolio.expense_tracker.service.IncomeService;
import com.portfolio.expense_tracker.service.IndividualService;
import com.portfolio.expense_tracker.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class IncomeServiceImpl implements IncomeService {
    @Autowired private IncomeRepository repo;
    @Autowired private IndividualService individualService;
    @Autowired private CurrencyService currencyService;
    @Autowired private LabelService labelService;
    @Autowired private IncomeMapper mapper;
    @Autowired private LabelMapper labelMapper;


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

    /* methods related to the labels */
    @Override
    public boolean addLabel(IncomeLabelDTO clientData) {
        Optional<IndividualDTO> individualOpt = individualService.getIfOwner(); // checks the user authentication

        if (individualOpt.isEmpty())
            return false;

        Optional<IncomeDTO> incomeOpt = getById(clientData.getIncomeId()); // checks if the income exists
        Optional<LabelDTO> labelOpt = labelService.getByName(clientData.getLabelName()); // checks if the label exists

        // if the income doesn't exist, its no needed to execute the rest of code
        if (incomeOpt.isEmpty())
            return false;

        if (labelOpt.isEmpty()) {
            Optional<LabelDTO> newLabel = labelService.addByName(clientData.getLabelName());

            // attaching the new label to the expense
            if (newLabel.isPresent()) {
                Income income = mapper.toEntity(incomeOpt.get());
                income.getLabels().add(labelMapper.toEntity(newLabel.get()));
                repo.save(income);

                return true;
            }
        } else {
            // the label already exists, so the attachment is made instantly
            Income income = mapper.toEntity(incomeOpt.get());
            income.getLabels().add(labelMapper.toEntity(labelOpt.get()));
            repo.save(income);

            return true;
        }

        return false; // something is wrong
    }

    @Override
    public boolean addLabels(Long id, List<LabelDTO> labels) {
        Optional<IndividualDTO> individualOpt = individualService.getIfOwner(); // checks the user authentication

        if (individualOpt.isEmpty())
            return false;

        Optional<IncomeDTO> incomeOpt = getById(id); // checks if the income exists

        // if the income doesn't exist, its no needed to execute the rest of code
        if (incomeOpt.isEmpty())
            return false;

        for (LabelDTO label : labels) {
            Optional<LabelDTO> labelOpt = labelService.getByName(label.getName());

            if (labelOpt.isEmpty())
                // the label doesn't exist, so its created
                labelOpt = labelService.addByName(label.getName());

            if (labelOpt.isPresent()) {
                Income income = mapper.toEntity(incomeOpt.get());
                income.getLabels().add(labelMapper.toEntity(labelOpt.get()));
                repo.save(income);
            }
        }

        return true;
    }


    @Override
    public boolean removeLabel(IncomeLabelDTO clientData) {
        Optional<IndividualDTO> individualOpt = individualService.getIfOwner(); // checks the user authentication

        if (individualOpt.isEmpty())
            return false;

        Optional<IncomeDTO> incomeOpt = getById(clientData.getIncomeId()); // checks if the income exists
        Optional<LabelDTO> labelOpt = labelService.getByName(clientData.getLabelName()); // checks if the label exists

        // if the income or label don't exist, its no needed to execute the rest of code
        if (incomeOpt.isEmpty() || labelOpt.isEmpty())
            return false;

        repo.removeLabel(labelOpt.get().getId(), incomeOpt.get().getId());

        return true;
    }

    @Override
    public Optional<List<IncomeDTO>> filterByLabel(String labelName) {
        List<IncomeDTO> incomes = getAll();
        Optional<LabelDTO> labelOpt = labelService.getByName(labelName);

        if (labelOpt.isEmpty())
            return Optional.empty();

        List<IncomeDTO> incomesFiltered = incomes.stream()
                .filter(income -> income.getLabels().contains(labelOpt.get()))
                .toList();

        return Optional.of(incomesFiltered);
    }

    @Override
    public Optional<List<IncomeDTO>> filterByMultipleLabels(List<LabelDTO> clientData) {
        List<IncomeDTO> incomes = getAll();
        List<LabelDTO> validLabels = new ArrayList<>();

        for (LabelDTO item : clientData) {
            // if the label is found, it's add to the list of valid labels
            Optional<LabelDTO> labelOpt = labelService.getByName(item.getName());
            labelOpt.ifPresent(validLabels::add);
        }

        List<IncomeDTO> incomesFiltered = incomes.stream()
                .filter(income -> new HashSet<>(income.getLabels()).containsAll(validLabels))
                .toList();

        return Optional.of(incomesFiltered);
    }
}
