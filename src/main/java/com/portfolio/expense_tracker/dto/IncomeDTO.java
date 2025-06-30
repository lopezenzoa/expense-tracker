package com.portfolio.expense_tracker.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

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
    @JsonIgnore private IndividualDTO individual;
    private List<LabelDTO> labels;
}
