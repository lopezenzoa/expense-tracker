package com.portfolio.expense_tracker.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Labels")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "label_id", nullable = false)
    private Long id;

    @Column(columnDefinition = "VARCHAR(20)", nullable = false)
    private String name;
}
