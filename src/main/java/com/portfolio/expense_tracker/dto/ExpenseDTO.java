package com.portfolio.expense_tracker.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ExpenseDTO {
    private Long id;
    private Double amount;
    private LocalDateTime loadDate;
    private CurrencyDTO currency;
    @JsonIgnore private IndividualDTO individual;
    private List<LabelDTO> labels;
}
