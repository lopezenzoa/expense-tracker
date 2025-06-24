package com.portfolio.expense_tracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Individuals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Individual {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "individual_id", nullable = false)
    private Long id;

    @Column(name = "first_name", nullable = false, columnDefinition = "VARCHAR(40)")
    private String firstName;

    @Column(name = "last_name", nullable = false, columnDefinition = "VARCHAR(40)")
    private String lastName;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "credential_id", updatable = false)
    private Credential credentials;

    @OneToMany
    @JoinColumn(name = "expense_id", updatable = false, insertable = false)
    private List<Expense> expenses;

    @OneToMany
    @JoinColumn(name = "income_id", updatable = false, insertable = false)
    private List<Income> incomes;
}
