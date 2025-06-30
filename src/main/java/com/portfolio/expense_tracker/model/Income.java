package com.portfolio.expense_tracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Incomes")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "income_id", nullable = false)
    private Long id;

    @Column(columnDefinition = "DECIMAL(10, 2)", nullable = false)
    private Double amount;

    @Column(name = "load_date", columnDefinition = "DATETIME")
    private LocalDateTime loadDate;

    @ManyToOne // a many-to-one relationship is needed to avoid duplicate entries
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "individual_id", updatable = false)
    private Individual individual;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "labels_incomes",
            joinColumns = @JoinColumn(name = "income_id"),
            inverseJoinColumns = @JoinColumn(name = "label_id")
    )
    private List<Label> labels;
}
