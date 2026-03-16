package com.kenji.qlnv_backend.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LeaveBalanceResponse {
    Long id;
    Long employeeId;
    Integer year;
    Integer totalDays;
    Integer usedDays;
}
