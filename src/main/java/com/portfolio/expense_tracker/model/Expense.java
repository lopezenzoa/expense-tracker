package com.portfolio.expense_tracker.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Expenses")
@Setter
@Getter
@NoArgsConstructor
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id", nullable = false)
    private Long id;

    @Column(columnDefinition = "DECIMAL(10, 2)", nullable = false)
    private Double amount;

    @Column(name = "load_date", columnDefinition = "DATETIME")
    private LocalDateTime loadDate;

    @OneToOne
    @JoinColumn(name = "currency_id", updatable = false, insertable = false)
    private Currency currency;

    @ManyToMany(mappedBy = "expenses")
    private List<Label> labels;
}
