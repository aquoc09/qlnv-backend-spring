package com.kenji.qlnv_backend.entity;
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
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    Employee employee;

    Integer month;

    Integer year;

    @Column(name = "base_salary")
    BigDecimal baseSalary;

    BigDecimal allowance;

    BigDecimal bonus;

    BigDecimal deduction;

    @Column(name = "net_salary")
    BigDecimal netSalary;

    @Column(name = "payment_date")
    LocalDate paymentDate;

    String status;
}
