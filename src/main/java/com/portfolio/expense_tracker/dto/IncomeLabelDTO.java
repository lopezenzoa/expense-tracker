package com.portfolio.expense_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IncomeLabelDTO {
    private Long incomeId;
    private String labelName;
}
