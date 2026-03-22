package com.kenji.qlnv_backend.dto.response;

import com.kenji.qlnv_backend.enums.ContractStatus;
import com.kenji.qlnv_backend.enums.ContractType;
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
    Long employeeId;
    String contractNumber;
    ContractType contractType;
    LocalDate startDate;
    LocalDate endDate;
    BigDecimal baseSalary;
    ContractStatus status;
}
