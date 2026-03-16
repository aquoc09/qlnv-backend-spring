package com.kenji.qlnv_backend.dto.response;

import com.kenji.qlnv_backend.enums.RewardDisciplineType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RewardDisciplineResponse {
    Long id;
    EmployeeResponse employee;
    RewardDisciplineType type;
    String reason;
    BigDecimal amount;
    LocalDate decisionDate;
}
