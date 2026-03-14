package com.kenji.qlnv_backend.entity;
import com.kenji.qlnv_backend.enums.RewardDisciplineType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "reward_disciplines")
public class RewardDiscipline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    Employee employee;

    @Enumerated(EnumType.STRING)
    RewardDisciplineType type;

    String reason;

    BigDecimal amount;

    @Column(name = "decision_date")
    LocalDate decisionDate;
}
