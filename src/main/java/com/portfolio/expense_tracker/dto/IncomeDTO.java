package com.portfolio.expense_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncomeDTO {
    private Long id;
    private Double amount;
    private LocalDateTime loadDate;
    private CurrencyDTO currency;
    private List<LabelDTO> labels;
}
