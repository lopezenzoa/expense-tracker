package com.portfolio.expense_tracker.service.impl;

import com.portfolio.expense_tracker.dto.LabelDTO;
import com.portfolio.expense_tracker.mapper.LabelMapper;
import com.portfolio.expense_tracker.model.Label;
import com.portfolio.expense_tracker.repository.LabelRepository;
import com.portfolio.expense_tracker.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LabelServiceImpl implements LabelService {
    @Autowired private LabelRepository repo;
    @Autowired private LabelMapper mapper;

    @Override
    public LabelDTO add(LabelDTO clientData) {
        Label saved = repo.save(mapper.toEntity(clientData));
        return mapper.toDto(saved);
    }

    @Override
    public List<LabelDTO> getAll() {
        List<Label> labels = repo.findAll();
        return mapper.toDtoList(labels);
    }

    @Override
    public Optional<LabelDTO> getByName(String name) {
        Optional<Label> labelOpt = repo.findByName(name);
        return labelOpt.map(mapper::toDto);
    }

    @Override
    public Optional<LabelDTO> update(LabelDTO newClientData) {
        Label updated = repo.save(mapper.toEntity(newClientData));
        return Optional.of(mapper.toDto(updated));
    }

    /*

    @Override
    public boolean delete(Long id) {
        Optional<Label> labelOpt = repo.findById(id);

        if (labelOpt.isPresent()) {
            repo.deleteById(id);
            return true;
        }

        return false;
    }

     */
}
