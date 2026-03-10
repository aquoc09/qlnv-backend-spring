package com.kenji.qlnv_backend.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContractRequest {
    Long employeeId;
    String contractNumber;
    String contractType;
    LocalDate startDate;
    LocalDate endDate;
    BigDecimal baseSalary;
    String status;
}
