package com.kenji.qlnv_backend.entity;

import com.kenji.qlnv_backend.enums.ContractLevel;
import com.kenji.qlnv_backend.enums.ContractStatus;
import com.kenji.qlnv_backend.enums.ContractType;
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
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    @JoinColumn(name = "employee_id", nullable = false)
    Employee employee;

    @Column(name = "contract_number", unique = true, nullable = false)
    String contractNumber;

    @Column(name = "contract_type")
    @Enumerated(EnumType.STRING)
    ContractType contractType;

    @Column(name = "contract_level")
    @Enumerated(EnumType.STRING)
    ContractLevel contractLevel;

    @Column(name = "start_date")
    LocalDate startDate;

    @Column(name = "end_date")
    LocalDate endDate;

    @Column(name = "base_salary")
    BigDecimal baseSalary;

    @Enumerated(EnumType.STRING)
    ContractStatus status;
}
