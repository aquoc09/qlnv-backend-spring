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
public class RewardDisciplineRequest {
    Long employeeId;
    String type;
    String reason;
    BigDecimal amount;
    LocalDate decisionDate;
}
