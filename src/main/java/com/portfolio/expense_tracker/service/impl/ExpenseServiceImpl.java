package com.portfolio.expense_tracker.service.impl;

import com.portfolio.expense_tracker.dto.*;
import com.portfolio.expense_tracker.mapper.ExpenseMapper;
import com.portfolio.expense_tracker.mapper.LabelMapper;
import com.portfolio.expense_tracker.model.Expense;
import com.portfolio.expense_tracker.repository.ExpenseRepository;
import com.portfolio.expense_tracker.service.CurrencyService;
import com.portfolio.expense_tracker.service.ExpenseService;
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
public class ExpenseServiceImpl implements ExpenseService {
    @Autowired
    private ExpenseRepository repo;
    @Autowired private IndividualService individualService;
    @Autowired private CurrencyService currencyService;
    @Autowired private ExpenseMapper mapper;
    @Autowired private LabelService labelService;
    @Autowired private LabelMapper labelMapper;


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

    /* methods related to the labels */
    @Override
    public boolean addLabel(ExpenseLabelDTO clientData) {
        Optional<IndividualDTO> individualOpt = individualService.getIfOwner(); // checks the user authentication

        if (individualOpt.isEmpty())
            return false;

        Optional<ExpenseDTO> expenseOpt = getById(clientData.getExpenseId()); // checks if the expense exists
        Optional<LabelDTO> labelOpt = labelService.getByName(clientData.getLabelName()); // checks if the label exists

        // if the expense doesn't exist, its no needed to execute the rest of code
        if (expenseOpt.isEmpty())
            return false;

        if (labelOpt.isEmpty()) {
            // the label doesn't exist yet, so its created
            Optional<LabelDTO> newLabel = labelService.addByName(clientData.getLabelName());

            // attaching the new label to the expense
            if (newLabel.isPresent()) {
                Expense expense = mapper.toEntity(expenseOpt.get());
                expense.getLabels().add(labelMapper.toEntity(newLabel.get()));
                repo.save(expense);

                return true;
            }
        } else {
            // the label already exists, so the attachment is made instantly
            Expense expense = mapper.toEntity(expenseOpt.get());
            expense.getLabels().add(labelMapper.toEntity(labelOpt.get()));
            repo.save(expense);

            return true;
        }


        return false; // something is wrong
    }


    @Override
    public boolean addLabels(Long id, List<LabelDTO> labels) {
        Optional<IndividualDTO> individualOpt = individualService.getIfOwner(); // checks the user authentication

        if (individualOpt.isEmpty())
            return false;

        Optional<ExpenseDTO> expenseOpt = getById(id); // checks if the expense exists

        // if the expense doesn't exist, its no needed to execute the rest of code
        if (expenseOpt.isEmpty())
            return false;

        for (LabelDTO label : labels) {
            Optional<LabelDTO> labelOpt = labelService.getByName(label.getName());

            if (labelOpt.isEmpty())
                // the label doesn't exist, so its created
                labelOpt = labelService.addByName(label.getName());

            if (labelOpt.isPresent()) {
                Expense expense = mapper.toEntity(expenseOpt.get());
                expense.getLabels().add(labelMapper.toEntity(labelOpt.get()));
                repo.save(expense);
            }
        }

        return true;
    }

    @Override
    public boolean removeLabel(ExpenseLabelDTO clientData) {
        Optional<IndividualDTO> individualOpt = individualService.getIfOwner(); // checks the user authentication

        if (individualOpt.isEmpty())
            return false;

        Optional<ExpenseDTO> expenseOpt = getById(clientData.getExpenseId()); // checks if the income exists
        Optional<LabelDTO> labelOpt = labelService.getByName(clientData.getLabelName()); // checks if the label exists

        // if the income or label don't exist, its no needed to execute the rest of code
        if (expenseOpt.isEmpty() || labelOpt.isEmpty())
            return false;

        repo.removeLabel(labelOpt.get().getId(), expenseOpt.get().getId());

        return true;
    }

    @Override
    public Optional<List<ExpenseDTO>> filterByLabel(String labelName) {
        List<ExpenseDTO> expenses = getAll();
        Optional<LabelDTO> labelOpt = labelService.getByName(labelName);

        if (labelOpt.isEmpty())
            return Optional.empty();

        List<ExpenseDTO> expensesFiltered = expenses.stream()
                .filter(income -> income.getLabels().contains(labelOpt.get()))
                .toList();

        return Optional.of(expensesFiltered);
    }

    @Override
    public Optional<List<ExpenseDTO>> filterByMultipleLabels(List<LabelDTO> clientData) {
        List<ExpenseDTO> expenses = getAll();
        List<LabelDTO> validLabels = new ArrayList<>();

        for (LabelDTO item : clientData) {
            // if the label is found, it's add to the list of valid labels
            Optional<LabelDTO> labelOpt = labelService.getByName(item.getName());
            labelOpt.ifPresent(validLabels::add);
        }

        List<ExpenseDTO> expensesFiltered = expenses.stream()
                .filter(income -> new HashSet<>(income.getLabels()).containsAll(validLabels))
                .toList();

        return Optional.of(expensesFiltered);
    }

    @Override
    public List<ExpenseDTO> getFullHistory() {
        List<Expense> expenses = repo.findAllByOrderByLoadDate();
        return mapper.toDtoList(expenses);
    }
}
