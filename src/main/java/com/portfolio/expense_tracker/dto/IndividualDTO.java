package com.portfolio.expense_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IndividualDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private CredentialDTO credentials;
    private List<ExpenseDTO> expenses;
    private List<IncomeDTO> incomes;
}
