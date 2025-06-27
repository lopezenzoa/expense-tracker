package com.portfolio.expense_tracker.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Labels")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "label_id", nullable = false)
    private Long id;

    @Column(columnDefinition = "VARCHAR(20)", nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "labels_expenses",
            joinColumns = @JoinColumn(name = "label_id"),
            inverseJoinColumns = @JoinColumn(name = "expense_id")
    )
    private List<Expense> expenses;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "labels_incomes",
            joinColumns = @JoinColumn(name = "label_id"),
            inverseJoinColumns = @JoinColumn(name = "income_id")
    )
    private List<Income> incomes;
}
