package com.portfolio.expense_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// this DTO is meant to be the register form
public class AuthDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
