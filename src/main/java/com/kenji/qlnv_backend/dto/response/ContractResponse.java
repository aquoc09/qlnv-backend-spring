package com.kenji.qlnv_backend.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContractResponse {
    Long id;
    EmployeeResponse employee;
    String contractNumber;
    String contractType;
    LocalDate startDate;
    LocalDate endDate;
    BigDecimal baseSalary;
    String status;
}
