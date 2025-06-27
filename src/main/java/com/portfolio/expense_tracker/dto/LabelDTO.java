package com.portfolio.expense_tracker.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LabelDTO {
    private Long id;
    private String name;

    // this can cause a recursive infinite call error
//    private List<ExpenseDTO> expenses;
    @JsonIgnore private List<IncomeDTO> incomes;
}
